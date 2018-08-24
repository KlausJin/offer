package com.cloudling.offer.model;

import com.cloudling.offer.bean.OfferProductBean;
import com.cloudling.offer.bean.SpareBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferProductModel extends Model {
    public OfferProductModel() {
        super("offer_product");
    }
public HashMap<String,String>  getProductByOfferId(String id){
       return where("offer_id="+id).find();
}
}
