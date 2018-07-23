package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  17:21
 * @Version 1.0
 **/
public class OffersModel extends Model{
    public OffersModel() {
        super("offer");
    }


    public ArrayList<HashMap<String, String>> list(String condition)
    {
        return where(condition).select();
    }

    public ArrayList<HashMap<String, String>> list()
    {
        return select();
    }

    public ArrayList<HashMap<String, String>> queryOffer(String sql){
        return query(sql);
    }
}
