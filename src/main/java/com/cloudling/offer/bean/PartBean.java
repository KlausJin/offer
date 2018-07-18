package com.cloudling.offer.bean;

import com.cloudling.offer.annotation.required;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:28
 * @Version 1.0
 **/
public class PartBean extends Bean {

    public PartBean(ControllerContext context){
        this.context = context;
    }

    public PartBean(){

    }

    @required
    public String name,cat_id;

    public String cost;






}
