package com.cloudling.offer.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  15:53
 * @Version 1.0
 **/
public class SpareBean extends Bean {

    public SpareBean(HashMap<String,String> data){
        super(data);
    }

    public String id;
    int product_id;

    String name;

    boolean is_float;

    int cat_id;

    int create_time;

    public SpareBean(String sparesByProductId) {
    }

    public List<AttrBean> getAttrBeans() {
        return attrBeans;
    }

    public void setAttrBeans(List<AttrBean> attrBeans) {
        this.attrBeans = attrBeans;
    }

    public String getName() {

        return name;
    }
    public int getProduct_id(){
        return product_id;
    }

    public void setName(String name) {
        this.name = name;
    }

   public  List<AttrBean> attrBeans;





}
