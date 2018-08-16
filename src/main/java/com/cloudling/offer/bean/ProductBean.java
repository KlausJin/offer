package com.cloudling.offer.bean;


import com.cloudling.offer.annotation.required;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  15:52
 * @Version 1.0
 **/
public class ProductBean extends Bean {

    public ProductBean(HashMap<String,String> data){
        super(data);
    }
    public int cat_id;
    public String id;

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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    int create_time;


    public List<SpareBean> spareBeans;

}
