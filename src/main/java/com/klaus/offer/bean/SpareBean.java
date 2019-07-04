package com.klaus.offer.bean;

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
     public  String product_id;

     public String name;

     public boolean is_float;

     public  int cat_id;

     public int create_time;

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


    public void setName(String name) {
        this.name = name;
    }

   public  List<AttrBean> attrBeans;





}
