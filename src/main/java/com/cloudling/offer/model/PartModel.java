package com.cloudling.offer.model;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.bean.PartBean;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ImportExcelUtil;
import com.cloudling.offer.util.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  14:47
 * @Version 1.0
 **/
public class PartModel extends Model {
    public static final String CODE = "code";
    public static final String ATTR = "attr";
    public static final String PRICE = "price";
    public PartModel() {

        super("part");
    }

    public void add_part(PartBean bean) throws Exception {
        bean.create_time=TimeUtil.getShortTimeStamp();
        add(bean);
    }

    public void delete_part(int id){
        where("id="+id).delete();
    }

    public void addDraft(HashMap<String,String> data) throws Exception {
        new Model("part_draft").add(data);
    }

    public ArrayList<HashMap<String, String>> DraftList(String limit) throws Exception {
        return new Model("part_draft").limit(limit).select();
    }

    public ArrayList<HashMap<String, String>> DraftList() throws Exception {
        return new Model("part_draft").select();
    }

    public int draftCount(){
        return new Model("part_draft").count();
    }

    /**
     * @param url excel 路径
     * @return 返回键值对形式的配件列表
     */
    public List<LinkedHashMap<String,Object>> fromExcel(String url) throws FileNotFoundException {
        File file = new File("assets/" + url + "");
        FileInputStream fis = new FileInputStream(file);
        try {
            List<LinkedHashMap<String, Object>> ls = ImportExcelUtil.parseExcel(fis, file.getName());
            List<LinkedHashMap<String, Object>> res = new ArrayList<>();
            for (int i = 0; i < ls.size(); i++) {
                LinkedHashMap<String, Object> map = ls.get(i);
                LinkedHashMap<String, Object> d = new LinkedHashMap<>();
                for (String k:
                        map.keySet()) {
                    String v = map.get(k).toString();
                    v.replaceAll("；",";");
                    String[] v1 = v.split(";");
                    if(v1.length==1){
                        Object vs = toAttrMap(v);
                        d.put(k,vs);

                    }else{
                        List<Object> attrs = new LinkedList<>();
                        for(int j=0;j<v1.length;j++){
                            attrs.add(toAttrMap(v1[j]));
                        }
                        d.put(k,attrs);
                    }

                }
                res.add(d);


            }
            return res;
        }catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }

    /**
     * @Description:01:仿金/玫瑰金/枪色:0.2 拆成map或者string
     * @param: s 字符串
     * @return:map或者string
     * @auther: CodyLongo
     * @modified:
     */

    protected Object toAttrMap(String s){
        s = s.replaceAll("：",":");
        String[] s1 = s.split(":");
        if(s1.length==1) return s1[0];
        if(s1.length==2){
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            map.put(ATTR,s1[0]);
            map.put(PRICE,s1[1]);
            return map;
        }
        if(s1.length==3){
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            map.put(CODE,s1[0]);
            map.put(ATTR,s1[1]);
            map.put(PRICE,s1[2]);
            return map;
        }
        return s;
    }

    public void update(int id,HashMap<String, String> map){
        where("id="+id).save_string(map);
    }


    public ArrayList<HashMap<String, String>> list(String condition)
    {
        return where(condition).select();
    }

    public HashMap<String,String> listone(String condition)
    {
        return where(condition).find();
    }

    public ArrayList<HashMap<String, String>> list()
    {
        return select();
    }
}
