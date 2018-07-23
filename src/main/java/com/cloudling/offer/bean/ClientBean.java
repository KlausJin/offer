package com.cloudling.offer.bean;

import com.cloudling.offer.server.ControllerContext;
import com.mysql.fabric.xmlrpc.Client;

public class ClientBean extends Bean {
    public ClientBean(ControllerContext context){
        super(context);
    }
   public  String sear_cli;
    public String name,logo;
    public int create_time;
    public String pic;
    public String remark;


}
