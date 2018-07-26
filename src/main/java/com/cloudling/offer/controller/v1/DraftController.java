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
        String page= I("get.page").toString();
        String limit=Integer.parseInt(page) * 10 +",10";
        PartModel partModel = new PartModel();
        HashMap<String,Object> res=new HashMap<>();
        ArrayList<HashMap<String,String>> list = null;
        try {
            list = partModel.DraftList(limit);
            for (int i=0;i<list.size();i++) {
                list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("pm", list.get(i).get("产品型号"));
                list.get(i).put("waterProof", list.get(i).get("防水"));
                HashMap<String, String> attr_color;
                HashMap<String, String> attr_size;
                attr_color =JSON.parseObject(list.get(i).get("颜色"), new HashMap<>().getClass());
                attr_size = JSON.parseObject(list.get(i).get("大身尺寸"), new HashMap<>().getClass());
                list.get(i).put("size_attr", attr_size.get("attr"));
                list.get(i).put("size_price", attr_size.get("price"));
                for (String k :
                        attr_color.keySet()) {
                    String v = attr_color.get(k).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int num=list.size();
        res.put("list", list);
        res.put("num",partModel.draftCount());
        success(res);
    }
}
