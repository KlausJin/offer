package com.klaus.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.klaus.offer.annotation.action;
import com.klaus.offer.bean.Bean;
import com.klaus.offer.config.Dictionary;
import com.klaus.offer.model.OfferModel;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.TimeUtil;

import java.util.*;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-03  14:35
 * @Version 1.0
 **/
public class OfferManageController extends AdminController {


    public OfferManageController(ControllerContext context) {
        super(context);
        if (admin_type!=MANAGER){
            pri=false;
            return;
        }
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
        String search_sale_name=I("search_sale_name")==""?"":I("search_sale_name").toString();
        HashMap<String, Object> res = new HashMap<>();
        HashMap<Integer, String> statusTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put(com.klaus.offer.config.Dictionary.ISOFFER, "已报价");
                put(com.klaus.offer.config.Dictionary.NOOFFER, "待报价");
            }
        };
        HashMap<Integer, String> catTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 2L;

            {
                put(com.klaus.offer.config.Dictionary.WATCH, "手表");
                put(com.klaus.offer.config.Dictionary.CLOCK, "时钟");
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
        if (search_sale_name.length()>0){
            sb.append(" and c.name like '%"+search_sale_name+"%'");
            snum.append(" and c.name like '%"+search_sale_name+"%'");
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
        String offer_id=I("id").toString();
        assign("offer_id",offer_id);
        HashMap<String, String> offer = M("offer").where("id=" + offer_id).find();
        if(offer==null){
            error("不存在该报价");
            return;
        }
        assign("note",offer.get("note"));

        HashMap<String, String> client = M("client").where("id=" + offer.get("client_id")).find();
        assign("client",JSON.toJSONString(client));

        HashMap<String, String> quote = M("quote").where("offer_id=" + offer_id).find();
        String qid = quote.get("id");
        assign("message",quote.get("message"));

        ArrayList<HashMap<String, String>> products = M("quote_pro").where("quote_id=" + qid).select();

        for (int i=0;i<products.size();i++){
            ArrayList<HashMap<String, String>> details = M("quote_detail").where("pro_id=" + products.get(i).get("id")).select();
            products.get(i).put("detail",JSON.toJSONString(details));
        }
        assign("products",JSON.toJSONString(products));
        toHtml("admin_tpl/show_offer");
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
        HashMap<String, String> offer = M("offer").where("id=" + offer_id).find();
        if(offer==null){
            error("不存在该报价");
            return;
        }
        assign("note",offer.get("note"));

        ArrayList<HashMap<String, String>> offer_products = M("offer_product").where("offer_id=" + offer_id).select();

        List<String> photos = new ArrayList<>();

        for(int i=0;i<offer_products.size();i++){
            photos.add(offer_products.get(i).get("url"));
        }

        assign("photos",JSON.toJSONString(photos));

        toHtml("admin_tpl/do_offer");
    }

    /**
     * 删除报价
     */
    @action
    public void remove(){
        String offer_id = I("id").toString();
        HashMap<String, String> quote = M("quote").where("offer_id=" + offer_id).find();
        if(quote == null){
            error("不存在该报价");
            return;
        }
        String qid = quote.get("id");

        ArrayList<HashMap<String, String>> pros = M("quote_pro").where("quote_id=" + qid).select();
        //删除细节
        for(int i=0;i<pros.size();i++){
            M("quote_detail").where("pro_id="+pros.get(i).get("id")).delete();
        }
        //删除产品
        M("quote_pro").where("quote_id=" + qid).delete();
        //删除报价
        M("quote").where("id=" + qid).delete();

        HashMap<String,String> data = new HashMap<>();
        data.put("status","0");
        M("offer").where("id="+offer_id).save_string(data);

        success(1);


    }




    @action
    public void do_edit(){
        try{
            String quote_info=I("data").toString();
            HashMap<String, String> d = JSON.parseObject(quote_info, new HashMap<String, String>().getClass());
            String offer_id=d.get("offer_id").toString();
            String manage_id = d.get("manage_id").toString();
            String message=d.get("message").toString();
            HashMap<String, String> quoteData = new HashMap<>();
            HashMap<String, String> offerData = new HashMap<>();
            quoteData.put("offer_id",offer_id);
            quoteData.put("manage_id",manage_id);
            quoteData.put("create_time",TimeUtil.getShortTimeStamp()+"");
            quoteData.put("message",message);
            long id=M("quote").add(quoteData);
            String ps = d.get("products");
            List<LinkedHashMap<String, Object>> products =
                    (List<LinkedHashMap <String, Object>>) JSON.parseArray(ps, new LinkedHashMap <String, String>().getClass());
            HashMap<String, String> proData = new HashMap<>();
            for (int i = 0; i < products.size(); i++) {
                LinkedHashMap<String, Object> product = products.get(i);

                String pro_name=product.get("product").toString();
                String pro_num=product.get("num").toString();
                String per_price=product.get("per_price").toString();
                String c_price=product.get("c_price").toString();
                String rate=product.get("rate").toString();
                String profit=product.get("profit").toString();
                String cbm=product.get("cbm").toString();
                String pic_url = product.get("pic_url").toString();
                String manage_note=product.get("manage_note").toString();
                proData.put("quote_id",id+"");
                proData.put("pro_name",pro_name);
                proData.put("pro_num",pro_num);
                proData.put("per_price",per_price);
                proData.put("c_price",c_price);
                proData.put("rate",rate);
                proData.put("profit",profit);
                proData.put("cbm",cbm);
                proData.put("pic_url",pic_url);
                proData.put("manage_note",manage_note);
                long pro_id=M("quote_pro").add(proData);
                HashMap<String, String> res = new HashMap<>();
                List<HashMap<String,String>> data = (List<HashMap<String,String>>)JSON.parseArray( product.get("data").toString(),new HashMap<String,String>().getClass());
                for(int k=0;k<data.size();k++){
                    res.put("kind",data.get(k).get("key"));
                    res.put("value",data.get(k).get("value"));
                    res.put("num",String.valueOf(data.get(k).get("num")));
                    res.put("price",String.valueOf(data.get(k).get("price")));
                    res.put("per_price",String.valueOf(data.get(k).get("per_price")));
                    res.put("pro_id",pro_id+"");
                    long t=M("quote_detail").add(res);
                }

            }
            offerData.put("status", Dictionary.ISOFFER+"");
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
    /**
     * 根据id查找业务员选择的图片       
     *
     * @param
     */
    @action
    public void getPictureById(){
        String id = I("id") == null ? "" : I("id").toString();
        HashMap<String, String> map = M("picture").where("id=" + id).find();
        try {

            success(map);
        } catch (Exception e) {
            // TODO: handle exception
            error("报价参数有误");
        }
    }


}
