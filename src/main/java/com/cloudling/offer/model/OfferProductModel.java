package com.cloudling.offer.model;


import com.cloudling.offer.bean.OfferProductBean;
import com.cloudling.offer.bean.ProductBean;
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
    public void addCBM(String CBM,String product_id,String offer_id,String packing){
        HashMap<String,Object> res=new HashMap<>();
        res.put("cbm",CBM);
        res.put("packing",packing);
        try {
            where("product_id="+product_id+" and offer_id="+offer_id).save(res);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    public HashMap<String,String> getCBM(String offer_id,String product_id){
        return where("offer_id="+offer_id+" and product_id="+product_id).find();
    }
public  HashMap<String,String> getProduct(String offer_product_id){
       return where("id="+offer_product_id).find();
}
    public List<OfferProductBean> getBean_sale(String offer_id){
        ArrayList<HashMap<String, String>> res = where("offer_id=" + offer_id).select();
        List<OfferProductBean> list =new ArrayList<>();
        for (int i=0;i<res.size();i++){
            HashMap<String, String> f_map = res.get(i);
            OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id,res.get(i).get("id"));
            OfferProductBean bean = new OfferProductBean(f_map);
            bean.offerAttrBeans= offerAttrModel.getSelect_sale(bean.id);
            list.add(bean);

        }
        return list;

    }
}
