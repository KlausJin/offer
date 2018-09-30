package com.cloudling.offer.model;

import com.cloudling.offer.exception.ExcelImportException;
import com.cloudling.offer.util.StringUtil;
import com.cloudling.offer.util.TimeUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-26  12:31
 * @Version 1.0
 **/
public class PartExcelModel extends Model {

    public static final String PRODUCT_CODE = "产品型号";
    public static final String PRODUCT_PRICE = "基础价格";
    public static final String[] ATTR_FIELDS = new String[]{"code", "name", "price", "num", "formula"};

    Model productModel;
    Model attrModel;
    int product_id = 0;
    Sheet sheet;
    String product_code;
    String product_price;
    String cat_id;


    public PartExcelModel(String table, Sheet sheet, String cat_id) {
        super("spare");
        this.sheet = sheet;
        this.cat_id = cat_id;
        productModel = new Model("product");
        attrModel = new Model("attr");
    }

    public void init() throws ExcelImportException {

        // 遍历所有的列
        getProduct();
        getSpare();
    }

    private void getSpare() throws ExcelImportException {

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row==null){
                break;
            }
            Object field = row.getCell(0);

            String fname = field == null ? "" : field.toString();
            if ("".equals(fname)) continue;
            if (!PRODUCT_CODE.equals(fname) && !PRODUCT_PRICE.equals(fname)) {
                HashMap<String, String> data = new HashMap<>();
                data.put("product_id", product_id + "");
                data.put("name", fname);
                data.put("cat_id", cat_id);
                data.put("create_time", TimeUtil.getShortTimeStamp() + "");
                try {
                    int spare_id = (int) add(data);
                    int row_c = getNextFiledRowCount(0, i);
                    getAttr(fname, spare_id, 1, i, row_c);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ExcelImportException(e.getMessage());
                }


            }

        }


    }

    /**
     * @Description:增加属性
     * @param: spare_id 配件id col 第几列 start_row第几行开始 end_row第几行结束
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    private void getAttr(String spare_name, int spare_id, int col, int start_row, int end_row) throws ExcelImportException {
        for (int i = start_row; i <= end_row; i++) {
            Row row = sheet.getRow(i);
            if (row==null){
                break;
            }
            String field = row.getCell(col) == null ? "" : row.getCell(col).toString();
            if (i == start_row && "".equals(field)) {
                field = spare_name;
            }
            if ("".equals(field)) {
                continue;
            }

            String fname = field.toString();
            HashMap<String, String> data = new HashMap<>();

            data.put("parent_id", "0");
            data.put("spare_id", spare_id + "");
            data.put("price", "-1");
            data.put("num", "-1");
            data.put("formula", "0");
            data.put("relate_id", getRelateId(fname)== null ? "0" :getRelateId(fname).get("relate_id"));
            data.put("or_id", getOrId(spare_id,fname)== null ? "0" : getOrId(spare_id,fname).get("or_Id"));
            if(getOrId(spare_id,fname) !=null){
                data.put("name",getOrId(spare_id,fname).get("name"));
            }else if (getRelateId(fname) !=null){
                data.put("name",getRelateId(fname).get("name"));
            }else{
                data.put("name", fname);
            }



            try {
                int parent_id = (int) attrModel.add(data);
                getSubAttr(parent_id, 2, i, getNextFiledRowCount(1, i));

            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelImportException(e.getMessage());
            }


        }

    }

    /**
     * @Description:增加子属性
     * @param: parent_id
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    private void getSubAttr(int parent_id, int col, int start_row, int end_row) throws ExcelImportException {

        for (int i = start_row + 1; i <= end_row; i++) {
            Row row = sheet.getRow(i);
            if (row==null){
                break;
            }
            HashMap<String, String> data = new HashMap<>();
            for (int j = col; j < row.getLastCellNum(); j++) {
                int index = j - col;
                String filed = row.getCell(j) == null ? "" : row.getCell(j).toString();
                if (!filed.equals("") && index < ATTR_FIELDS.length)
                        data.put(ATTR_FIELDS[index], filed);
            }
            if ((!data.containsKey("price") || data.get("price").equals("")) && (!data.containsKey("name") || data.get("name").equals(""))) {
                continue;
            }
            data.put("spare_id", "0");
            data.put("parent_id", parent_id + "");
            try {
                attrModel.add(data);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelImportException(e.getMessage());
            }
        }

    }
    //并列 只能选一个
    public HashMap<String,String> getOrId(int spare_id, String fname){
        HashMap<String,String> map=new HashMap();
        if(fname.contains("{")){
            String a[]=fname.split("\\{");
            String newName=a[0];
            String attr_name=a[1].substring(0,a[1].length()-1);
            String sql="select id from attr where name='"+attr_name+"' and spare_id="+spare_id;
            ArrayList<HashMap<String, String>> list = attrModel.query(sql);
            String x = list.get(0).get("id");
            map.put("or_Id",x+"");
            map.put("name",newName);
            return  map;
        }else {
            return null;
        }
    }

    //关联
    public HashMap<String,String>  getRelateId(String fname){
        HashMap<String,String> map=new HashMap();
        if(fname.contains("[")){
            String a[]=fname.split("\\[");
            String newName=a[0];
            String b[]=a[1].replace("，",",").split(",");
            String spare_name = b[0];
            String attr_name = b[1].substring(0, b[1].length() - 1);
            String sql="select id from attr where name='"+attr_name+"' and spare_id=(select id from spare where name='"+spare_name+"' and product_id="+product_id+")";
            ArrayList<HashMap<String, String>> list = attrModel.query(sql);
            String x = list.get(0).get("id");
            map.put("relate_id",x+"");
            map.put("name",newName);
            return  map;
        }else{
            return null;
        }
    }

    private void getProduct() throws ExcelImportException {

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row==null){
                break;
            }
            String filed = row.getCell(0) == null? "" : row.getCell(0).toString();
            if (!"".equals(filed) && PRODUCT_CODE.equals(filed.toString())) {
                product_code = row.getCell(1).toString();
                if(product_code.contains(".")){
                    product_code=product_code.substring(0,product_code.indexOf("."));
                }
            }
            if (!"".equals(filed) && PRODUCT_PRICE.equals(filed.toString())) {
                product_price = row.getCell(1).toString();
            }

        }

        if (product_code == null) {
            //说明这个sheet没有产品
            return;
        }

        if (product_price == null || product_price.equals("")) {
            product_price = "0";
        }

        HashMap<String, String> product = productModel.where("code = '" + product_code + "'").find();
        if (product != null) {
            throw new ExcelImportException("已存在该产品名:" + product_code);
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("code", product_code);
        data.put("cat_id", cat_id);
        data.put("price", product_price);
        data.put("create_time", TimeUtil.getShortTimeStamp() + "");
        try {
            product_id = (int) productModel.add(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelImportException(e.getMessage());
        }


    }


    /**
     * @Description: 获取当前行列距离下个有值的行数
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    int getNextFiledRowCount(int col, int row) {
        int res = row;

        for (int i = row + 1; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (r==null){
                break;
            }
            String filed = r.getCell(col) == null ? "" : r.getCell(col).toString();

            if (!"".equals(filed)) {


                break;
            } else {
                if (col > 0 && !(r.getCell(col - 1) == null ? "" : r.getCell(col - 1).toString()).equals("")) break;
                else res++;
            }

        }
        return res;
    }


}
