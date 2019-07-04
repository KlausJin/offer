package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-06  10:22
 * @Version 1.0
 **/
public class WelcomeController extends AdminController {
    public WelcomeController(ControllerContext context) {
        super(context);
        if (admin_type>ADMIN){
            pri=false;
            return;
        }
    }

    @action
    public void list(){
        toHtml("admin_tpl/welcome_list");
    }
}
