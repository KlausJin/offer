package com.cloudling.offer.model;

import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.bean.SpareBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  16:02
 * @Version 1.0
 **/
public class ProductModel extends Model {


    public ProductModel(){
        super("product");
    }


    /**
     * @Description: 根据关键词搜索产品
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public ArrayList<HashMap<String,String>> search(ProductBean productBean){
        String limit = 10 + ",10";
        String sql="select code,id from product where code  like %" + productBean.getCode()+ "% limit "+limit;
        ArrayList<HashMap<String, String>> list = query(sql);
        return list;
    }

    /**
     * @Description:根据产品id获取产品bean(固定配件)
     * @param: 产品id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public ProductBean getBean(String id){

        HashMap<String, String> map = where("id=" + id).find();
        ProductBean bean = new ProductBean(map);

        bean.spareBeans = new SpareModel().getBeansByProductId(bean.id);


        return bean;
    }

/**
 * @Description:根据产品id获取产品bean（实时配件)
 * @param: 产品id
 * @return:
 * @auther: CodyLongo
 * @modified:
 */

public ProductBean getBean_real(String id){
    HashMap<String, String> map = where("id=" + id).find();
    ProductBean bean = new ProductBean(map);

    bean.spareBeans = new SpareModel().getBeansByProductId_real(bean.id);


    return bean;
}
}
