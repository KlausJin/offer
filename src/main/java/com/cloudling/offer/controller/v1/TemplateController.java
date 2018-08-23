package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.HashMap;

public class TemplateController extends Controller {
    public TemplateController(ControllerContext context) {
        super(context);
    }
    @action
    public void add_template(){
        toHtml("");
    }
    @action
    public void do_add_template(){
        String code = I("post.code") == null ? "" : I("post.code").toString();
        String person = I("post.person") == null ? "" : I("post.person").toString();
        String excel_load = I("post.excel_load") == null ? "" : I("post.excel_load").toString();
        HashMap<String,String> res=new HashMap<>();
        res.put("code",code);
        res.put("person",person);
        res.put("excel_load",excel_load);
        res.put("create_time",TimeUtil.getShortTimeStamp() + "");
        try {
            M("template").add(res);
            success("数据库更新成功");
        } catch (Exception e) {

            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }
}
