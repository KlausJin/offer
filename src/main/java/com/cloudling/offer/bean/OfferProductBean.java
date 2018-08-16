package com.cloudling.offer.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferProductBean extends Bean {
    public OfferProductBean(HashMap<String,String> data){
        super(data);
    }
    public String id;
    public int offer_id;
    public String product_id;
    public int num;
    public int attr_id;
    public ArrayList<HashMap<String, String>> offersapreBeans;
}
