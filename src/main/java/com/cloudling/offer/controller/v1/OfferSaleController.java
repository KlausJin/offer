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
        String sql = "select a.*,b.name as client_name from offer a left join client b on a.client_id=b.id where a.parent_id = 0 and a.sale_id=" + user.get("id")+" order by id desc";
        StringBuffer sb = new StringBuffer(sql);
        String sqls = sb.append(" limit " + limit).toString();
        ArrayList<HashMap<String, String>> list1 = M("offer").query(sql);
        ArrayList<HashMap<String, String>> list = M("offer").query(sqls);
        ArrayList<HashMap<String, Object>> slist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String,Object> mp = new HashMap<>();
            mp.put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            mp.put("status", statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            mp.put("client_name",list.get(i).get("client_name"));
            mp.put("id",list.get(i).get("id"));
            ArrayList<HashMap<String, String>> subRes = M("offer").where("parent_id=" + list.get(i).get("id")).select();
            for(int j=0;j<subRes.size();j++){
                subRes.get(j).put("create_time", TimeUtil.stampToDate(subRes.get(j).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                subRes.get(j).put("status", statusTypes.get(Integer.parseInt(subRes.get(j).get("status"))));

            }
            mp.put("sub",subRes);
            slist.add(mp);

        }
        res.put("list", slist);
        res.put("num", list1.size());
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

    /**
     * @Description:业务员报价细节
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void start_offer() {
        assign("re_offer",0);
        String cat_id = I("cat_id").toString();
        assign("cat_id", cat_id);
        assign("parent_id",0);

        assign("products","[]");
        assign("client","[]");
        assign("attrs","[]");

        toHtml("admin_tpl/start_offer");
    }

    @action
    public void re_offer(){
        String id = I("id")==null?"0":I("id").toString();
        if(id.equals("0")){
            error("不存在该报价");
            return;
        }
        assign("re_offer",1);
        assign("parent_id",id);

        HashMap<String, String> offer = M("offer").where("id=" + id).find();
        //客户
        HashMap<String, String> client = M("client").where("id=" + offer.get("client_id")).find();
        assign("client",JSON.toJSONString(client));

        //产品
        ArrayList<HashMap<String, String>> products = M("offer_product").where("offer_id=" + id).select();
        assign("products",JSON.toJSONString(products));

        //分类
        HashMap<String, String> product = M("product").field("cat_id").where("id=" + products.get(0).get("product_id")).find();
        assign("cat_id",product.get("cat_id"));

        //属性
       List<HashMap<String,Object>> attrs = new ArrayList<>();
        for(int i=0;i<products.size();i++){
            ArrayList<HashMap<String, String>> t = M("offer_attr").field("spare_id,attr_id,num").where("offer_id=" + id + " and product_id=" + products.get(i).get("product_id")).select();
            HashMap<String,Object> attr = new HashMap<>();
            attr.put("id",products.get(i).get("product_id"));
            HashMap<String,String> as = new HashMap<>();
            for(int j=0;j<t.size();j++){

                as.put(t.get(j).get("spare_id"),t.get(j).get("attr_id"));
                int num = Integer.parseInt(t.get(j).get("num"));
                if(num>0){
                    as.put("n_"+t.get(j).get("spare_id"),num+"");
                }

            }
            attr.put("attrs",as);
            attrs.add(attr);
        }
        assign("attrs",JSON.toJSONString(attrs));


        toHtml("admin_tpl/start_offer");

    }

    /**
     * @Description:业务员提交需求存数据库
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void do_offer() {
        try {
            String offer_info = I("data").toString();
            HashMap<String, Object> d = JSON.parseObject(offer_info, new HashMap<String, Object>().getClass());
            String custom_id = d.get("custom_id").toString();
            String sale_id = d.get("sale_id").toString();
            String parent_id = d.get("parent_id").toString();
            int create_time = TimeUtil.getShortTimeStamp();
            HashMap<String, String> data = new HashMap<>();
            data.put("sale_id", sale_id);
            data.put("client_id", custom_id);
            data.put("create_time", create_time + "");
            data.put("parent_id",parent_id);
            OfferModel om = new OfferModel();
            long offer_id = om.add(data);
            List<HashMap<String, Object>> products =
                    (List<HashMap<String, Object>>) JSON.parseArray(d.get("products").toString(), new HashMap<String, Object>().getClass());
            HashMap<String, String> numdata = new HashMap<>();
            for (int i = 0; i < products.size(); i++) {
                HashMap<String, Object> product = products.get(i);
                String product_id = product.get("id").toString();
                numdata.put("offer_id", offer_id + "");
                numdata.put("product_id", product_id);
                M("offer_product").add(numdata);
                HashMap<String, String> res = new HashMap<>();
                HashMap<String, String> attrs = JSON.parseObject(product.get("attrs").toString(), new HashMap<String, Object>().getClass());
                HashMap<String,String> nums = new HashMap<>();
                HashMap<String, String> s_attrs = new HashMap<>();
                for (String key : attrs.keySet()) {
                    if(key.indexOf("n_")==0){
                        String id = key.replace("n_","");
                        nums.put(id,attrs.get(key).equals("")?"0":attrs.get(key));
                        //attrs.remove(key);
                    }else{
                        if((key.indexOf("__")>0)){
                            String[] t2 = key.split("__");
                            nums.put(t2[0],attrs.get(key));
                            s_attrs.put(t2[0],t2[1]);
                        }
                        else s_attrs.put(key,attrs.get(key));
                    }
                }

                for (String key : s_attrs.keySet()) {
                    res.put("spare_id", key);
                    res.put("attr_id", s_attrs.get(key));
                    String num = nums.containsKey(key)?nums.get(key):"0";
                    res.put("num", num);
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

