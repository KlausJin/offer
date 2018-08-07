package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferManageController extends Controller {
    public OfferManageController(ControllerContext context) {
        super(context);
    }
    /**
     * 报价列表
     *
     * @param
     */
@action
public void offer_list(){
        toHtml("");
}

    @action
    public void do_offer_list(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sale_id = I("sale_id") == null ? "" : I("sale_id").toString();
        ArrayList<HashMap<String, String>> list = M("offer").where("sale_id =" + sale_id).select();
        for(int i=0;i<list.size();i++) {
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
        }
        success(list);
    }

    /**
     * 查看详情
     *
     * @param
     */
    @action
    public void check(){
        toHtml("");
    }
@action
    public void do_check(){
    String id = I("id") == null ? "" : I("id").toString();
    HashMap<String, String> map = M("offer").where("id =" + id).find();
    ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + id).select();
    ArrayList<HashMap<String, String>> list1 = M("offer_spare").where("offer_id=" + id).select();
    ArrayList<HashMap<String, String>> list2 = M("offer_picture").where("offer_id=" +id).select();
    list.add(map);
    list.addAll(list1);
    list.addAll(list2);
    assign("list",JSON.toJSON(list));
    success(list);

}
    /**
     * 编辑报价
     *
     * @param
     */
    @action
    public void edit(){
        toHtml("");
    }
    @action
    public void do_edit(){
    String id = I("id") == null ? "" : I("id").toString();
    HashMap<String, String> map = M("offer").where("id =" + id).find();
    if (map.get("status").equals("0")){
        ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + id).select();
        ArrayList<HashMap<String, String>> list1 = M("offer_spare").where("offer_id=" + id).select();
        ArrayList<HashMap<String, String>> list2 = M("offer_picture").where("offer_id=" +id).select();
        list.add(map);
        list.addAll(list1);
        list.addAll(list2);
        assign("list",JSON.toJSON(list));
        success(list);


    }
    else{
        error("报价已完成");
    }
    }
    /**
     * 发起报价
     *
     * @param
     */
@action
    public void do_offer(){
    redirect("");
}
    /**
     * 再次报价
     *
     * @param
     */
    @action
    public void more_offer(){
        toHtml("");
    }
    @action
    public void do_more_offer(){
        String id = I("id") == null ? "" : I("id").toString();
        HashMap<String, String> map = M("offer").where("id =" + id).find();
        ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + id).select();
        ArrayList<HashMap<String, String>> list1 = M("offer_spare").where("offer_id=" + id).select();
        ArrayList<HashMap<String, String>> list2 = M("offer_picture").where("offer_id=" +id).select();
        list.add(map);
        list.addAll(list1);
        list.addAll(list2);
        assign("list",JSON.toJSON(list));
        success(list);
    }
    /**
     *搜索报价
     *
     * @param
     */
    @action
    public void sear_client(){
        toHtml("");
    }
@action
    public void do_sear_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String sear_name = I("post.sear_name") == null ? "" : I("post.sear_name").toString();
    String sql="select * from client where name like '%" + sear_name+"%'  order by create_time desc limit " +limit;
    ArrayList<HashMap<String, String>> list = M("client").query(sql);

    for (int i =0;i<list.size();i++){
        ArrayList<HashMap<String, String>> res = M("offer").where("client_id=" + list.get(i).get("id")).select();
        list.addAll(res);
        }
    assign("list",JSON.toJSON(list));
    success(list);
}
}
