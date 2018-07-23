package com.cloudling.offer.bean;

import com.cloudling.offer.server.ControllerContext;
import org.apache.poi.ss.usermodel.Picture;

public class PictureBean extends  Bean {
    public PictureBean(ControllerContext context){
        super(context);
    }
    public String pic,name;
    public int create_time;
}
