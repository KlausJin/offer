package com.cloudling.offer.export;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-14  15:49
 * @Version 1.0
 **/
public class ExportDictonary {

    public static HashMap<String,String> dic = new HashMap<String,String>(){
        {
            put("业务员邮箱_yz","yz_email");
            put("业务员邮箱_wsk","wsk_email");
            put("业务员座机","tel");
            put("业务员英文名_yz","yz_name");
            put("业务员英文名_wsk","wsk_name");
            put("报价时间","create_time");
            put("产品型号","pro_name");
            put("单价","per_price");
            put("总价","sale");
            put("数量","num");
            put("cbm","cbm");
        }
    };

}
