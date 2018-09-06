package com.cloudling.offer.bean;

import com.cloudling.offer.annotation.required;
import com.cloudling.offer.server.ControllerContext;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:28
 * @Version 1.0
 **/
public class PartCatBean extends Bean {

    public PartCatBean(HashMap<String, String> context)
    {
        super(context);
    }



    @required
    public String name,parent_id,cat_id,id;

    public int create_time;


    public List<SpareBean> spareBeans;



}
