package com.cloudling.offer.bean;

import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  15:53
 * @Version 1.0
 **/
public class SpareBean extends Bean {

    int product_id;

    String name;

    boolean is_float;

    int cat_id;

    int create_time;

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

    List<AttrBean> attrBeans;





}
