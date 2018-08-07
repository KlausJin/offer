package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-06  10:22
 * @Version 1.0
 **/
public class WelcomeController extends Controller {
    public WelcomeController(ControllerContext context) {
        super(context);
    }

    @action
    public void list(){
        toHtml("admin_tpl/welcome_list");
    }
}
