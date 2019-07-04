package com.klaus.offer.bean;

import com.klaus.offer.annotation.required;
import com.klaus.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  13:28
 * @Version 1.0
 **/
public class PartBean extends Bean {

    public PartBean(ControllerContext context)
    {
        super(context);
    }



    @required
    public String code,par_name,cat_id,model,unit,cons,unit_cost,type_id;

    public int create_time,update_time;






}
