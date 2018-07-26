package com.cloudling.offer.model;

import com.cloudling.offer.bean.PartBean;
import com.cloudling.offer.bean.PartCatBean;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  14:47
 * @Version 1.0
 **/
public class PartCatModel extends Model {
    public PartCatModel() {

        super("part_cat");
    }

    public void add_part_cat(PartCatBean bean) throws Exception {
        bean.create_time=TimeUtil.getShortTimeStamp();
        add(bean);
    }

    public void delete_part_cat(int id){
        where("id="+id).delete();
    }

    public void update(int id,HashMap<String, String> map){
        where("id="+id).save_string(map);
    }


    public ArrayList<HashMap<String, String>> list(String condition)
    {
        return where(condition).select();
    }

    public HashMap<String,String> listone(String condition)
    {
        return where(condition).find();
    }

    public ArrayList<HashMap<String, String>> list()
    {
        return select();
    }

    public ArrayList<HashMap<String, String>> queryPartCat(String sql){
        return  query(sql);
    }
}