package com.klaus.offer.util;

import com.klaus.offer.bean.Bean;
import com.klaus.offer.config.Entrance;
import com.klaus.offer.exception.ParamsErrorException;
import com.klaus.offer.server.ControllerContext;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:37
 * @Version 1.0
 **/
public class BeanUtil {

    public static final int POST=1;
    public static final int GET=2;

    public static Bean getBean(String bean_name, ControllerContext context, int type) throws ParamsErrorException {
        String classname = Entrance.PACKAGE_NAME+".bean."+toUpperCaseFirstOne(bean_name)+"Bean";
        Class<?> BeanClass = null;
        try {
            BeanClass = Class.forName(classname);
            Constructor<?> c = BeanClass.getConstructor(ControllerContext.class);
            Bean bean = (Bean) c.newInstance(context);
            HashMap params = new HashMap();
            switch (type){
                case POST:
                    params = context.POST;
                    break;
                case GET:
                    params = context.GET;
                    break;
            }

            Field[] fields = BeanClass.getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                Field field = fields[i];
                String annotation="";
                for(Annotation an : field.getAnnotations()){
                    annotation=an.toString();
                }
                if( (!params.containsKey(field.getName()) || params.get(field.getName())==null)){
                    if(annotation.equals("@"+ Entrance.PACKAGE_NAME +".annotation.required()"))
                        throw new ParamsErrorException(field.getName()+"不能为空");
                }else{
                    field.setAccessible(true);
                    field.set(bean,filterParam(params.get(field.getName()).toString()));

                }



            }

            return bean;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
           return null;
        }





    }

    static String filterParam(String param) {
        ArrayList<FilterUtil.FilterHandler> handlers = new ArrayList<>();
        //添加html过滤器
        handlers.add(FilterUtil.getHtmlHandler());
        //添加房sql注入过滤器
        handlers.add(FilterUtil.getSqlHandler());

        return FilterUtil.filter(param, handlers);
    }

    static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


}
