package com.klaus.offer.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.klaus.offer.util.ExcelUtil.parseExcel;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-21  12:05
 * @Version 1.0
 **/



public class AreaExcelModel extends Model {

    String url;
    public AreaExcelModel(String url) {
        super("area");
        this.url=url;
    }



    public List<HashMap<String, String>>  do_excel() throws Exception {
        File file = new File("assets/"+url+"");
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("地区", "area_name");
        m.put("国家编号","country_id");
        List<HashMap<String, String>> ls = null;
        ls = parseExcel(fis, file.getName(), m);
        return ls;
    }
}
