package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartsModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  13:31
 * @Version 1.0
 **/
public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

    @action
    public void list(){
        PartsModel part = new PartsModel("parts");
        ArrayList<HashMap<String, String>> list = part.list();
        success(list);
    }

    @action
    public void add(){

    }












}


