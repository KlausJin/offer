package com.cloudling.offer.model;

import com.cloudling.offer.bean.PersonBean;
import com.cloudling.offer.util.Md5Util;
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
    public PersonModel()
    {
        super("person");
    }

    public void add_person(PersonBean bean) throws Exception {
        bean.create_time = TimeUtil.getShortTimeStamp();
        bean.password=Md5Util.MD5(bean.password);
        add(bean);
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

    public ArrayList<HashMap<String, String>>  list(String condition,String limit)
    {
        return where(condition).limit(limit).select();
    }

    public ArrayList<HashMap<String, String>>  list(String condition)
    {
        return where(condition).select();
    }

    public HashMap<String,String> listone(String condition)
    {
        return where(condition).find();
    }

    public ArrayList<HashMap<String, String>>  list()
    {
        return select();
    }


}
