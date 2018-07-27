package com.cloudling.offer.bean;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  15:55
 * @Version 1.0
 **/
public class AttrBean extends Bean {

    public int id;

    String name;

    int spare_id;

    float price;

    String num;

    String attr;

    public AttrBean(HashMap<String,String> data) {
        super(data);
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    int parent_id;

    String code;

 

    List<AttrBean> attrBeans;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<AttrBean> getAttrBeans() {

        return attrBeans;
    }

    public void setAttrBeans(List<AttrBean> attrBeans) {
        this.attrBeans = attrBeans;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
