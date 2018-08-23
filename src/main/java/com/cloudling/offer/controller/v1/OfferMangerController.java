package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.config.Config;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferMangerController extends Controller {
       public OfferMangerController(ControllerContext context) {
        super(context);
    }
    /**
     * 产品属性渲染到模板
     *
     * @param
     */
    @action
    public void do_man_offer(){
        String id = I("id") == null ? "" : I("id").toString();
        HashMap<String, String> map = M("offer").where("id=" + id).find();
        ArrayList<HashMap<String, String>> list = M("offer_attr").where("offer_id=" + id).select();
        HashMap<String, String> map_product = M("offer_product").where("offer_id=" + id).find();
        HashMap<String, String> map_cat = M("product").where("id=" + map_product.get("product_id")).find();
        ArrayList<HashMap<String,String>> res=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            HashMap<String, String> map_spare = M("attr").where("id=" + list.get(i).get("spare_id")).field("name").find();
            HashMap<String, String> map_attr = M("attr").where("id=" + list.get(i).get("attr_id")).find();
            if (map_cat.get("cat_id").equals("22")){
                if (map_attr.get("price").equals("-2")){

                    map_attr.put("price",null);
                }
                else{
                    HashMap<String, String> map_code = M("attr").where("spare_id=" + map_attr.get("spare_id") + " and code=" + 01).field("price").find();
                    if (map_attr.get("code")!="01" && map_attr.get("code")!="0"){
                        double a = Double.parseDouble(map_attr.get("price"));
                        double b = Double.parseDouble(map_code.get("price"));
                       map_attr.put("price",a+b+"");
                    }
                }
                map_attr.put("spare_name",map_spare.get("name"));
                res.add(map_attr);


            }
            else if (map_cat.get("cat_id").equals("32")){
                if (map_attr.get("price").equals("-2")){
                    HashMap<String, String> map_material = M("material").where("name=" + map_attr.get("name")).find();
                    map_attr.put("price",map_material.get("price"));
                }

                map_attr.put("spare_name",map_spare.get("name"));
                res.add(map_attr);
                res.add(list.get(i));
            }

        }

        success(res);
        assign("list",JSON.toJSON(res));
    }
    /**
     * 保存价格
     *
     * @param
     */
    @action
    public void add_price(){
        String price;

        try{
            price= I("post.name").toString();
        }
        catch (Exception e){
            // TODO: handle exception
            error("参数提交错误");
            return;
        }
        String cat_id = I("cat_id") == null ? "" : I("cat_id").toString();
        String name = I("name") == null ? "" : I("name").toString();
        HashMap<String,Object> res =new HashMap<>();
        if (cat_id.equals("22")){
            res.put("price",price);
            try {
                M("offer_attr").save(res);
                success("数据库更新成功");
            } catch (Exception e) {
                // TODO: handle exception
                error("数据加载到数据库失败");

            }
        }
        else if (cat_id.equals("32")){
              res.put("price",price);
                    res.put("price",price);
            try {
                M("offer_attr").save(res);
                M("material").where("name ="+name).save(res);
                success("数据库更新成功");
            } catch (Exception e) {
                // TODO: handle exception
                error("数据加载到数据库失败");

            }
        }

    }
    /**
     * 报价状态
     *
     * @param
     */
    @action
    public void con_offer(){
        String id = I("id") == null ? "0" : I("id").toString();
        HashMap<String, Object> res =new HashMap<>();
        res.put("status","1");
        try {
            M("offer").where("id="+id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }



    }
    /**
     * 客户利率费用
     *
     * @param
     */

    @action
    public void consumer(){
        String id = I("id") == null ? "" : I("id").toString();
        String profit = I("post.profit") == null ? "" : I("post.profit").toString();
        String num = I("post.num") == null ? "" : I("post.num").toString();
        HashMap<String,String> res=new HashMap<>();
        res.put("profit",profit);
        res.put("num",num);
        res.put("offer_id",id);
        try {
            M("offer_consumer").add(res);
            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }

    }
    /**
     * 产品包装数量
     *
     * @param
     */
    @action
    public void Fob(){
        String id = I("id") == null ? "" : I("id").toString();
        String count = I("post.count") == null ? "" : I("post.count").toString();
        HashMap<String,String> res =new HashMap<>();
        res.put("offer_id",id);
        res.put("count",count);

        try {
            M("offer_FOM").add(res);

            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }




    }
    /**
     * 模板   仓库
     *
     * @param
     */
    @action
    public void add_freight(){
        ArrayList<HashMap<String, String>> list = M("freight").select();
        assign("list",JSON.toJSON(list));
    }
    /**
     * 仓库
     *
     * @param
     */
    @action
    public  void get_freight(){

        String id = I("id") == null ? "" : I("id").toString();
        String freight_id = I("post.freight_id") == null ? "" : I("post.freight_id").toString();
        String fre_num = I("post.fre_num") == null ? "" : I("post.fre_num").toString();
        String fre_price = I("post.fre_price") == null ? "" : I("post.fre_price").toString();
        HashMap<String,String> res=new HashMap<>();
        res.put("offer_id",id);
        res.put("freight_id",freight_id);
        res.put("fre_num",fre_num);
        res.put("fre_price",fre_price);
        try {
            M("offer_freight").add(res);

            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }


    /**
     * 成本6
     *
     * @param
     */
    @action
    public void offer_price(){
        String id = I("id") == null ? "" : I("id").toString();
     //   String assemble = I("post.assemble") == null ? "" : I("post.assemble").toString();//装配工资
        HashMap<String, String> map = M("offer").where("id=" + id).find();
        ArrayList<HashMap<String, String>> list = M("offer_spare").where("offer_id=" + id).select();
        ArrayList<HashMap<String,String>> res=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            HashMap<String, String> map_spare = M("spare").where("id=" + list.get(i).get("spare_id")).field("name").find();
            HashMap<String, String> map_attr = M("attr").where("id=" + list .get(i).get("attr_id")).find();
            String map_price = list.get(i).get("price");
            String attr_num=list.get(i).get("num");
            map_attr.put("attr_price",map_price);
            map_attr.put("spare_name",map_spare.get("name"));
            map_attr.put("attr_num",attr_num);
            res.add(map_attr);
        }
        int spare_total=0;
        for (int j=0;j<res.size();j++){
       int attr_num=Integer.parseInt(res.get(j).get("attr_num"));
       int attr_price=Integer.parseInt(res.get(1).get("attr_price"));

       if (res.get(j).get("formul").equals("0")){int attr_total=attr_price; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("1")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("3")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("4")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("5")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("6")){int attr_total=attr_num*attr_price; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("7")){int attr_total=attr_price/attr_num; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("8")){spare_total=spare_total;}
}
        int product_total=spare_total;
        HashMap<String, String> map_freight = M("offer_freight").where("id=" + id).find();
        product_total=product_total+Integer.parseInt(map_freight.get("num"))*Integer.parseInt(map_freight.get("price"));


    }

}
