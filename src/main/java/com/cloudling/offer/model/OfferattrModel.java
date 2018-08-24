package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferattrModel extends  Model {
    public OfferattrModel() {
        super("offer_attr");
    }
    public ArrayList<HashMap<String,String>> getMapBySpareId(String spare_id, String offer_id){
        ArrayList<HashMap<String,String>> map = where("spare_id=" + spare_id +" and offer_id="+offer_id).select();
        return map;
    }

}
