package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PartBean;
import com.cloudling.offer.bean.PartCatBean;
import com.cloudling.offer.model.PartCatModel;
//import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-19  14:43
 * @Version 1.0
 **/
public class PartCatController extends AdminController {
    public PartCatController(ControllerContext context) {
        super(context);
    }

    @action
    public  void  add(){
        toHtml("admin_tpl/add_part_cat");
    }

    @action
    public void do_add() {
        PartCatBean bean;
        PartCatModel pm=new PartCatModel();
        try {
            bean  = (PartCatBean) BeanUtil.getBean("partCat",context,BeanUtil.POST);
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{
            pm.add_part_cat(bean);
            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("添加配件分类失败");
        }
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
        String  name =URLDecoder.decode(I("get.name").toString());
        PartCatModel pm=new PartCatModel();
        HashMap<String, String> map = pm.listone("name='" + name + "'");
        if(map == null){
            success("1");
        }else{
            error("0");
        }
    }

}
