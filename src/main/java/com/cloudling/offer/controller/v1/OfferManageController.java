package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-03  14:35
 * @Version 1.0
 **/
public class OfferManageController extends AdminController {


    public OfferManageController(ControllerContext context) {
        super(context);
    }


    @action
    public void list(){

    }

    /**
     * 产品属性渲染到模板(固定配件)
     *
     * @param
     */
    @action
    public void getproduct(){
        String id = I("id") == null ? "" : I("id").toString();
        ProductModel productModel =new ProductModel();
        success(productModel.getBean1(id).getData());
    }

}
