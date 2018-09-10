package com.cloudling.offer.model;

import com.cloudling.offer.bean.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  16:02
 * @Version 1.0
 **/
public class ProductModel extends Model {


    public ProductModel(){
        super("product");
    }


    /**
     * @Description: 根据关键词搜索产品
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public ArrayList<HashMap<String,String>> search(ProductBean productBean){
        String limit = 10 + ",10";
        String sql="select code,id from product where code  like %" + productBean.getCode()+ "% limit "+limit;
        ArrayList<HashMap<String, String>> list = query(sql);
        return list;
    }



    /**
     * @Description:根据产品id获取产品bean(固定配件)
     * @param: 产品id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public ProductBean getBean(String id){

        HashMap<String, String> map = where("id=" + id).find();
        ProductBean bean = new ProductBean(map);
        bean.spareBeans = new SpareModel().getBeansByProductId(bean.id);

        if (map == null){
            return null;
        }
        else{

            return bean;
        }
    }

    public List<ProductBean> getBean1(String offer_id){
        OfferProductModel offerProductModel=new OfferProductModel();
        ArrayList<HashMap<String, String>> res = offerProductModel.getProductByOfferId(offer_id);
        List<ProductBean> list =new ArrayList<>();
        for (int i=0;i<res.size();i++){
            HashMap<String, String> map= where("id="+res.get(i).get("product_id")).find();
            ProductBean bean = new ProductBean(map);
            bean.spareBeans = new SpareModel().getBeansByProductId1(bean.id,offer_id);
            list.add(bean);

        }
        return list;

    }





    /**
     * @Description:根据产品id获取产品bean（实时配件)
     * @param: 产品id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public ProductBean getBean_real(String id){
        HashMap<String, String> map = where("id=" + id).find();
        ProductBean bean = new ProductBean(map);

        bean.spareBeans = new SpareModel().getBeansByCat_id_real(bean.cat_id);

        if ( map ==null){ return null; }
        else{    return bean;}

    }






    public List<ProductBean> getBean_real1(String offer_id){
        OfferProductModel offerProductModel=new OfferProductModel();
        List<ProductBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>>res = offerProductModel.getProductByOfferId(offer_id);
        for (int i=0;i<res.size();i++){
            HashMap<String, String> map= where("id="+res.get(i).get("product_id")).find();
            ProductBean bean = new ProductBean(map);

            bean.spareBeans = new SpareModel().getBeansByCat_id_real1(offer_id);
            list.add(bean);

        }


        return list;

    }



    /**
     * @Description:根据产品id获取产品（实时配件)
     * @param: 产品id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */


    public HashMap<String,String> getCatId(String product_id){
        return where("id="+product_id).find();
    }

}
