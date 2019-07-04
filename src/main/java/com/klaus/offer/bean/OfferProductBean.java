package com.klaus.offer.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferProductBean extends Bean {
    public OfferProductBean(HashMap<String,String> data){
        super(data);
    }
    public String id;
    public int offer_id;
    public String product_id;
    public String num;
    public int attr_id;
    public String url;
    public String attr_name,parent_name,spare_name;
    public ArrayList<HashMap<String, String>> offersapreBeans;
    public ArrayList<HashMap<String, String>> offerAttrBeans;
}
