package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferAttrModel extends  Model {
    public OfferAttrModel() {
        super("offer_attr");
    }
    public HashMap<String,String> getMapBySpareId(String spare_id, String offer_id){
        HashMap<String,String> map = where("spare_id=" + spare_id +" and offer_id="+offer_id ).find();
        return map;
    }
    public ArrayList<HashMap<String,String>> getSpareByNum(String spare_id){
        ArrayList<HashMap<String,String>> list=where("spare_id ="+spare_id).select();
        return list;
    }
    public ArrayList<HashMap<String, String>> getSpareByOn(String product_id,String offer_id){
        return where("offer_id="+offer_id+" and attr_id="+"'on'"+" and product_id="+product_id).select();

    }
}
