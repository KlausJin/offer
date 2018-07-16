package com.cloudling.offer.model;

import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  14:47
 * @Version 1.0
 **/
public class PartsModel extends Model {
    public PartsModel(String table) {
        super(table);
    }

    public void add_parts(HashMap<String, String> map){
        try {
            map.put("create_time",TimeUtil.getShortTimeStamp()+"");
            add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> list() {
        return select();
    }
}
