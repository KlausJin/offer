package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;




public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

    @action
    public void list() {
        PartModel part = new PartModel();
        ArrayList<HashMap<String, String>> list = part.list();
        success(list);
    }

    @action
    public void add() {

    }

    @action
    public void add_bean() {

        long t = TimeUtil.getLongTimeStamp();
        ProductModel productModel = new ProductModel();

        ProductBean bean = productModel.getBean("47");
        long ms = TimeUtil.getLongTimeStamp() - t;
        System.out.println("ms:"+ms);

        success(bean.getData());

    }
    }
















