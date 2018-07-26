package com.cloudling.offer.bean;

import com.cloudling.offer.annotation.required;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:28
 * @Version 1.0
 **/
public class PartCatBean extends Bean {

    public PartCatBean(ControllerContext context)
    {
        super(context);
    }



    @required
    public String name,parent_id,cat_id;

    public int create_time;






}
