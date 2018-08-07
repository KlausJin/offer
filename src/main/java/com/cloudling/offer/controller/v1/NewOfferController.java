package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class NewOfferController extends Controller {
    public NewOfferController(ControllerContext context) {
        super(context);
    }
    @action
    public void newoffer(){
        toHtml("");
    }

    @action
    public void get_newoffer(){
        String id = I("id") == null ? "" : I("id").toString();
        String sql="select *,(select count(*) from offer_product where offer.id = offer_product.offer_id) as count,(SELECT person.name from person where offer.sale_id=person.id)as sale_name from offer where m_id="+id +" and status ="+0 +" order by create_time desc";
        ArrayList<HashMap<String, String>> list = M("offer").query(sql);
        for (int i=0;i<list.size();i++){
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
        }
        assign("list",JSON.toJSON(list));
        success(list);

    }

@action
    public void man_offer(){
        toHtml("");
}
@action
    public void do_man_offer(){
    String id = I("id") == null ? "" : I("id").toString();
    HashMap<String, String> map = M("offer").where("id=" + id).find();
    ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + map.get("id")).select();
    for (int i =0;i<list.size();i++) {
        HashMap<String, String> map1 = M("product").where("id=" + list.get(i).get("product_id")).field("code").find();
    }
    ArrayList<HashMap<String, String>> list1 = M("offer").where("offer_id=" + map.get("id")).select();
    for (int j=0;j<list1.size();j++){
        M("attr").where("id="+list1.get(j).get("attr_id")).field("name").find();
    }
}


}
