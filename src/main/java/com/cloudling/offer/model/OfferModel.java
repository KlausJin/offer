package com.cloudling.offer.model;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferModel extends Model {
    public OfferModel()
    {
        super("offer");
    }

    public ArrayList list(String condition)
    {
        return where(condition).select();
    }

    public HashMap<String,String> listone(String condition)
    {
        return where(condition).find();
    }

    public ArrayList list()
    {
        return select();
    }

    public void add_offer(OfferBean bean) throws Exception {
        HashMap<String,String> list=new HashMap<>();
        list.put("prod_name",bean.prod_name);
        list.put("part_name",bean.part_name);
        list.put("num",bean.num);
        bean.content=JSON.toJSONString(list);
        bean.create_time= TimeUtil.getShortTimeStamp();
        add(bean);
    }
    public ArrayList<HashMap<String, String>> sear_offer(OfferBean bean) {
        String limit = 10 + ",10";
        String sql = "select a.offer_code,a.id from offer where offer_code like %" + bean.search_name + "% or context ->  '$.prod_name'like %" + bean.search_name + "% limit " + limit;
        return query(sql);
    }

public ArrayList<HashMap<String, String>> sear_pro(OfferBean bean){
        String limit = 10 + ",10";
        String sql ="select a.name,a.id from part_cat where name like %" + bean.search_pro + "% limit " + limit;
        String part_sql="select name,parent_id from part_cat where id =parent_id";
        ArrayList<HashMap<String,String>> list=query(sql);
        ArrayList<HashMap<String,String>> map=query(part_sql);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<map.size();j++){
                if(list.get(i).get("id")==list.get(j).get("parent_id")){
                    list.get(i).put("part_name",list.get(i).get("name"));
                }
            }
        }
     return list;
}
public void sel_offer(OfferBean bean){
         where("id="+bean.id).find();
}
public HashMap<String, String> add_part (OfferBean bean){
    HashMap<String, String> list = new HashMap<>();
    list.put("search_name",bean.search_name);
    return list;
}
    }

