package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.Bean;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.model.*;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferSaleController extends AdminController {

    public OfferSaleController(ControllerContext context) {
        super(context);
        if (admin_type<=MANAGER){
            pri=false;
            return;
        }
    }

    @action
    public void list() {
        toHtml("admin_tpl/offer_sale_list");
    }

    /**
     * @Description: 查看报价需求详情
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void detail_offer(){
        String offer_id=I("id").toString();
        HashMap<String, String> offer = M("offer").where("id=" + offer_id).find();
        if(offer==null){
            error("不存在该报价");
            return;
        }
        HashMap<String, String> client = M("client").where("id=" + offer.get("client_id")).find();
        assign("client",JSON.toJSONString(client));

//        HashMap<String, String> quote = M("quote").where("offer_id=" + offer_id).find();
//        String qid = quote.get("id");
//        assign("message",quote.get("message"));

        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean_sale(offer_id);
        HashMap<String, Object> b =t.getData();


        assign("products",b);
        toHtml("admin_tpl/show_offer_sale");
    }


    @action
    public void getSaleOfferList() {
        String page = I("get.page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String search_name = I("search_name") == "" ? "" : I("search_name").toString();
        String search_status = I("search_status").equals("2") ? "" : I("search_status").toString();
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
        HashMap<Integer, String> catTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 2L;

            {
                put(Dictionary.WATCH, "手表");
                put(Dictionary.CLOCK, "时钟");
            }
        };
        String sale_id = admin_type == Dictionary.FOLLOW ? M("person").where("id=" + user.get("follow_id")).find().get("id") : user.get("id");
        String sql = "select a.*,b.name as client_name from offer a left join client b on a.client_id=b.id where a.parent_id = 0 and a.sale_id=" + sale_id;
        StringBuffer sb = new StringBuffer(sql);
        StringBuffer snum = new StringBuffer(sql);
        if (search_name.length() > 0) {
            sb.append("  and b.name like '%" + search_name + "%'");
            snum.append(" and b.name like '%" + search_name + "%'");
        }
        if (search_status.length() > 0) {
            sb.append(" and a.status=" + search_status);
            snum.append(" and a.status=" + search_status);
        }
        sb.append(" order by id desc ");
        sb.append(" limit " + limit);
        ArrayList<HashMap<String, String>> list = M("offer").query(sb.toString());
        ArrayList<HashMap<String, String>> list1 = M("offer").query(snum.toString());
        ArrayList<HashMap<String, Object>> slist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> mp = new HashMap<>();
            mp.put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            mp.put("status", statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            mp.put("cat_id", catTypes.get(Integer.parseInt(list.get(i).get("cat_id"))));
            mp.put("client_name", list.get(i).get("client_name"));
            mp.put("id", list.get(i).get("id"));
            mp.put("quote_id",list.get(i).get("quote_id"));
            ArrayList<HashMap<String, String>> subRes = M("offer").where("parent_id=" + list.get(i).get("id")).select();
            for (int j = 0; j < subRes.size(); j++) {
                subRes.get(j).put("create_time", TimeUtil.stampToDate(subRes.get(j).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                subRes.get(j).put("status", statusTypes.get(Integer.parseInt(subRes.get(j).get("status"))));

            }
            mp.put("sub", subRes);
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
        assign("re_offer", 0);
        String cat_id = I("cat_id").toString();
        assign("cat_id", cat_id);
        assign("parent_id", 0);

        assign("products", "[]");
        assign("client", "[]");
        assign("attrs", "[]");
        assign("note","");
        toHtml("admin_tpl/start_offer");
    }

    @action
    public void re_offer() {
        String id = I("id") == null ? "0" : I("id").toString();
        if (id.equals("0")) {
            error("不存在该报价");
            return;
        }
        assign("re_offer", 1);
        assign("parent_id", id);

        HashMap<String, String> offer = M("offer").where("id=" + id).find();

        assign("note",offer.get("note"));

        //客户
        HashMap<String, String> client = M("client").where("id=" + offer.get("client_id")).find();
        assign("client", JSON.toJSONString(client));

        //产品
        ArrayList<HashMap<String, String>> products = M("offer_product").where("offer_id=" + id).select();
        assign("products", JSON.toJSONString(products));

        //分类
        HashMap<String, String> product = M("product").field("cat_id").where("id=" + products.get(0).get("product_id")).find();
        assign("cat_id", product.get("cat_id"));

        //属性
        List<HashMap<String, Object>> attrs = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            ArrayList<HashMap<String, String>> t = M("offer_attr").field("spare_id,attr_id,num").where("offer_id=" + id + " and product_id=" + products.get(i).get("product_id")).select();
            HashMap<String, Object> attr = new HashMap<>();
            attr.put("id", products.get(i).get("product_id"));
            HashMap<String, String> as = new HashMap<>();
            for (int j = 0; j < t.size(); j++) {


                int num = Integer.parseInt(t.get(j).get("num"));
                if (num > 0) {
                    //判断父属性是否有多个子属性
                    int count = M("attr").where("parent_id=" + t.get(j).get("spare_id")).count();
                    if(count==1){
                        as.put(t.get(j).get("spare_id")+"__"+t.get(j).get("attr_id"),num+"");
                    }
                    else {
                        as.put("n_" + t.get(j).get("spare_id"), num + "");
                        as.put(t.get(j).get("spare_id"), t.get(j).get("attr_id"));
                    }
                }else{
                    as.put(t.get(j).get("spare_id"), t.get(j).get("attr_id"));
                }

            }
            attr.put("attrs", as);
            attrs.add(attr);
        }
        assign("attrs", JSON.toJSONString(attrs));


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
            String cat_id = d.get("cat_id").toString();
            String note = d.get("note").toString();

            int create_time = TimeUtil.getShortTimeStamp();
            HashMap<String, String> data = new HashMap<>();
            data.put("sale_id", sale_id);
            data.put("client_id", custom_id);
            data.put("create_time", create_time + "");
            data.put("parent_id", parent_id);
            data.put("cat_id", cat_id);
            data.put("note",note);
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
                numdata.put("url",product.get("photo").toString());
                long offer_product_id=M("offer_product").add(numdata);
                HashMap<String, String> res = new HashMap<>();
                HashMap<String, String> attrs = JSON.parseObject(product.get("attrs").toString(), new HashMap<String, Object>().getClass());
                HashMap<String, String> nums = new HashMap<>();
                HashMap<String, String> s_attrs = new HashMap<>();
                for (String key : attrs.keySet()) {
                    if (key.indexOf("n_") == 0) {
                        String id = key.replace("n_", "");
                        nums.put(id, attrs.get(key).equals("") ? "0" : attrs.get(key));
                        //attrs.remove(key);
                    } else {
                        if ((key.indexOf("__") > 0)) {
                            String[] t2 = key.split("__");
                            nums.put(t2[0], attrs.get(key));
                            s_attrs.put(t2[0], t2[1]);
                        } else s_attrs.put(key, attrs.get(key));
                    }
                }

                for (String key : s_attrs.keySet()) {
                    res.put("spare_id", key);
                    res.put("attr_id", s_attrs.get(key));
                    String num = nums.containsKey(key) ? nums.get(key) : "0";
                    res.put("num", num);
                    res.put("product_id", product_id);
                    res.put("offer_id", offer_id + "");
                    res.put("offer_product_id", offer_product_id + "");
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
     * @Description: 选择报价模板
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void offer_templet() {
        String quote_id = I("quote_id") == null || I("quote_id").equals("") ? "0" : I("quote_id").toString();
        assign("quote_id", quote_id);
        toHtml("admin_tpl/offer_templet_pre");
    }

    @action
    public void do_offer_templet() {
        String quote_id = I("quote_id").toString();
        String offer_templet_id = I("offer_templet_id").toString();
        String templet_url = M("offer_templet").where("id=" + offer_templet_id).find().get("url");
        InputExcelModel iem = new InputExcelModel(templet_url, quote_id);
        try {
            String data = iem.do_excel();
            success(data);
        } catch (Exception e) {
            e.printStackTrace();
            error("导出失败");
        }
    }

    @action
    public void getTempletInfo() {
        String cat_id = I("cat_id").toString();
        String sale_id = admin_type == Dictionary.FOLLOW ? M("person").where("id=" + user.get("follow_id")).find().get("id") : user.get("id");
        ArrayList<HashMap<String, String>> list = M("offer_templet").query("SELECT id,name from (select * from offer_templet where cat_id=" + cat_id + ") a   where  sale_id=0 or sale_id=" + sale_id);
        success(list);
    }
    @action
    public void getSelectAll_sale() {
        long s =TimeUtil.getLongTimeStamp();
        String id = I("id") == null ? "" : I("id").toString();
        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean_sale(id);
        HashMap<String, Object> b =t.getData();
        try {

            success(b);
        } catch (Exception e) {
            // TODO: handle exception
            error("报价参数有误");
        }


        long ms =TimeUtil.getLongTimeStamp()-s;
        System.out.println("-------------------"+ms+"ms");

    }
}

