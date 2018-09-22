package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.Bean;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.model.OfferModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-03  14:35
 * @Version 1.0
 **/
public class OfferManageController extends AdminController {


    public OfferManageController(ControllerContext context) {
        super(context);
    }


    @action
    public void list(){
        toHtml("admin_tpl/offer_manage_list");
    }

    @action
    public void getManageOfferList() {
        String page = I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String search_name=I("search_name")==""?"":I("search_name").toString();
        String search_status=I("search_status").equals("2")?"":I("search_status").toString();
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
        String sql="select a.*,b.name as client_name,c.name as sale_name from offer a left join client b on a.client_id=b.id left join person c on a.sale_id=c.id where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        StringBuffer snum=new StringBuffer(sql);
        if (search_name.length()>0){
            sb.append(" and b.name like '%"+search_name+"%'");
            snum.append(" and b.name like '%"+search_name+"%'");
        }
        if (search_status.length()>0){
            sb.append(" and a.status="+search_status);
            snum.append(" and a.status="+search_status);
        }
        sb.append(" order by id desc ");
        sb.append(" limit "+limit);
        ArrayList<HashMap<String, String>> list = M("offer").query(sb.toString());
        ArrayList<HashMap<String, String>> list1 = M("offer").query(snum.toString());
        for (int i=0;i<list.size();i++){
            list.get(i).put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            list.get(i).put("status",statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            list.get(i).put("cat_id",catTypes.get(Integer.parseInt(list.get(i).get("cat_id"))));
        }
        res.put("list", list);
        res.put("num", list1.size());
        success(res);
    }

    /**
     * @Description: 查看报价详情
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void detail(){

    }

    /**
     * @Description: 渲染到经理做报价的界面
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void edit(){
        String offer_id=I("id").toString();
        assign("offer_id",offer_id);
        toHtml("admin_tpl/do_offer");
    }


    @action
    public void do_edit(){
        try{
            String quote_info=I("data").toString();
            HashMap<String, Object> d = JSON.parseObject(quote_info, new HashMap<String, Object>().getClass());
            String offer_id=d.get("offer_id").toString();
            String manage_id = d.get("manage_id").toString();
            HashMap<String, String> quoteData = new HashMap<>();
            HashMap<String, String> offerData = new HashMap<>();
            quoteData.put("offer_id",offer_id);
            quoteData.put("manage_id",manage_id);
            quoteData.put("create_time",TimeUtil.getShortTimeStamp()+"");
            long id=M("quote").add(quoteData);
            List<HashMap<String, Object>> products =
                    (List<HashMap<String, Object>>) JSON.parseArray(d.get("products").toString(), new HashMap<String, Object>().getClass());
            HashMap<String, String> proData = new HashMap<>();
            for (int i = 0; i < products.size(); i++) {
                HashMap<String, Object> product = products.get(i);
                String pro_name=product.get("product").toString();
                String pro_num=product.get("num").toString();
                String per_price=product.get("per_price").toString();
                String c_price=product.get("c_price").toString();
                String rate=product.get("rate").toString();
                String profit=product.get("profit").toString();
                String cbm=product.get("cbm").toString();
                proData.put("quote_id",id+"");
                proData.put("pro_name",pro_name);
                proData.put("pro_num",pro_num);
                proData.put("per_price",per_price);
                proData.put("c_price",c_price);
                proData.put("rate",rate);
                proData.put("profit",profit);
                proData.put("cbm",cbm);
                long pro_id=M("quote_pro").add(proData);
                HashMap<String, String> res = new HashMap<>();
                HashMap<String, String> data = JSON.parseObject(product.get("data").toString(), new HashMap<String, Object>().getClass());
                for (String key:data.keySet()) {
                    res.put("kind",key);
                    res.put("value",data.get(key));
                    res.put("pro_id",pro_id+"");
                    long t=M("quote_detail").add(res);
                }
            }
            offerData.put("status",Dictionary.ISOFFER+"");
            offerData.put("m_id",manage_id);
            offerData.put("quote_time",TimeUtil.getShortTimeStamp()+"");
            offerData.put("quote_id",id+"");
            M("offer").where("id="+offer_id).save_string(offerData);
            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("报价失败");
        }
    }



    /**
     * 产品属性渲染到模板(固定配件)
     *
     * @param
     */
    @action
    public void getProduct() {
       long s =TimeUtil.getLongTimeStamp();
        String id = I("id") == null ? "" : I("id").toString();
        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean(id);
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
    /**
     * 产品属性渲染到模板(常用配件)
     *
     * @param
     */
    @action
    public void getProduct_real(){
        String id = I("id") == null ? "" : I("id").toString();
        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean_real(id);
        HashMap<String, Object> b =t.getData();
        try {

            success(b);
        } catch (Exception e) {
            // TODO: handle exception
            error("报价参数有误");
        }

    }

}
