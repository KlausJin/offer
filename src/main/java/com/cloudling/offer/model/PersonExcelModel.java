package com.cloudling.offer.model;


import java.io.File;
import java.io.FileInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cloudling.offer.util.ExcelUtil.parseExcel;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-21  12:05
 * @Version 1.0
 **/



public class PersonExcelModel extends Model {

    String url;
    public PersonExcelModel(String url) {
        super("person");
        this.url=url;
    }



    public List<HashMap<String, String>> excelToPersonList() throws Exception {
        File file = new File("assets/"+url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("登录名", "name");
        m.put("人员类型", "status");
        m.put("yz_name", "yz_name");
        m.put("wsk_name", "wsk_name");
        m.put("tel","tel");
        m.put("yz_email","yz_email");
        m.put("wsk_email","wsk_email");
        m.put("follow_name","follow_id");
        List<HashMap<String, String>> ls = null;
        ls = parseExcel(fis, file.getName(), m);
        return ls;
    }
}
