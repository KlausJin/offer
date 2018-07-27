package com.cloudling.offer.bean;

import com.cloudling.offer.server.ControllerContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        instance = this;
        Field[] fields = getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            field.setAccessible(true);
            try {
                if(data.containsKey(field.getName()) && data.get(field.getName())!=null){
                    Class c = field.getType();
                    if(c.equals(String.class)){
                        field.set(instance,data.get(field.getName()));
                    }
                    if(c.equals(float.class)){
                        field.set(instance,Float.parseFloat(data.get(field.getName())));
                    }

                    if(c.equals(int.class)){
                        field.set(instance,Integer.parseInt(data.get(field.getName())));
                    }

                    if(c.equals(boolean.class)){
                        if(data.get(field.getName()).equals("1")){
                            field.set(instance,true);
                        }else{
                            field.set(instance,false);
                        }

                    }


                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Bean(){

    }

    public HashMap<String,Object> getData(){
        HashMap<String,Object> data = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            field.setAccessible(true);
            try {
                if(field.get(instance)!=null){
                    Class c = field.getType();
                    if(c.equals(List.class)){

                        List<Bean>  beans = (List<Bean>) field.get(instance);
                        if(beans!=null && beans.size()>0){
                            List<HashMap<String,Object>> blist = new ArrayList<>();
                            for(int j=0;j<beans.size();j++){
                                blist.add(beans.get(j).getData());
                            }
                            data.put(field.getName(),blist);
                        }

                    }
                    else
                   data.put(field.getName(),field.get(instance));


                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return data;

    }

}
