package com.klaus.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.klaus.offer.annotation.action;
import com.klaus.offer.bean.ProductBean;
import com.klaus.offer.config.Dictionary;
import com.cloudling.offer.model.*;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.ExcelUtil;
import com.klaus.offer.util.TimeUtil;
import com.klaus.offer.model.*;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
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
        String sale_id=admin_type== Dictionary.FOLLOW?M("person").where("id="+user.get("follow_id")).find().get("id"):user.get("id");
        ArrayList<HashMap<String, String>> list = cm.field("id,name").where("sale_id=" + sale_id+" or sale_id=0").select();
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


    @action
    public void edit_product() {
        String id = I("post.id") == null ? "" : I("post.id").toString();
        String code = I("post.code") == null ? "" : I("post.code").toString();
        String price = I("post.price") == null ? "" : I("post.price").toString();
        HashMap<String, String> map = M("product").where("id =" + id).find();
        if (id.equals("")) {
            error("id不能为空");
            return;
        }
        if(map==null){
            error("此产品不存在");
            return;
        }
        HashMap<String, Object> res = new HashMap<>();
        if (code.equals("")){
            res.put("price", price);
        }
        else if (price.equals("")) {
            res.put("code", code);
        }
        else{
            res.put("price", price);
            res.put("code", code);
        }

        try {
            M("product").where("id=" + id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("参数修改错误");

        }
    }
    /**
     * 根据id
     *
     * @param
     */

    @action
    public void remove_spare() {
        String id;
        try {
            id = I("post.id").toString();
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }
        SpareModel sparetModel = new SpareModel();


        sparetModel.removeSpareById(id);
        success("1");

    }

    /**
     * 增加配件
     *
     * @param
     */

    @action
    public void add_spare() {
        toHtml("");
    }

    @action
    public void do_add_spare() {
        String product_id, name, is_float, cat_id;
        try {
            product_id = I("post.product_id").toString();
            name = I("post.name").toString();
            is_float = I("post.is_float").toString();
            cat_id = I("post.cat_id").toString();
        } catch (Exception e) {
            // TODO: handle exception
            error("参数不能为空");
            return;
        }
        HashMap<String, String> res = new HashMap<>();
        res.put("product_id", product_id);
        res.put("name", name);
        res.put("is_float", is_float);
        res.put("cat_id", cat_id);
        res.put("create_time", TimeUtil.getShortTimeStamp() + "");
        try {
            M("spare").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("数据加载到数据库失败");
        }

    }

    /**
     * 修改配件
     *
     * @param
     */

    @action
    public void edit_spare() {
        String id = I("id").toString();
        assign("spare", JSON.toJSON(M("spare").where("id=" + id).find()));
        toHtml("");
    }

    @action
    public void do_edit_spare() {
        String id = I("post.id") == null ? "" : I("post.id").toString();
        String product_id = I("post.product_id") == null ? "" : I("post.product_id").toString();
        String name = I("post.name") == null ? "" : I("post.name").toString();
        String is_float = I("post.is_float") == null ? "" : I("post.is_float").toString();
        String cat_id = I("post.cat_id") == null ? "" : I("post.cat_id").toString();
        if (id.equals("")) {
            error("id不能为空");
            return;
        }
        HashMap<String, Object> res = new HashMap<>();
        if (product_id != null  && product_id.length() != 0){ res.put("product_id", product_id);}
        if (name !=null && name.length() != 0){ res.put("name", name);}
        if (is_float !=null && is_float.length() != 0){ res.put("is_float", is_float);}
        if (cat_id !=null && cat_id.length() != 0){ res.put("cat_id", cat_id);}

        try {
            M("spare").where("id=" + id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("参数修改错误");
        }
    }

    /**
     * 增加属性
     *
     * @param
     */
    public void add_attr() {
        toHtml("");
    }
    @action
    public void do_add_attr() {
        String spare_id, price, parent_id;
        try {
            spare_id = I("post.spare_id").toString();
            price = I("post.price").toString();
            parent_id = I("post.parent_id").toString();
        } catch (Exception e) {
            // TODO: handle exception
            error("参数不能为空");
            return;
        }
        String name = I("post.name") == null ? "" : I("post.name").toString();
        String num = I("post.num") == null ? "" : I("post.num").toString();
        String code = I("post.code") == null ? "" : I("post.code").toString();
        String formula = I("post.formula") == null ? "" : I("post.formula").toString();
        String relate_id = I("post.relate_id") == null ? "" : I("post.relate_id").toString();
        String or_id = I("post.or_id") == null ? "" : I("post.or_id").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("spare_id", spare_id);
        res.put("price", price);
        res.put("parent_id", parent_id);
        if (name != null  && name.length() != 0) {res.put("name", name);}
        if (num != null  && num.length() != 0) { res.put("num", num);}
        if (code != null  && code.length() != 0)   {res.put("code", code);}
        if (formula != null  && formula.length() != 0){ res.put("formula", formula);}
        if (relate_id != null  && relate_id.length() != 0){ res.put("relate_id", relate_id);}
        if (or_id != null  && or_id.length() != 0){ res.put("or_id", or_id);}
        try {
            M("attr").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("数据加载到数据库失败");
        }


    }

    /**
     * 修改属性
     *
     * @param
     */
    @action
    public void edit_attr() {
        String id = I("post.id") == null ? "" : I("post.id").toString();
        String name = I("post.name") == null ? "" : I("post.name").toString();
        String num = I("post.num") == null ? "" : I("post.num").toString();
        String code = I("post.code") == null ? "" : I("post.code").toString();
        String formula = I("post.formula") == null ? "" : I("post.formula").toString();
        String relate_id = I("post.relate_id") == null ? "" : I("post.relate_id").toString();
        String or_id = I("post.or_id") == null ? "" : I("post.or_id").toString();
        String spare_id = I("post.spare_id") == null ? "" : I("post.spare_id").toString();
        String price = I("post.price") == null ? "" : I("post.price").toString();
        String parent_id = I("post.parent_id") == null ? "" : I("post.parent_id").toString();
        if (id.equals("")) {
            error("id不能为空");
            return;
        }
        HashMap<String, Object> res = new HashMap<>();

        if (name != null && name.length() != 0){ res.put("name", name);}
        if (spare_id != null  && spare_id.length() != 0)  {res.put("spare_id", spare_id);}
        if (price != null  && price.length() != 0){res.put("price", price);}
        if (parent_id != null  && parent_id.length() != 0){ res.put("parent_id", parent_id);}
        if (num != null  && num.length() != 0){res.put("num", num);}
        if (code != null  && code.length() != 0){res.put("code", code);}
        if (formula != null  && formula.length() != 0)  {res.put("formula", formula);}
        if (relate_id != null  && relate_id.length() != 0){res.put("relate_id", relate_id);}
        if (or_id != null  && or_id.length() != 0) {res.put("or_id", or_id);}
        try {
            M("attr").where("id=" + id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("参数修改错误");
        }
    }



}
