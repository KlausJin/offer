package com.cloudling.offer.model;

import com.cloudling.offer.bean.SpareBean;

import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-24  16:07
 * @Version 1.0
 **/
public class SpareModel extends Model {

    public SpareModel(String table) {
        super("spare");
    }

    /**
     * @Description:根据分类id获取所有的通用配件（product_id = 0）
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public List<SpareBean> getSpares(String cat_id){
        return null;
    }


    /**
     * @Description:根据产品id获取所有的配件
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public List<SpareBean> getSparesByProductId(String product_id){
        return null;
    }


}
