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



public class EnglishReferModel extends Model {

    String url;
    public EnglishReferModel(String url) {
        super("english_refer");
        this.url=url;
    }



    public List<HashMap<String, String>> do_excel() throws Exception {
        File file = new File("assets/"+url+"");
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("中文", "name");
        m.put("英文","e_name");
        List<HashMap<String, String>> ls = null;
        ls = parseExcel(fis, file.getName(), m);
        return ls;
    }
}
