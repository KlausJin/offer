package com.cloudling.offer.model;

import com.cloudling.offer.export.ExcelExprtAdapter;
import com.cloudling.offer.util.ExcelToOfferUtil;

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



public class InputExcelModel extends Model {

    String url;
    String id;

    public InputExcelModel(String url,String id) {
        super("offer_templet");
        this.url=url;
        this.id = id;
    }



    public String  do_excel() throws Exception {
        File file = new File(url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        List<HashMap<String, String>> ls = null;
        return  ExcelToOfferUtil.parseExcel(fis, file.getName(), new ExcelExprtAdapter(id));

    }
}
