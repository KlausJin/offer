package com.cloudling.offer.model;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferSpareModel extends Model {
    public OfferSpareModel() {
        super("offer_spare");


    }

public ArrayList<HashMap<String, String>> getSpareByProductId (String product_id){
        return where("product_id="+product_id).select();
}
}
