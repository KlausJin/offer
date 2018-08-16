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
        ArrayList<HashMap<String, String>> list = M("offer_spare").where("offer_id=" + id).select();
        ArrayList<HashMap<String,String>> res=new ArrayList<>();
        for (int j=0;j<list.size();j++){
            HashMap<String, String> map_spare = M("spare").where("id=" + list.get(j).get("spare_id")).field("name").find();
            HashMap<String, String> map_attr = M("attr").where("id=" + list .get(j).get("attr_id")).find();
            map_attr.put("spare_name",map_spare.get("name"));
            res.add(map_attr);
        }
        HashMap<String, String> map_client = M("client").where("id=" + map.get("client_id")).find();
        res.add(map_client);
        ArrayList<HashMap<String, String>> list_picture = M("offer_picture").where("offer_id=" + id).select();
        for (int j=0;j<list_picture.size();j++){
            HashMap<String, String> map_picture = M("picture").where("id=" + list_picture.get(j).get("pic_id")).find();
            res.add(map_picture);
        }
        assign("list",JSON.toJSON(res));
        success(res);
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
        HashMap<String,Object> res =new HashMap<>();
        res.put("price",price);
        try {
            M("offer_spare").save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

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
    @action
    public void offer_price(){
        String id = I("id") == null ? "" : I("id").toString();
        String assemble = I("post.assemble") == null ? "" : I("post.assemble").toString();
        HashMap<String, String> map = M("offer").where("id=" + id).find();
        ArrayList<HashMap<String, String>> list = M("offer_spare").where("offer_id=" + id).select();
        ArrayList<HashMap<String,String>> res=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            HashMap<String, String> map_spare = M("spare").where("id=" + list.get(i).get("spare_id")).field("name").find();
            HashMap<String, String> map_attr = M("attr").where("id=" + list .get(i).get("attr_id")).find();
            String map_price = list.get(i).get("price");
            map_attr.put("attr_price",map_price);
            map_attr.put("spare_name",map_spare.get("name"));
            res.add(map_attr);
        }
        int spare_total=0;
      for (int j=0;j<res.size();j++){
       int price=Integer.parseInt(res.get(j).get("price"));
       int attr_price=Integer.parseInt(res.get(1).get("price"));
       if (res.get(j).get("formul").equals("0")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("1")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("3")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("4")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("5")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("6")){int attr_total=1; spare_total+=attr_total;}
       if (res.get(j).get("formul").equals("7")){int attr_total=1; spare_total+=attr_total;}

}
   int product_total=spare_total+Integer.parseInt(assemble);
        HashMap<String, String> map_freight = M("offer_freight").where("id=" + id).find();
        product_total=product_total+Integer.parseInt(map_freight.get("num"))*Integer.parseInt(map_freight.get("price"));


    }

}
