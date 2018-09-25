package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.model.*;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.ExcelUtil;
import com.cloudling.offer.util.StringUtil;
import com.cloudling.offer.util.TimeUtil;
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
public class ProductController extends AdminController {
    public ProductController(ControllerContext context) {
        super(context);
        if (admin_type>MANAGER){
            pri=false;
            return;
        }
    }

    @action
    public void list() {
        toHtml("admin_tpl/product_list");
    }


    @action
    public void dialog_search() {
        String cat_id=I("cat_id").toString();
        assign("p_cat_id",cat_id);
        toHtml("admin_tpl/dialog_product_search");
    }

    @action
    public void dialog_search_s_spare() {
        toHtml("admin_tpl/dialog_product_search_s_spare");
    }

    @action
    public void dialog_search_client() {
        toHtml("admin_tpl/dialog_product_search_client");
    }

    @action
    public void dialog_new_client() {
        HashMap<String, String> data = null;
        if (admin_type == 4) {
            data = M("person").where("id=" + user.get("follow_id")).find();
        } else {
            data = M("person").where("id=" + user.get("id")).find();
        }
        assign("info", JSON.toJSON(data));
        toHtml("admin_tpl/dialog_product_new_client");
    }


    @action
    public void getClientFrom(){
        ArrayList<HashMap<String, String>> list = M("client_from").field("id,name").select();
        success(list);
    }

    @action
    public void getArea(){
        ArrayList<HashMap<String, String>> list = M("area").field("id,area_name").select();
        success(list);
    }

    @action
    public void getList() {
        String page = I("get.page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String search_name=I("search_name")==""?"":I("search_name").toString();
        String search_cat=I("search_cat").equals("0")?"":I("search_cat").toString();
        HashMap<String, Object> res = new HashMap<>();
        String sql = "SELECT a.*,(select COUNT(*) from spare b where b.product_id=a.id) as count from product a where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        StringBuffer snum=new StringBuffer(sql);
        if (search_name.length()>0){
            sb.append(" and a.code like '%"+search_name+"%'");
            snum.append(" and a.code like '%"+search_name+"%'");
        }
        if (search_cat.length()>0){
            sb.append(" and a.cat_id="+search_cat);
            snum.append(" and a.cat_id="+search_cat);
        }
        sb.append(" limit "+limit);
        ProductModel pm = new ProductModel();
        ArrayList<HashMap<String, String>> list = pm.query(sb.toString());
        ArrayList<HashMap<String, String>> list1 = pm.query(snum.toString());

        res.put("list", list);
        res.put("num", list1.size());
        success(res);
    }

    @action
    public void add_product() {
        toHtml("admin_tpl/add_productByExcel");
    }

    @action
    public void do_add_product() {
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
        String cat_id=I("cat_id")==null || I("cat_id").equals("")?"0":I("cat_id").toString();
        ProductModel pm = new ProductModel();
        if (!cat_id.equals("0")) {
            ArrayList<HashMap<String, String>> list = pm.field("id,code").where("cat_id=" + cat_id).select();
            success(list);
        }else{
            error("获取产品信息失败");
        }
    }

    @action
    public void getClientInfo() {
        ClientModel cm = new ClientModel();
        String sale_id=admin_type==Dictionary.FOLLOW?M("person").where("id="+user.get("follow_id")).find().get("id"):user.get("id");
        ArrayList<HashMap<String, String>> list = cm.field("id,name").where("sale_id=" + sale_id).select();
        success(list);
    }

    @action
    public void getProduct() {
        String id = I("id") == null ? "0" : I("id").toString();
        ProductModel productModel = new ProductModel();
        success(productModel.getBean(id).getData());
    }

    @action
    public void getCommonSpare(){
        String id = I("id") == null ? "" : I("id").toString();
        PartCatModel pm =new PartCatModel();
        success(pm.getBean_real(id).getData());
    }

    @action
    public void getClient() {
        String id = I("id") == null ? "0" : I("id").toString();
        ClientModel cm = new ClientModel();
        success(cm.where("id=" + id).find());
    }

    @action
    public void edit(){
        String id = I("id") == null ? "0" : I("id").toString();
        long t = TimeUtil.getLongTimeStamp();
        ProductModel productModel = new ProductModel();
        ProductBean bean = productModel.getBean(id);
        assign("data", JSON.toJSON(bean.getData()));

        long ms = TimeUtil.getLongTimeStamp() - t;
        System.out.println("ms:"+ms);
        toHtml("admin_tpl/product_edit");
    }


}
