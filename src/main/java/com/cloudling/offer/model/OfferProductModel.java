package com.cloudling.offer.model;

import com.cloudling.offer.bean.OfferProductBean;
import com.cloudling.offer.bean.SpareBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferProductModel extends Model {
    public OfferProductModel(){
        super("offer_product");
    }

    public List<OfferProductBean> getBean(String offer_id){
        List<OfferProductBean> list =new ArrayList<>();
        ArrayList<HashMap<String,String>> map=getProductByOfferId(offer_id);
        for (int i=0;i<map.size(); i++){
                HashMap<String,String> res=map.get(i);
                OfferProductBean bean =new OfferProductBean(res);
                bean.offersapreBeans=new ProductModel().getProductByProductId(bean.product_id);
                list.add(bean);
        }
        return list;
    }
    public ArrayList<HashMap<String,String>> getProductByOfferId(String offer_id){
        return    where("offer_id=" + offer_id).select();
    }
}
