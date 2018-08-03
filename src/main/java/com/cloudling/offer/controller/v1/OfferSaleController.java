package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.*;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.*;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.JsonUtil;
import com.cloudling.offer.util.TimeUtil;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferSaleController extends Controller {

    public OfferSaleController(ControllerContext context) {
        super(context);
    }
    @action
    public void list(){
        toHtml("admin_tpl/sale_product_list");
    }



    @action
    public void add_offer(){
           HashMap<String,String> res =new HashMap<>();
           res.put("create_time",TimeUtil.getShortTimeStamp() + "");
        try {
            M("offer").add(res);
            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }
    /**
     * 搜索产品
     *
     * @param
     */
@action
    public void sear_pro(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String pro = I("post.pro") == null ? "" : I("post.pro").toString();
    String sql ="select * from product where code like %" + pro+"%  limit" +limit;
    ArrayList<HashMap<String, String>> list = M("product").query(sql);
    assign("list",JSON.toJSON(list));

}
    /**
     * 不选择直接添加
     *
     * @param
     */





    /**
     *固定配件
     *
     * @param
     */


    @action
    public void fix_spare(){
        String id = I("post.id") == null ? "" : I("post.id").toString();
        ArrayList<HashMap<String, String>> list = M("spare").where("product_id =" + id).select();
        for (int i =0; i<list.size();i++){
            ArrayList<HashMap<String, String>> map = M("sttr").where("spare_id =" + list.get(i).get("id")).select();
            list.addAll(map);
            for(int j=0;j<map.size();j++){
                ArrayList<HashMap<String, String>> res = M("sttr").where("parent_id =" + map.get(j).get("id")).select();
                list.addAll(res);
            }
            }
            assign("list",JSON.toJSON(list));
            toHtml("");

    }
    /**
     * 常用配件
     *
     *
     * @param
     */
    @action
    public void com_spare(){
        String cat_id = I("post.cat_id") == null ? "" : I("post.cat_id").toString();
        ArrayList<HashMap<String, String>> list = M("spare").where("cat_id =" + cat_id +"and product_id="+0).select();
        for (int i =0; i<list.size();i++) {
            ArrayList<HashMap<String, String>> map = M("sttr").where("spare_id =" + list.get(i).get("id")).select();
            list.addAll(map);
            for (int j = 0; j < map.size(); j++) {
                ArrayList<HashMap<String, String>> res = M("sttr").where("parent_id =" + map.get(j).get("id")).select();
                list.addAll(res);
            }
        }
        assign("list",JSON.toJSON(list));
        toHtml("");
    }
@action
public void add_spare(){
    String product_id = I("post.product_id") == null ? "" : I("post.product_id").toString();
    String spare_id = I("post.spare_id") == null ? "" : I("post.spare_id").toString();
    String attr_id = I("post.attr_id") == null ? "" : I("post.attr_id").toString();
    String num = I("post.num") == null ? "" : I("post.num").toString();
    String spare_count = I("post.spare_count") == null ? "" : I("post.spare_count").toString();
    HashMap<String,String> res =new HashMap<>();
    res.put("product_id",product_id);
    res.put("spare_id",spare_id);
    res.put("attr_id",attr_id);
    res.put("num",num);
    res.put("spare_count",spare_count);

    try {
        M("offer_spare").add(res);
        success("数据库更新成功");
    } catch (Exception e) {
        // TODO: handle exception
        error("数据加载到数据库失败");

    }
    success(res);
}

    /**
     * 删除配件
     *
     * @param
     */
    @action
    public void remove(){
        String spare_id = I("spare_id") == null ? "" : I("spare_id").toString();
        try {
            M("offer_spare").where("spare_id=" + spare_id).delete();
            success("删除成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("删除失败");

        }


    }
    /**
     * 添加到报价表
     *
     * @param
     */

    @action
    public void update_offer(){
        String sale_id, m_id,client_id;

        try{
            sale_id= I("post.sale_id").toString();
            m_id=I("post.m_id").toString();
            client_id=I("post.client_id").toString();
        }
        catch (Exception e){
            // TODO: handle exception
            error("参数提交错误");
            return;
        }
        HashMap<String,String> res=new HashMap<>();
        res.put("sale_id",sale_id);
        res.put("m_id",m_id);
        res.put("m_id",m_id);
        try {
            M("offer").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }
    }
    /**
     * 添加到报价的产品表
     *
     * @param
     */
@action
    public void add_product(){
    String offer_id = I("offer_id") == null ? "" : I("offer_id").toString();
    String product_id = I("product_id") == null ? "" : I("product_id").toString();
    String num = I("num") == null ? "" : I("num").toString();
    String attr_id = I("attr_id") == null ? "" : I("attr_id").toString();
    HashMap<String,String> res =new HashMap<>();
    res.put("offer_id",offer_id);
    res.put("product_id",product_id);
    res.put("num",num);
    res.put("attr_id",attr_id);
}

/**
 * 选择客户
 *
 * @param
 */
@action
    public void sel_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String sql="select * from client order by create_time desc limit " + limit;
    ArrayList<HashMap<String, String>> list = M("client").query(sql);
    for(int i=0;i<list.size();i++) {
        list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
    }
    assign("list",JSON.toJSON(list));
    toHtml("");
}
@action
    public void sear_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String sesr_name = I("post.sesr_name") == null ? "" : I("post.sesr_name").toString();
    String sql="select * from client where name like %" + sesr_name+"%  order by create_time desc limit" +limit;
    ArrayList<HashMap<String, String>> list = M("client").query(sql);
    for(int i=0;i<list.size();i++) {
        list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
    }
    assign("list",JSON.toJSON(list));
}

@action
    public void get_client(){
    String id = I("id") == null ? "" : I("id").toString();
    HashMap<String, String> map = M("client").where("id ="+id).find();
    success(map);
}
    /**
     * 选择备注图片
     *
     * @param
     */
    @action
    public void sel_picture (){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sql="select * from picture  order by create_time desc limit " + limit;
        ArrayList<HashMap<String, String>> list = M("client").query(sql);
        for(int i=0;i<list.size();i++) {
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
        }
        assign("list",JSON.toJSON(list));
        toHtml("");
    }

    @action
    public void sear_picture(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sesr_name = I("post.sesr_name") == null ? "" : I("post.sesr_name").toString();
        String sql="select * from client where name like %" + sesr_name+"%  order by create_time desc limit" +limit;
        ArrayList<HashMap<String, String>> list = M("client").query(sql);
        for(int i=0;i<list.size();i++) {
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
        }
        assign("list",JSON.toJSON(list));
    }

    @action
    public void get_picture(){
        String id = I("id") == null ? "" : I("id").toString();
        HashMap<String, String> map = M("picture").where("id ="+id).find();
        success(map);
    }

    /**
     * 上传备注图片
     *
     * @param
     */

@action
public void upload_pic(){
    String name = I("post.name") == null ? "" : I("post.name").toString();
    String pic = I("post.pic") == null ? "" : I("post.pic").toString();
    HashMap<String,String> res = new HashMap<>();
    res.put("name",name);
    res.put("pic",pic);
    res.put("create_time",TimeUtil.getShortTimeStamp() + "");
    try {
        M("picture").add(res);
        success("数据库更新成功");
    } catch (Exception e) {

        // TODO: handle exception
        error("数据加载到数据库失败");
    }
    assign("res",JSON.toJSON(res));
}

    /**
     * 删除备注图片
     *
     * @param
     */
@action
    public void  remove_pic(){
    String id = I("id") == null ? "" : I("id").toString();
    try {
        M("offer_picture").where("picture_id =" + id).delete();
        success("删除成功");
    } catch (Exception e) {
        // TODO: handle exception
        error("删除失败");

    }
}

}

