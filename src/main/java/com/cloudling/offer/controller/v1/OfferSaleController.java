package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.*;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.*;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.JsonUtil;
import com.cloudling.offer.util.TimeUtil;
import jdk.nashorn.internal.parser.JSONParser;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferSaleController extends AdminController {

    public OfferSaleController(ControllerContext context) {
        super(context);
    }

    @action
    public void list() {
        toHtml("admin_tpl/offer_sale_list");
    }

    @action
    public void getSaleOfferList() {
        String page = I("get.page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        HashMap<Integer, String> statusTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put(Dictionary.ISOFFER, "已报价");
                put(Dictionary.NOOFFER, "待报价");
            }
        };
        String sql = "select a.*,b.name as client_name from offer a left join client b on a.client_id=b.id where a.sale_id=" + user.get("id");
        StringBuffer sb = new StringBuffer(sql);
        String sqls = sb.append(" limit " + limit).toString();
        ArrayList<HashMap<String, String>> list = M("offer").query(sqls);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            list.get(i).put("status", statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
        }
        res.put("list", list);
        res.put("num", list.size());
        success(res);
    }

    @action
    public void offer_choose() {
        toHtml("admin_tpl/start_offer_pre");
    }

    @action
    public void getCatInfo() {
        ArrayList<HashMap<String, String>> list = M("part_cat").field("id,name").select();
        success(list);
    }

    @action
    public void start_offer() {
        String cat_id = I("cat_id").toString();
        assign("cat_id", cat_id);
        toHtml("admin_tpl/start_offer");
    }

    @action
    public void get_offer() {
        try {
            String offer_info = I("data").toString();
            HashMap<String, Object> d = JSON.parseObject(offer_info, new HashMap<String, Object>().getClass());
            String custom_id = d.get("custom_id").toString();
            String sale_id = d.get("sale_id").toString();
            int create_time = TimeUtil.getShortTimeStamp();
            HashMap<String, String> data = new HashMap<>();
            data.put("sale_id", sale_id);
            data.put("client_id", custom_id);
            data.put("create_time", create_time + "");
            OfferModel om = new OfferModel();
            long offer_id = om.add(data);
            List<HashMap<String, Object>> products =
                    (List<HashMap<String, Object>>) JSON.parseArray(d.get("products").toString(), new HashMap<String, Object>().getClass());
            HashMap<String, String> numdata = new HashMap<>();
            for (int i = 0; i < products.size(); i++) {
                HashMap<String, Object> product = products.get(i);
                String product_id = product.get("id").toString();
                String product_num = product.get("product_num").toString();
                numdata.put("offer_id", offer_id + "");
                numdata.put("product_id", product_id);
                numdata.put("num", product_num);
                M("offer_product").add(numdata);
                HashMap<String, String> res = new HashMap<>();
                HashMap<String, String> attrs = JSON.parseObject(product.get("attrs").toString(), new HashMap<String, Object>().getClass());
                ArrayList<HashMap<String, String>> list = M("attr").field("id,parent_id").where(" parent_id !=0 and num=-2").select();
                HashMap<String, String> spe = new HashMap<>();
                for (int j = 0; j < list.size(); j++) {
                    String x = list.get(j).get("id");
                    String y = list.get(j).get("parent_id");
                    spe.put(x, y);
                }
                for (String key : attrs.keySet()) {
                    if (spe.containsKey(key)) {
                        res.put("spare_id", spe.get("" + key + ""));
                        res.put("attr_id", key);
                        res.put("num", attrs.get(key));
                    } else {
                        res.put("spare_id", key);
                        res.put("attr_id", attrs.get(key));
                        res.put("num", 0+"");
                    }
                    res.put("product_id", product_id);
                    res.put("offer_id", offer_id + "");
                    long t = M("offer_attr").add(res);
                }
            }
            success("1");
        } catch (Exception e) {
            e.printStackTrace();
            error("提交报价信息失败");
        }

    }

    /**
     * 搜索产品
     *
     * @param
     */
    @action
    public void sear_pro() {
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String pro = I("post.pro") == null ? "" : I("post.pro").toString();
        String sql = "select * from product where code like %" + pro + "%  limit" + limit;
        ArrayList<HashMap<String, String>> list = M("product").query(sql);
        assign("list", JSON.toJSON(list));
    }

    @action
    public void add_spare() {
        String product_id = I("post.product_id") == null ? "" : I("post.product_id").toString();
        String spare_id = I("post.spare_id") == null ? "" : I("post.spare_id").toString();
        String attr_id = I("post.attr_id") == null ? "" : I("post.attr_id").toString();
        String num = I("post.num") == null ? "" : I("post.num").toString();
        String spare_count = I("post.spare_count") == null ? "" : I("post.spare_count").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("product_id", product_id);
        res.put("spare_id", spare_id);
        res.put("attr_id", attr_id);
        res.put("num", num);
        res.put("spare_count", spare_count);

        try {
            M("offer_spare").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }
        success(res);
    }

}

