package com.klaus.offer.bean;

import com.klaus.offer.server.ControllerContext;

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
