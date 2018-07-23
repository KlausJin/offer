package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientController extends Controller {

    public ClientController(ControllerContext context) {
        super(context);
    }
    @action
    public void add (){
        toHtml("");
    }
    @action
    public void do_add(){
        String name, pic;

        try{
            name= I("post.name").toString();
            pic=I("post.pic").toString();
        }
        catch (Exception e){
            // TODO: handle exception
            error("参数提交错误");
            return;

        }
        String remark = I("post.remark") == null ? "0" : I("post.remark").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("name",name);
        res.put("pic",pic);
        res.put("remark",remark);
        try {
            M("client").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }
        assign("res",JSON.toJSON(res));

    }
    @action
    public void get_client() {
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        assign("client",JSON.toJSON((M("client").select())));
    }


@action
public void do_get_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String client_name = I("post.client_name") == null ? "0" : I("post.client_name").toString();
    String sql="select * from client where name like like %" + client_name+"% limit " + limit;
}
}