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

    public String id;

    public String name;

    public int spare_id;

    public   float price;

    String attr;


    public  String num;

    public AttrBean(HashMap<String,String> data) {
        super(data);
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public int parent_id;

    public String code;

    public int formula;

    public int relate_id;

    public int or_id;



   public List<AttrBean> f_attrBeans;

    public List<AttrBean> getF_attrBeans() {
        return f_attrBeans;
    }

    public void setF_attrBeans(List<AttrBean> f_attrBeans) {
        this.f_attrBeans = f_attrBeans;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
