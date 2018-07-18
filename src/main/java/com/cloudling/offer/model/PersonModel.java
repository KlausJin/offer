package com.cloudling.offer.model;

import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  15:20
 * @Version 1.0
 **/
public class PersonModel extends Model {
    public PersonModel(String table) {
        super(table);
    }

    public ArrayList list(String condition){
        return where(condition).select();
    }
    public ArrayList list(){
        return select();
    }

    public void add_person(HashMap<String, String> map){
        try {
            map.put("create_time",TimeUtil.getShortTimeStamp()+"");
            add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete_person(int id){
        where("id="+id).delete();
    }

    public int update_pwd(int id,String pwd){
        String sql="update person set password='"+pwd+"' where id="+id;
        int t=execute(sql);
        return t;
    }

    public void update(int id,HashMap<String, String> map){
        where("id="+id).save_string(map);
    }
}
