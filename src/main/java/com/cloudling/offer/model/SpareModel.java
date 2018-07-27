package com.cloudling.offer.model;

import com.cloudling.offer.bean.SpareBean;

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
     * @Description:根据分类id获取所有的通用配件（product_id = 0）
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
            HashMap<String,String> res =where("id ="+map.get(i).get("id")).find();

           SpareBean bean =new SpareBean( res);
           bean.attrBeans= new AttrModel().getlistBySpareId(bean.id);
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


}
