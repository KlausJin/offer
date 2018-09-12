package com.cloudling.offer.model;


import com.cloudling.offer.bean.SpareBean;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferProductModel extends Model {
    public OfferProductModel() {
        super("offer_product");
    }
    public ArrayList<HashMap<String,String>>  getProductByOfferId(String id){
        return where("offer_id="+id).select();
    }
    public void addCBM(String CBM,String product_id,String offer_id){
        HashMap<String,Object> res=new HashMap<>();
        res.put("cbm",CBM);
        try {
            where("product_id="+product_id+" and offer_id="+offer_id).save(res);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    public HashMap<String,String> getCBM(String offer_id,String product_id){
        return where("offer_id="+offer_id+" and product_id="+product_id).find();
    }

}
