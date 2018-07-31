package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.SpareModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.HashMap;

public class ProductsController extends Controller {
    public ProductsController(ControllerContext context) {
        super(context);
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
    if (name != null  && name.length() != 0) {res.put("name", name);}
        res.put("spare_id", spare_id);
        res.put("price", price);
        res.put("parent_id", parent_id);
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

        if (name != null  && name.length() != 0){ res.put("name", name);}
        if (spare_id != null  && spare_id.length() != 0)  {res.put("spare_id", spare_id);}
        if (price != null  && price.length() != 0){res.put("price", price);}
        if (parent_id != null  && parent_id.length() != 0){ res.put("parent_id", parent_id);}
        if (num != null  && num.length() != 0){res.put("num", num);}
        if (num != null  && num.length() != 0){res.put("code", code);}
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
