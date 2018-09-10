package com.cloudling.offer.model;

import com.cloudling.offer.bean.OfferProductBean;
import com.cloudling.offer.bean.OfferSpareBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferSpareModel extends Model {
    public OfferSpareModel() {
        super("offer_spare");


    }

    public List<OfferSpareBean> getSpareBean(String product_id){
        List<OfferSpareBean> list =new ArrayList<>();
        ArrayList<HashMap<String,String>> map=getSpareByProductId(product_id);
        for (int i=0;i<map.size(); i++){
            HashMap<String,String> res=map.get(i);
            OfferSpareBean bean =new OfferSpareBean(res);
            bean.f_offersapreBeans=new SpareModel().getSpareBySpareId(bean.spare_id);
            list.add(bean);
        }
        return list;
    }
    public ArrayList<HashMap<String, String>> getSpareByProductId (String product_id){
        return where("product_id="+product_id).select();
    }
}
