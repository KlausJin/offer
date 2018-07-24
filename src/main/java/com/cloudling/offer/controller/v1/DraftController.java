package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-23  10:44
 * @Version 1.0
 **/
public class DraftController extends Controller {
    public DraftController(ControllerContext context) {
        super(context);
    }

    @action
    public void list(){
        toHtml("admin_tpl/draft_list");
    }

    @action
    public  void getList(){

        PartModel partModel = new PartModel();
        HashMap<String,Object> res=new HashMap<>();
        ArrayList<HashMap<String,String>> list = null;
        HashMap<String, String> user;
        try {
            list = partModel.DraftList();
            for (int i=0;i<list.size();i++) {
                list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        success(list);
    }
}
