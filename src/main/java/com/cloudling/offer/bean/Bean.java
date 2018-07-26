package com.cloudling.offer.bean;

import com.cloudling.offer.server.ControllerContext;
import org.omg.CORBA.Object;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:42
 * @Version 1.0
 **/
public class Bean {
    protected ControllerContext context;
    Bean instance;
    public Bean(ControllerContext context){
        this.context = context;
        instance = this;
    }
    public Bean(HashMap<String,String> data){
        Field[] fields = getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            field.setAccessible(true);
            try {
                if(data.containsKey(field.getName()) && data.get(field.getName())!=null){
                    field.set(field.getName(),data.get(field.getName()));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Bean(){

    }

    public HashMap<String,String> getData(){
        HashMap<String,String> data = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            field.setAccessible(true);
            try {
                if(field.get(instance)!=null){
                   data.put(field.getName(),field.get(instance).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return data;

    }

}
