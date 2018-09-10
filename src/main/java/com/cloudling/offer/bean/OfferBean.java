
package com.cloudling.offer.bean;

import java.util.HashMap;
import java.util.List;

    public class OfferBean extends  Bean{
        public OfferBean(HashMap<String,String> data){
            super(data);
        }
       public String id,sale_id,m_id,client_id,status;
        public List<ProductBean> productBeans;


}

