package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.OffersModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  17:15
 * @Version 1.0
 **/
public class OfferAdminController extends AdminController {
    public OfferAdminController(ControllerContext context) {
        super(context);
        if (admin_type>ADMIN){
            pri=false;
            return;
        }
    }

    @action
    public void list(){
        toHtml("admin_tpl/offers_list");
    }

    public void getOffersList() {
        String page= I("get.page").toString();
        String limit=Integer.parseInt(page) * 10 +",10";
        HashMap<String,Object> res=new HashMap<>();
        OffersModel om = new OffersModel();
        String sql="select  a.id,b.name as client_name,a.offer_time,c.name as sale_name,d.name as man_name,a.status from offer a LEFT JOIN client b on a.client_id=b.id LEFT JOIN person c on a.sale_id=c.id LEFT JOIN person d ON a.man_id=d.id";
        ArrayList<HashMap<String, String>> list = om.query(sql);
        for(int i=0;i<list.size();i++){

        }
        int num=list.size();
        res.put("list", list);
        res.put("num", num);
        success(res);
    }
}
