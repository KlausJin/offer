package com.cloudling.offer.bean;

import com.cloudling.offer.annotation.required;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  14:21
 * @Version 1.0
 **/
public class PersonBean  extends Bean{

    public PersonBean(ControllerContext context){
        super(context);
    }

    @required
    public  String yz_name,wsk_name,name,status,tel,follow_id;

    public int create_time,last_login_time;

    public  String password,yz_email,wsk_email;

}
