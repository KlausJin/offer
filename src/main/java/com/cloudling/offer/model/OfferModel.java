package com.cloudling.offer.model;

import com.cloudling.offer.bean.OfferBean;

import java.util.HashMap;
import java.util.IdentityHashMap;

public class OfferModel extends Model {
    public OfferModel()
    {
        super("offer");
    }


    public OfferBean getProductBean(String offer_id){
        HashMap<String, String> map = where("id=" + offer_id).find();
        OfferBean bean= new OfferBean(map);
        ProductModel productModel= new ProductModel();
        bean.productBeans=productModel.getBean1(bean.id);
        if (map == null){
            return null;
        }
        else{

            return bean;
        }
    }

   public OfferBean getProductBean_real(String offer_id){
       HashMap<String, String> map = where("id=" + offer_id).find();
       OfferBean bean= new OfferBean(map);
       ProductModel productModel= new ProductModel();
       bean.productBeans=productModel.getBean_real1(bean.id);
        if (map == null){
           return null;
       }
      else{
           return bean;
        }
    }
    public OfferBean getProductBean_sale(String offer_id){
        HashMap<String, String> map = where("id=" + offer_id).find();
        OfferBean bean= new OfferBean(map);
        OfferProductModel offerProductModel= new OfferProductModel();
        bean.offerproductBeans=offerProductModel.getBean_sale(bean.id);
        if (map == null){
            return null;
        }
        else{

            return bean;
        }
    }
}




