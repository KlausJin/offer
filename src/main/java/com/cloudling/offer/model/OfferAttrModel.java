package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferAttrModel extends  Model {

    List<HashMap<String,String>> list;
    HashMap<String,Object> spares = new HashMap<>();

    static  HashMap<String,OfferAttrModel> models = new HashMap<>();

    public static OfferAttrModel getInstance(String offer_id,String offer_product_id){
        if(models.containsKey(offer_id)) return models.get(offer_id);
        else {
            OfferAttrModel model = new OfferAttrModel(offer_id,offer_product_id);
            models.put(offer_id,model);
            return  model;
        }

    }


    private OfferAttrModel(String offer_id,String offer_product_id) {
        super("offer_attr");
        list = where("offer_id="+offer_id +" and offer_product_id="+offer_product_id).select();
        for(int i=0;i<list.size();i++){
            spares.put(list.get(i).get("spare_id"),list.get(i));
        }
    }
    public HashMap<String,String> getMapBySpareId(String spare_id){

        return (HashMap<String, String>) spares.get(spare_id);
    }
    public HashMap<String,String> getOfferAttrBySpareId(String spare_id, String offer_id,String offer_product_id){
        return where("spare_id="+spare_id+" and offer_id="+offer_id+" and offer_product_id="+offer_product_id).find();
    }

    public ArrayList<HashMap<String, String>> getSpareByOn(String offer_id,String offer_product_id){
        return where("offer_id="+offer_id+" and attr_id="+"'on'"+" and offer_product_id="+offer_product_id).select();

    }
}
