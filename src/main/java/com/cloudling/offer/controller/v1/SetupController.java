package com.cloudling.offer.controller.v1;


import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.Md5Util;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-16  18:41
 * @Version 1.0
 **/
public class SetupController extends AdminController{
    public SetupController(ControllerContext context) {
        super(context);
    }

    @action
    public void change(){
        toHtml("admin_tpl/change_selfpwd");
    }

    @action
    public void do_change(){
        try {
            String new_pwd = I("post.new_pwd").toString();
            HashMap<String,String> map=new HashMap<>();
            map.put("password",Md5Util.MD5(new_pwd));
            M("person").where("id="+user.get("id")).save_string(map);
            success("修改登录密码成功");
        }catch (Exception e){
            error("修改登录密码失败");
        }
    }



}
