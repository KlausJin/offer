package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartExcelModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-19  9:30
 * @Version 1.0
 **/
public class ProductController extends Controller {
    public ProductController(ControllerContext context) {
        super(context);
    }

    @action
    public void list(){
        toHtml("admin_tpl/product_list");
    }

    @action
    public void add_product(){
        toHtml("admin_tpl/add_product");
    }

    @action
    public void do_add_product() throws FileNotFoundException {
        String url = I("url").toString();
        try {
            File file = new File("assets/" + url + "");
            FileInputStream fis = new FileInputStream(file);
            List<Sheet> ls = ExcelUtil.getSheet(fis, file.getName());
            for (int i=0;i<ls.size();i++){
                 PartExcelModel pem=new PartExcelModel("spare",ls.get(i),"22");
                 pem.init();
            }
            success("1");
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
        }
    }


}
