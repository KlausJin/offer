package com.cloudling.offer.model;

import com.cloudling.offer.bean.ClientBean;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.util.TimeUtil;
import com.mysql.fabric.xmlrpc.Client;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientModel extends  Model {
    public ClientModel() {
        super("client");
    }

    public ArrayList list(String condition) {
        return where(condition).select();
    }

    public HashMap<String, String> listone(String condition) {
        return where(condition).find();
    }

    public ArrayList list() {
        return select();
    }

    public ArrayList<HashMap<String, String>> sear_cli(ClientBean bean) {
        String limit = 10 + ",10";
        String sql ="select* from part_cat where name like %" + bean.sear_cli + "% limit " + limit;
        ArrayList<HashMap<String,String>> list=query(sql);
        return list;
    }
public void add_cli(ClientBean bean) throws Exception {
    bean.create_time= TimeUtil.getShortTimeStamp();
    add(bean);
}
public ArrayList<HashMap<String, String>> cli_list(){
        String limit = 10 + ",10";
        String sql="select * from client limit "+limit;
    ArrayList<HashMap<String, String>> list = query(sql);
    for(int i=0;i<list.size();i++){

        list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy年 MM月 dd日"));
    }
    return list;
}
}
