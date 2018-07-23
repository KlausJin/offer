package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ImportExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-19  9:30
 * @Version 1.0
 **/
public class ProductController extends Controller {
    public ProductController(ControllerContext context) {
        super(context);
    }

    @action
    public void list(){
        toHtml("admin_tpl/product_list");
    }



}
