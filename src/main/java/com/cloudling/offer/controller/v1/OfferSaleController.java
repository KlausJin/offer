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
    /**
     * 报价列表
     *
     * @param
     */
    @action
    public void list(){
        toHtml("admin_tpl/sale_product_list");
    }
    @action
    public void get_list(){

    }

    /**
     * 添加到报价表
     *
     * @param
     */

    @action
    public void add_offer(){
        String sale_id, m_id;
        try{
            sale_id= I("post.sale_id").toString();
            m_id=I("post.m_id").toString();

        }

        catch (Exception e){
            // TODO: handle exception
            error("参数提交错误");
            return;
        }
        HashMap<String,String> res=new HashMap<>();
        res.put("sale_id",sale_id);
        res.put("m_id",m_id);
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
    String sql ="select * from product where code like '%"+pro+"%'  limit "+limit;
    ArrayList<HashMap<String, String>> list = M("product").query(sql);
    success(list);
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
            ArrayList<HashMap<String, String>> map = M("attr").where("spare_id =" + list.get(i).get("id")).select();
            list.addAll(map);
            for(int j=0;j<map.size();j++){
                ArrayList<HashMap<String, String>> res = M("attr").where("parent_id =" + map.get(j).get("id")).select();
                list.addAll(res);
            }
            }
            success(list);
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
        ArrayList<HashMap<String, String>> list = M("spare").where("cat_id =" + cat_id +" and product_id="+0).select();
        for (int i =0; i<list.size();i++) {
            ArrayList<HashMap<String, String>> map = M("attr").where("spare_id =" + list.get(i).get("id")).select();
            list.addAll(map);
            for (int j = 0; j < map.size(); j++) {
                ArrayList<HashMap<String, String>> res = M("attr").where("parent_id =" + map.get(j).get("id")).select();
                list.addAll(res);
            }
        }
        success(list);
        assign("list",JSON.toJSON(list));
        toHtml("");
    }
    /**
     *增加配件
     *
     * @param
     */
@action
public void add_spare(){
    String offer_id = I("post.offer_id") == null ? "" : I("post.offer_id").toString();
    String spare_id = I("post.spare_id") == null ? "" : I("post.spare_id").toString();
    String attr_id = I("post.attr_id") == null ? "" : I("post.attr_id").toString();
    String num = I("post.num") == null ? "" : I("post.num").toString();
    String spare_count = I("post.spare_count") == null ? "" : I("post.spare_count").toString();
    HashMap<String,String> res =new HashMap<>();
    res.put("offer_id",offer_id);
    res.put("spare_id",spare_id);
    res.put("attr_id",attr_id);
    if (isempty(num)){res.put("num",num);}
    if (isempty(spare_count)){res.put("spare_count",spare_count);}

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
    try {
        M("offer_product").add(res);
        success("数据库更新成功");
    } catch (Exception e) {
        // TODO: handle exception
        error("数据加载到数据库失败");

    }
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
    success(list);
    assign("list",JSON.toJSON(list));
    toHtml("");
}
@action
    public void sear_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String sear_name = I("post.sear_name") == null ? "" : I("post.sear_name").toString();
    String sql="select * from client where name like '%" + sear_name+"%'  order by create_time desc limit " +limit;
    ArrayList<HashMap<String, String>> list = M("client").query(sql);
    for(int i=0;i<list.size();i++) {
        list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
    }
    success(list);
    assign("list",JSON.toJSON(list));
}

@action
    public void get_client(){
    String id = I("id") == null ? "" : I("id").toString();
    HashMap<String, String> map = M("client").where("id ="+id).find();
    success(map);
}

    /**
     * 添加到报价表
     *
     * @param
     */
    @action
    public void update(){
        String client_id=I("client_id") == null ? "" : I("client_id").toString();
        String id = I("id") == null ? "" : I("id").toString();
        HashMap<String,Object> res=new HashMap<>();
        res.put("client_id",client_id);
        try {
            M("offer").where("id="+id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }
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
        ArrayList<HashMap<String, String>> list = M("picture").query(sql);
        for(int i=0;i<list.size();i++) {
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
        }
        success(list);
        assign("list",JSON.toJSON(list));
        toHtml("");
    }
    /**
     * 搜索备注图片
     *
     * @param
     */
    @action
    public void sear_picture(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sear_name = I("post.sear_name") == null ? "" : I("post.sear_name").toString();
        String sql="select * from picture where name like '%" + sear_name+"%'  order by create_time desc limit " +limit;
        ArrayList<HashMap<String, String>> list = M("picture").query(sql);
        for(int i=0;i<list.size();i++) {
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
        }
        success(list);
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
     * 添加到报价备注图片表
     *
     * @param
     */
    @action
    public void add_pic(){
        String offer_id = I("post.offer_id") == null ? "" : I("post.offer_id").toString();
        String pic_id = I("post.pic_id") == null ? "" : I("post.pic_id").toString();
        HashMap<String,String> res =new HashMap<>();
        res.put("offer_id",offer_id);
        res.put("pic_id",pic_id);
        try {
            M("offer_picture").add(res);
            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }


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
        M("offer_picture").where("pic_id =" + id).delete();
        success("删除成功");
    } catch (Exception e) {
        // TODO: handle exception
        error("删除失败");

    }
}

}

