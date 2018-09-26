package com.cloudling.offer.controller.v1;


import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.HashMap;


/**
 * @Description 主页面
 * @Author Administrator
 * @Date 2018-07-09  15:06
 * @Version 1.0
 **/
public class IndexController extends AdminController {

    public IndexController(ControllerContext context) {
        super(context);

    }

    @action
    public void index() {
        HashMap<String,String>  res=new HashMap<>();
        res.put("last_login_time",TimeUtil.getShortTimeStamp()+"");
        M("person").where("id="+user.get("id")).save_string(res);
        String isadmin = user.get("status");
        switch (isadmin) {
            case "1":
                redirect("/v1/offerAdmin/list");
                break;
            case "2":
                redirect("/v1/offerManage/list");
                break;
            case "3":
                redirect("/v1/client/list");
                break;
            case "4":
                redirect("/v1/client/list");
                break;
            default:
                break;
        }
    }
}
