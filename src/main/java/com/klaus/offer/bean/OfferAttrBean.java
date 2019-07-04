package com.klaus.offer.bean;

import java.util.HashMap;

public class OfferAttrBean extends Bean {
    public OfferAttrBean(HashMap<String,String> data){
        super(data);
    }
    public String num;
    public String attr_name,parent_name,spare_name;
}
