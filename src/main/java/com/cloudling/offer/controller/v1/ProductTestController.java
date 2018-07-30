package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

public class ProductTestController extends Controller {

    public ProductTestController(ControllerContext context) {
        super(context);
    }

    @action
    public void test(){
        toHtml("admin_tpl/jx");
    }

}
