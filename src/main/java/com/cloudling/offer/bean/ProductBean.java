package com.cloudling.offer.bean;


import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  15:52
 * @Version 1.0
 **/
public class ProductBean extends Bean {

    String code;

    public float getPrice() {
        return price;
    }

    public List<SpareBean> getSpareBeans() {
        return spareBeans;
    }

    public void setSpareBeans(List<SpareBean> spareBeans) {
        this.spareBeans = spareBeans;
    }

    public void setPrice(float price) {

        this.price = price;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    float price;

    int create_time;

    List<SpareBean> spareBeans;


}
