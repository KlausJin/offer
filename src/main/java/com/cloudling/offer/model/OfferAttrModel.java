package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferAttrModel extends  Model {

    List<HashMap<String,String>> list;
    HashMap<String,Object> spares = new HashMap<>();

    static  HashMap<String,OfferAttrModel> models = new HashMap<>();

    public static OfferAttrModel getInstance(String offer_id){
        if(models.containsKey(offer_id)) return models.get(offer_id);
        else {
            OfferAttrModel model = new OfferAttrModel(offer_id);
            models.put(offer_id,model);
            return  model;
        }

    }


    private OfferAttrModel(String offer_id) {
        super("offer_attr");
        list = where("offer_id="+offer_id).select();
        for(int i=0;i<list.size();i++){
            spares.put(list.get(i).get("spare_id"),list.get(i));
        }
    }
    public HashMap<String,String> getMapBySpareId(String spare_id, String offer_id){

        return (HashMap<String, String>) spares.get(spare_id);
    }
    public ArrayList<HashMap<String,String>> getSpareByNum(String spare_id){
        ArrayList<HashMap<String,String>> list=where("spare_id ="+spare_id).select();
        return list;
    }
    public ArrayList<HashMap<String, String>> getSpareByOn(String product_id,String offer_id){
        return where("offer_id="+offer_id+" and attr_id="+"'on'"+" and product_id="+product_id).select();

    }
}
