package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartCatModel;
import com.cloudling.offer.model.PartExcelModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void list() {
        toHtml("admin_tpl/product_list");
    }


    @action
    public void dialog_search() {

        toHtml("admin_tpl/dialog_product_search");
    }

    @action
    public void dialog_search_client() {
        toHtml("admin_tpl/dialog_product_search_client");
    }

    @action
    public void getList() {
        String page = I("get.page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        HashMap<String, String> SpareDate = new HashMap<>();
        ProductModel pm = new ProductModel();
        ArrayList<HashMap<String, String>> list = pm.query("SELECT a.*,(select COUNT(*) from spare b where b.product_id=a.id) as count from product a limit " + limit + "");
        ArrayList<HashMap<String, String>> list1 = pm.query("SELECT a.*,(select COUNT(*) from spare b where b.product_id=a.id) as count from product a");
        int num = list1.size();
        res.put("list", list);
        res.put("num", num);
        success(res);
    }

    @action
    public void add_product() {
        toHtml("admin_tpl/add_product");
    }

    @action
    public void do_add_product() {
        PartCatModel pm = new PartCatModel();
        String url = I("url").toString();
        String cat_id = I("cat_id").toString();
        try {
            File file = new File("assets/" + url + "");
            FileInputStream fis = new FileInputStream(file);
            List<Sheet> ls = ExcelUtil.getSheet(fis, file.getName());
            for (int i = 0; i < ls.size(); i++) {
                PartExcelModel pem = new PartExcelModel("spare", ls.get(i), cat_id);
                pem.init();
            }
            success("1");
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
        }
    }

    @action
    public void getFirstInfo() {
        PartCatModel pm = new PartCatModel();
        String sql = "select id,name,cat_id  from part_cat where parent_id=0";
        ArrayList<HashMap<String, String>> list = pm.query(sql);
        success(list);
    }

    @action
    public void getProductInfo() {
        ProductModel pm = new ProductModel();
        ArrayList<HashMap<String, String>> list = pm.field("id,code").select();
        success(list);
    }

    @action
    public void getProduct() {
        String id = I("id") == null ? "0" : I("id").toString();
        ProductModel productModel = new ProductModel();
        success(productModel.getBean(id).getData());

    }


}
