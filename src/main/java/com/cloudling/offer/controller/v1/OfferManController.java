package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferManController extends Controller {
    public OfferManController(ControllerContext context) {
        super(context);
    }
    @action
    public void offerlist(){
        toHtml("");
    }

    @action
    public void get_offerlist(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 20 + ",20";
        String sql="select *,(SELECT person.name from person where offer.sale_id=person.id)as sale_name,(select client.name from client where offer.client_id=client.id)as client_name from offer  order by create_time desc limit "+limit;
        ArrayList<HashMap<String, String>> list = M("offer").query(sql);
        for (int i=0;i<list.size();i++){
            list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
        }
        success(list);
        assign("list",JSON.toJSON(list));
    }

    /**
     *查看详情
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
        String sql="select *,(SELECT person.name from person where offer.sale_id=person.id)as sale_name,(select client.name from client where offer.client_id=client.id)as client_name ,(select person.name from person where offer.m_id=person.id)as m_name from offer where id="+id;
        ArrayList<HashMap<String, String>> list = M("offer").query(sql);
        String product_sql="select *,(select product.code from product where product.id=offer_product.product_id) as product_name from offer_product where offer_product.offer_id = " +id;
    ArrayList<HashMap<String, String>> list_product = M("offer_product").query(product_sql);
    for (int i=0;i<list_product.size();i++) {
        String spare_sql = "select *,(select spare.name from spare where spare.id=offer_spare.spare_id)as spare_name,(select attr.name from attr where attr.id=offer_spare.attr_id)as attr_name from offer_spare where offer_spare.product_id =" +list_product.get(i).get("id");
        ArrayList<HashMap<String, String>> list_spare = M("offer_spare").query(spare_sql);

        list.addAll(list_spare);
    }
        list.addAll(list_product);
    success(list);
}

    /**
     *搜索客户业务员
     *
     * @param
     */
    @action
    public void do_search(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sear_name = I("post.sear_neme") == null ? "" : I("post.sear_neme").toString();
        String client_sql ="select * from client where name like '%" + sear_name+"%' order by create_time desc limit " +limit;
        String sale_sql="select * from person where name like '%" + sear_name+"%' and status=3 order by create_time desc limit " +limit;
        ArrayList<HashMap<String, String>> list = M("client").query(client_sql);
        list.addAll(M("person").query(sale_sql));
        success(list);
        assign("list",JSON.toJSON(list));
    }
    /**
     *选择客户业务员
     *
     * @param
     */
@action
    public void do_select(){
    String id = I("id") == null ? "" : I("id").toString();


}
    }
