package com.klaus.offer.model;

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

    public ArrayList<HashMap<String, String>> getSelect_sale(String offer_product_id){
        ArrayList<HashMap<String, String>> list = where("offer_product_id=" + offer_product_id +" and attr_id != -1 and attr_id != 'on'").select();
        ArrayList<HashMap<String,String>> c_data =new ArrayList<>();
        for (int i=0;i<list.size();i++){
            HashMap<String,String> res = getPut(i,offer_product_id);
            c_data.add(res);


        }
        return c_data;
    }
public HashMap<String,String> getPut(int i,String offer_product_id){
       AttrModel attrModel=new AttrModel();
       SpareModel spareModel=new SpareModel();
       ArrayList<HashMap<String, String>> list = where("offer_product_id=" + offer_product_id +" and attr_id != -1 and attr_id != 'on'").select();
        HashMap<String, String> map_attr = attrModel.getAttr_sale(list.get(i).get("attr_id"));
        HashMap<String, String> map_parent = attrModel.getParent_sale(list.get(i).get("spare_id"));
        HashMap<String, String> map_spare = spareModel.getSpare_real(map_parent.get("spare_id"));
        HashMap<String,String> res =new HashMap<>();
        res.put("attr_name",map_attr.get("name"));
        res.put("parent_name",map_parent.get("name"));
        res.put("spare_name",map_spare.get("name"));
        res.put("num",list.get(i).get("num"));
        return  res;


    }
}

