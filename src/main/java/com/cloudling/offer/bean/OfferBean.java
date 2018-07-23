package com.cloudling.offer.bean;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferBean extends Bean {
    public OfferBean(ControllerContext context){
        super(context);
    }
   public String sale_id,man_id,offer_code,status,prod_name,part_name,num;
    public String content;
    public String require;
    public int create_time,offer_time;
    public String search_name;
    public String  search_pro;
    public String id;

}

