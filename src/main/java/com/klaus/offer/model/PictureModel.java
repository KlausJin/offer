package com.klaus.offer.model;

import com.klaus.offer.bean.PictureBean;
import com.klaus.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class PictureModel extends  Model {
    public PictureModel() {
        super("picture");

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
     public void add_pic(PictureBean bean) throws Exception {
         bean.create_time= TimeUtil.getShortTimeStamp();
         add(bean);
     }
     public ArrayList<HashMap<String, String>> pic_list(){
         String sql="select * from picture limit "+ limit;
         ArrayList<HashMap<String, String>> list = query(sql);
         return list;

     }

     public ArrayList<HashMap<String,String>> getPictureByName(String name,String id){
         String sql="SELECT * from (SELECT * from picture where name like '%"+name+"%' ) b where sale_id = "+id+" or sale_id =0";

         ArrayList<HashMap<String, String>> list = query(sql);

         for(int i=0;i< list.size();i++){
             String pic = list.get(i).get("pic");
             pic = pic.replace("\\","/");
             pic = pic.replace("assets","");
             list.get(i).put("pic",pic);

         }

        return  list;
     }
}
