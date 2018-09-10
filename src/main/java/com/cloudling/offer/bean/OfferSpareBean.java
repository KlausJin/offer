package com.cloudling.offer.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferSpareBean extends Bean {
    public OfferSpareBean(HashMap<String,String> data){
        super(data);
    }
   public String id;
    public String  offer_id;
    public String spare_id;
    public int attr_id;
    public  int price;
    public int num;
    public int spare_count;
    public int product_id;
    public ArrayList<HashMap<String, String>> f_offersapreBeans;

}
