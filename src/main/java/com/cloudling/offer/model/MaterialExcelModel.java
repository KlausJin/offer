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



public class MaterialExcelModel extends Model {

    String url;
    public MaterialExcelModel(String url) {
        super("material");
        this.url=url;
    }



    public List<HashMap<String, String>> do_excel() throws Exception {
        File file = new File("assets/"+url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("原材料名称", "name");
        m.put("价格","price");
        m.put("原材料分类","cat_id");
        List<HashMap<String, String>> ls = null;
        ls = parseExcel(fis, file.getName(), m);
        return ls;
    }
}
