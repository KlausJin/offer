package com.klaus.offer.bean;

import com.klaus.offer.server.ControllerContext;

public class PictureBean extends  Bean {
    public PictureBean(ControllerContext context){
        super(context);
    }
    public String pic,name;
    public int create_time;
}
