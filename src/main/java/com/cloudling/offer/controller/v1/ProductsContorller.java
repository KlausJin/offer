package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.HashMap;

public class ProductsContorller extends Controller {
    public ProductsContorller(ControllerContext context) {
        super(context);
    }

    @action
    public void edit_product() {
        String id = I("post.id") == null ? "" : I("post.id").toString();
        String code = I("post.code") == null ? "" : I("post.code").toString();
        String price = I("post.price") == null ? "" : I("post.price").toString();
        M("product").where("code")
        if(code)
        HashMap<String, String> res = new HashMap<>();
        res.put("code", code);
        res.put("price", price);
        try {
            M("product").where("id=" + id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("参数修改错误");

        }
    }
}
