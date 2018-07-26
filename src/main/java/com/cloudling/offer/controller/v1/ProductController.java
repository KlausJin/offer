package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ExceltoBeanUtil;

import java.util.List;

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

    @action
    public void add_product(){
        toHtml("admin_tpl/add_product");
    }

    @action
    public void do_add_product() {
        String url = I("url").toString();
        try {
            List<ProductBean> ls =ExceltoBeanUtil.analysisExcel("assets/" + url + "");
            System.out.println(JSON.toJSONString(ls));
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
        }
    }


}
