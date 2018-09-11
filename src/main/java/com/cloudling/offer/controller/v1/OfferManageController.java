package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.Bean;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.model.OfferModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

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
        toHtml("admin_tpl/offer_manage_list");
    }

    @action
    public void getManageOfferList() {
        String page = I("get.page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        HashMap<Integer, String> statusTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put(Dictionary.ISOFFER, "已报价");
                put(Dictionary.NOOFFER, "待报价");
            }
        };
        String sql="select a.*,b.name as client_name,c.name as sale_name from offer a left join client b on a.client_id=b.id left join person c on a.sale_id=c.id";
        StringBuffer sb=new StringBuffer(sql);
        String sqls=sb.append(" limit "+limit).toString();
        ArrayList<HashMap<String, String>> list = M("offer").query(sqls);
        for (int i=0;i<list.size();i++){
            list.get(i).put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            list.get(i).put("status",statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
        }
        res.put("list", list);
        res.put("num", list.size());
        success(res);
    }

    /**
     * @Description: 查看报价详情
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void detail(){

    }

    /**
     * @Description: 渲染到经理做报价的界面
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void edit(){
        String offer_id=I("id").toString();
        assign("offer_id",offer_id);
        toHtml("admin_tpl/do_offer");
    }



    /**
     * 产品属性渲染到模板(固定配件)
     *
     * @param
     */
    @action
    public void getProduct() {
       long s =TimeUtil.getLongTimeStamp();
        String id = I("id") == null ? "" : I("id").toString();
        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean(id);
        HashMap<String, Object> b =t.getData();
        success(b);
        long ms =TimeUtil.getLongTimeStamp()-s;
        System.out.println("-------------------"+ms+"ms");

    }
    /**
     * 产品属性渲染到模板(常用配件)
     *
     * @param
     */
    @action
    public void getProduct_real(){
        String id = I("id") == null ? "" : I("id").toString();
        OfferModel offerModel = new OfferModel();
        Bean t=offerModel.getProductBean_real(id);
        HashMap<String, Object> b =t.getData();
        success(b);
    }

}
