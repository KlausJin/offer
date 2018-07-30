package com.cloudling.offer.bean;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.required;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferBean extends Bean {
    public OfferBean(ControllerContext context){
        super(context);
    }


    @required
    public String sale_id,man_id,status;




    public String requires;
    public int create_time,offer_time;
    public String search_name;
    public String  search_pro;
    public String id;

}

