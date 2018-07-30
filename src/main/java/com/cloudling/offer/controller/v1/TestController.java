package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.bean.SpareBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.model.SpareModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;

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

        ProductModel productModel = new ProductModel();

        ProductBean bean = productModel.getBean_real("47");

        success(bean.getData());

    }
@action
    public void do_add_spare(){
        SpareBean bean;
        SpareModel sparetModel = new SpareModel();
        try {
            bean= (SpareBean) BeanUtil.getBean("spare",context,BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{


            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("录入模版失败");
        }
    }

}



















