package com.cloudling.offer.model;

import com.cloudling.offer.bean.SpareBean;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  16:07
 * @Version 1.0
 **/
public class SpareModel extends Model {

    public SpareModel() {
        super("spare");
    }

    /**
     * @Description:根据分类id获取所有的通用配件（product_id ！= 0）
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public ArrayList<HashMap<String, String>> getSpares(String cat_id){
        ArrayList<HashMap<String, String>> list = where("cat_id =" +0).field("name,product_id,id").select();
        return list;
    }


    public List<SpareBean> getBeansByProductId(String product_id){

        List<SpareBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map = getSparesByProductId(product_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res =map.get(i);

            SpareBean bean =new SpareBean( res);
            bean.attrBeans= new AttrModel().getlistBySpareId(bean.id);
            list.add(bean);
        }
        return list;
    }

    public List<SpareBean> getBeansByProductId1(String product_id,String offer_id,String cat_id,String offer_product_id){
        List<SpareBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map = getSparesByProductId(product_id); //根据product_id 查找spare

        for (int i=0;i<map.size();i++){                                          //遍历找出的spare
            HashMap<String,String> res =map.get(i);
            SpareBean bean =new SpareBean( res);                                 //放入bean
            bean.attrBeans= new AttrModel().getlistBySpareId1(bean.id,offer_id,product_id,cat_id,offer_product_id); //传值到下一层
            list.add(bean);
        }
        return list;
    }
    /**
     * @Description:根据产品id获取所有的配件
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     * @param product_id
     */

    public ArrayList<HashMap<String, String>> getSparesByProductId(String product_id){

        return where("product_id="+product_id).select();
    }

    /**
     * @Description:根据分类id获取所有的实时配件（product_id = 0）
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     * @param cat_id
     */
    public List<SpareBean> getBeansByCat_id_real(String cat_id){

        List<SpareBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map = getSparesByProductId_real(cat_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res =map.get(i);
            SpareBean bean =new SpareBean( res);
            bean.attrBeans= new AttrModel().getlistBySpareId(bean.id);
            list.add(bean);
        }
        return list;
    }
    public List<SpareBean> getBeansByCat_id_real1(String product_id,String offer_id,String offer_product_id){

        List<SpareBean> list =new ArrayList<>();
        OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id,offer_product_id);
        ArrayList<HashMap<String, String>> map = offerAttrModel.getSpareByOn(product_id,offer_id);//查到选择的常用配件（attr_id=on）

        for (int i=0;i<map.size();i++){
            HashMap<String,String> res=getSpare_real(map.get(i).get("spare_id")); //根据spareid（offerattr表）差 （spare表）id
            SpareBean bean =new SpareBean( res);
            bean.attrBeans= new AttrModel().getSpareId_real(bean.id,offer_id,product_id,offer_product_id);
            list.add(bean);
        }
        return list;
    }
    public ArrayList<HashMap<String, String>> getSparesByProductId_real(String cat_id){

        return where("product_id="+0+" and cat_id="+cat_id).select();
    }
    public HashMap<String, String> getSparesBySpareId(String spare_id){

        return where("id="+spare_id).find();
    }


    /**
     * 删除指定的配件
     * @param id
     */
    public void removeSpareById(String id){

        where("id ="+id).delete();
        AttrModel attrModel = new AttrModel();

        attrModel.removeAttrBySpareId(id);

    }

    public ArrayList<HashMap<String,String>> getSpareBySpareId(String spare_id){
        ArrayList<HashMap<String, String>> list = where("id=" + spare_id).select();
        return list;
    }

    public HashMap<String,String> getSpare_real(String spare_id){
       return where("id="+spare_id).find();
    }

}


