package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-13  17:39
 * @Version 1.0
 **/
public class DictionaryController extends AdminController {
    public DictionaryController(ControllerContext context) {
        super(context);
    }


    @action
    public void profit_list() {
        toHtml("admin_tpl/profit_list");
    }

    @action
    public void getProfit() {
        ArrayList<HashMap<String, String>> list = M("dictionary").select();
        for(int i=0;i<list.size();i++){
            list.get(i).put("updatetime",TimeUtil.stampToDate(list.get(i).get("updatetime"),"yyyy-MM-dd HH:mm:ss"));
        }
        success(list);
    }

    @action
    public void edit(){
        String id =I("id").toString();
        HashMap<String, String> map = M("dictionary").where("id="+id).find();
        assign("info",JSON.toJSON(map));
        toHtml("admin_tpl/edit_profit");
    }

    @action
    public void do_edit(){
        String id =I("id").toString();
        String value=I("value").toString();
        HashMap<String, String> map=new HashMap<>();
        map.put("value",value);
        map.put("updatetime",TimeUtil.getShortTimeStamp()+"");
        try{
            boolean t = M("dictionary").where("id=" + id).save_string(map);
            if (t){
                success("1");
            }
        }catch (Exception e){
            e.printStackTrace();
            error("修改失败");
        }
    }
}
