package com.cloudling.offer.controller.v1;


import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartCatModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import static com.cloudling.offer.util.ExcelUtil.parseExcel;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-04  13:07
 * @Version 1.0
 **/
public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

//    @action
//    public void test(){
//        File file = new File("assets/resourses/01.xlsx");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        HashMap<String, String> m = new HashMap<String, String>();
//        m.put("id", "id");
//        m.put("姓名", "name");
//        m.put("年龄", "age");
//        List<HashMap<String, String>> ls = null;
//        try {
//            ls = parseExcel(fis, file.getName(), m);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(ls));
//    }
//    @action
//    public void test1(){
//        String id = I("id") == null ? "" : I("id").toString();
//        PartCatModel pm =new PartCatModel();
//        success(pm.getBean_real(id).getData());
//    }

//    @action
//    public void test2(){
//        PersonExcelModel  pm=new PersonExcelModel();
//        try {
//            List<HashMap<String, String>> ls = pm.excelToPersonList();
//            System.out.println(JSON.toJSONString(ls));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @action
//    public void test3(){
//        String x="123.0";
//        double a=0;
//        int b=0;
//        a=Double.valueOf(x);
//        b=(int)a;
//        System.out.println(b);
//    }


}
