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
public class ClientExcelModel extends Model {
    String url;
    public ClientExcelModel(String url) {
        super("client");
        this.url=url;
    }

    public List<HashMap<String, String>> excelToClientList() throws Exception {
        File file = new File("assets/"+url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("业务员", "sale_id");
        m.put("客户名称", "name");
        m.put("客户来源", "client_from_id");
        m.put("地区编号","area_id");
        m.put("clock<=500","clockle500");
        m.put("clock500-1K","clock500_1k");
        m.put("clock1K-3K","clock1k_3k");
        m.put("clock3K-5K","clock3k_5k");
        m.put("clock5K-10K","clock5k_10k");
        m.put("clock10K-20K","clock10k_20k");
        m.put("clock>=20K","clockme20k");
        m.put("watch<=5K","watchle5k");
        m.put("watch5K-10K","watch5k_10k");
        m.put("watch10K-50K","watch10k_50k");
        m.put("watch50K-100K","watch50k_100k");
        List<HashMap<String, String>> ls = null;
        ls = parseExcel(fis, file.getName(), m);
        return ls;
    }
}
