package com.cloudling.offer.model;

import com.cloudling.offer.bean.ProductBean;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  16:02
 * @Version 1.0
 **/
public class ProductModel extends Model {


    public ProductModel(String table) {
        super("product");
    }


    /**
     * @Description: 根据关键词搜索产品
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public List<HashMap<String,String>> search(String key){
        return null;
    }

    /**
     * @Description:根据产品id获取产品bean
     * @param: 产品id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public ProductBean getBean(String id){
        return null;
    }



}
