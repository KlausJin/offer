package com.cloudling.offer.export;

import com.cloudling.offer.model.Model;
import com.cloudling.offer.util.DoubleUtil;
import com.cloudling.offer.util.ModelUtil;
import com.cloudling.offer.util.TimeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-14  15:07
 * @Version 1.0
 **/
public class ExcelExprtAdapter implements OnGetExcelKey {

    String qid;

    List<HashMap<String,String>> products = new ArrayList<>();
    List<HashMap<String,String>> products_attrs = new ArrayList<>();
    List<HashMap<String,String>> pics=new ArrayList<>();



    HashMap<String,String> normalData;

    HashMap<String,String> changeData;

    HashMap<String,String> picData;

    public ExcelExprtAdapter(String id){
        this.qid = id;
        //获取业务员相关数据
        String sql = "select a.*,c.* from quote a left join offer b on a.offer_id = b.id left join person c on b.sale_id = c.id where a.id ="+id;
        normalData = ModelUtil.getModel("quote").query(sql).get(0);
        normalData.put("create_time",TimeUtil.stampToDate(normalData.get("create_time"),"yyyy-MM-dd"));

        //获取产品
        products = ModelUtil.getModel("quote_pro").where("quote_id=" + qid).select();
        for( int i=0;i<products.size();i++){

            ArrayList<HashMap<String, String>> product = ModelUtil.getModel("quote_detail").where("pro_id=" + products.get(i).get("id")).select();
            double sale = Double.parseDouble(products.get(i).get("sale"));
            double num = Double.parseDouble(products.get(i).get("pro_num"));
            products.get(i).put("per_price",DoubleUtil.div(sale,num)+"");
            HashMap<String,String> p = new HashMap<>();
            for( int j=0;j<product.size();j++){
                p.put(product.get(j).get("kind"),product.get(j).get("value"));
            }
            products_attrs.add(p);
        }

    }

    /**
     * @Description:获取通用信息
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @Override
    public String getNormalKey(Cell cell) {
       String v = cell.toString();
       List<String> keys = new ArrayList<>();
       String t1[] = v.split("\\$\\$\\{");
       if(t1.length<2) return cell.toString();
       for(int i=0;i<t1.length;i++){
           String s = t1[i];
           if(s.indexOf("}")>0){
               String t2[] = s.split("\\}");
               keys.add(t2[0]);
           }
       }

       for(int i=0;i<keys.size();i++){
           String k = keys.get(i);
           String kv = ExportDictonary.dic.containsKey(k)?ExportDictonary.dic.get(k):k;

               kv = normalData.containsKey(kv) ? normalData.get(kv) : kv;
               String old_char = "$${" + k + "}";
               v = v.replace(old_char, kv);

       }

       return Lang.getEn(v);
    }


    /**
     * @Description:获取产品相关的信息
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @Override
    public String getProductKey(Cell cell, int pos) {
        String v = cell.toString();
        if(v.indexOf("${序列号}")>-1){
            return  pos+1+"";
        }

        HashMap<String, String> product_attr = products_attrs.get(pos);
        HashMap<String, String> product = products.get(pos);

        List<String> keys = new ArrayList<>();
        String t1[] = v.split("\\$\\{");
        if(t1.length<2) return cell.toString();
        for(int i=0;i<t1.length;i++){
            String s = t1[i];
            if(s.indexOf("}")>0){
                String t2[] = s.split("\\}");
                keys.add(t2[0]);
            }
        }

        for(int i=0;i<keys.size();i++){
            String k = keys.get(i),kv;
            if(ExportDictonary.dic.containsKey(k)){
                String kk = ExportDictonary.dic.get(k);
                if(product_attr.containsKey(kk)){
                    kv = product_attr.get(kk);
                }else{
                    kv = product.containsKey(kk)?product.get(kk):kk;
                }

            }else{
                kv = product_attr.containsKey(k)?product_attr.get(k):k;
            }


            String old_char  = "${"+k+"}";
            v = v.replace(old_char,kv);
        }

        return Lang.getEn(v);
    }

    /**
     * @Description:返回产品的长度
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @Override
    public int getSize() {
        return products_attrs.size();
    }

    @Override
    public String getId() {
        return qid;
    }

    @Override
    public int getStartRow(Sheet sheet) {
        int res = -1;
        for (int j = 1; j < sheet.getLastRowNum()+1; j++) {
            Row row = sheet.getRow(j);

            if(row!=null) {
                String key;
                Cell cell = row.getCell(row.getFirstCellNum());
                if(cell==null) continue;
                key = cell.toString();
                if(key.equals("${start}")){
                    res = j;
                    break;
                }

            }


        }
        return res;
    }

    @Override
    public int getEndRow(Sheet sheet) {
        int res = -1;
        for (int j = 1; j < sheet.getLastRowNum()+1; j++) {
            Row row = sheet.getRow(j);

            if(row!=null) {
                String key;
                Cell cell = row.getCell(row.getFirstCellNum());
                if(cell==null) continue;
                key = cell.toString();
                if(key.equals("${end}")){
                    res = j;
                    break;
                }

            }


        }
        return res;
    }

    @Override
    public boolean isPicture(Cell cell) {
        String s = cell.toString();
        if(s.indexOf("${图片}")>-1){
            return true;
        }
        return false;
    }

    @Override
    public String getPictures(int pos) {
        return products.get(pos).get("pic_url");
    }
}
