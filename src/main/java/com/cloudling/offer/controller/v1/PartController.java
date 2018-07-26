package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartCatModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.*;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  12:15
 * @Version 1.0
 **/
public class PartController extends Controller {
    public PartController(ControllerContext context) {
        super(context);
    }

    @action
    public void add_part(){
        toHtml("admin_tpl/add_part");
    }

    @action
    public void do_add_part() {

    }


    @action
    public void edit(){
    }

    @action
    public  void do_edit(){
    }

    @action
    public  void list(){
        toHtml("admin_tpl/part_list");
    }

    @action
    public void getFirstInfo(){
        PartCatModel pm=new PartCatModel();
        String sql="select id,name,cat_id  from part_cat where parent_id=0";
        ArrayList<HashMap<String, String>> list = pm.query(sql);
        success(list);
    }

    @action
    public void getSecondInfo(){
        String  second_id=I("get.cat_id").toString();
        PartCatModel pm=new PartCatModel();
        ArrayList<HashMap<String, String>> list = pm.list("parent_id='" + second_id + "'");
        success(list);
    }
}
