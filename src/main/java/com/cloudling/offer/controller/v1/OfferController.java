package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.Bean;
import com.cloudling.offer.bean.ClientBean;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.bean.PictureBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.ClientModel;
import com.cloudling.offer.model.OfferModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.model.PictureModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.JsonUtil;
import com.cloudling.offer.util.TimeUtil;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferController  extends Controller {

    public OfferController(ControllerContext context) {
        super(context);
    }
    //添加模板
    @action
    public void add_offer(){
        toHtml("admin_tpl/offer");
    }
    @action
    public void do_add_offer(){
        OfferBean bean;
        OfferModel offerModel = new OfferModel();
        try {
            bean= (OfferBean) BeanUtil.getBean("offer",context,BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{

            offerModel.add_offer(bean);
            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("录入模版失败");
        }
    }
    //搜模板
@action
public void sear_offer(){
        toHtml("");

    }
@action
    public void do_sear_offer() {
    OfferBean bean;
    OfferModel offerModel = new OfferModel();
    try {
        bean= (OfferBean) BeanUtil.getBean("offer",context,BeanUtil.POST);
    } catch (ParamsErrorException e) {
        e.printStackTrace();
        error(e.getMessage());
        return;
    }


        try {
            ArrayList<HashMap<String, String>> list = offerModel.sear_offer(bean);
            assign("list",list);
            success(1);
        } catch (Exception e) {
            e.printStackTrace();
            error("参数错误");
        }

    }
//搜产品
@action
public void sear_prod(){
        toHtml("");
}
@action
public void do_sear_prod(){
    OfferBean bean;
    OfferModel offerModel = new OfferModel();
    try {
        bean= (OfferBean) BeanUtil.getBean("offer",context,BeanUtil.POST);
    } catch (ParamsErrorException e) {
        e.printStackTrace();
        error(e.getMessage());
        return;
    }
    try {
    ArrayList<HashMap<String, String>> map = offerModel.sear_offer(bean);
    assign("map",map);
    success(1);
    } catch (Exception e) {
        e.printStackTrace();
        error("参数错误");
    }


}
//选择模板
@action
public  void  sel_offer(){
    OfferBean bean;
    OfferModel offerModel = new OfferModel();
    try {
        bean= (OfferBean) BeanUtil.getBean("offer",context,BeanUtil.POST);
    } catch (ParamsErrorException e) {
        e.printStackTrace();
        error(e.getMessage());
        return;

    }
    try {
       offerModel.sel_offer(bean);
       success(1);
    } catch (Exception e) {
        e.printStackTrace();
        error("参数错误");
    }

}


//选择添加



    //直接添加
    @action
    public void sel_pro2(){
        OfferBean bean;
        OfferModel offerModel = new OfferModel();
        try {
            bean= (OfferBean) BeanUtil.getBean("offer",context,BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;

        }
        HashMap<String, String> map = offerModel.add_part(bean);
        success(map);
    }
    //选择客户
    @action
    public void sel_client(){
        ClientModel clientModel =new ClientModel();
        ArrayList<HashMap<String,String>> map =clientModel.cli_list();
        assign("map",map);
        toHtml("");
    }
    @action
    public void do_sel_client() {
        ClientBean bean;
        ClientModel clientModel = new ClientModel();
        try {
            bean = (ClientBean) BeanUtil.getBean("client", context, BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try {
            ArrayList<HashMap<String, String>> map = clientModel.sear_cli(bean);
            assign("client", map);
            success("1");

        } catch (Exception e) {
            e.printStackTrace();
            error("参数错误");
        }
    }
    @action
    public void add_client(){

        toHtml("");
    }
    @action
    public void do_add_client(){
        ClientBean bean;
        ClientModel clientModel = new ClientModel();
        try {
            bean = (ClientBean) BeanUtil.getBean("client", context, BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{

            clientModel.add_cli(bean);
            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("录入模版失败");
        }
    }
    //备注图片
@action
    public void add_picture(){
        PictureModel pictureModel =new PictureModel();
        ArrayList<HashMap<String,String>> map = pictureModel.pic_list();
        assign("map",map);
        toHtml("");
}
//上传备注图片
@action
    public void do_add_picture(){
    PictureBean bean;
    PictureModel pictureModel=new PictureModel();
    try {
        bean = (PictureBean) BeanUtil.getBean("picture", context, BeanUtil.POST);
    } catch (ParamsErrorException e) {
        e.printStackTrace();
        error(e.getMessage());
        return;

    }
    try {
        pictureModel.add_pic(bean);
        success("1");
    } catch (Exception e) {
        e.printStackTrace();
        error("录入模版失败");
    }

}

}

