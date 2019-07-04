package com.klaus.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.klaus.offer.annotation.action;
import com.klaus.offer.config.Dictionary;
import com.klaus.offer.model.MaterialExcelModel;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-24  13:40
 * @Version 1.0
 **/
public class MaterialController  extends AdminController {
    public MaterialController(ControllerContext context) {
        super(context);
        if (admin_type>MANAGER){
            pri=false;
            return;
        }
    }


    @action
    public void add(){
        toHtml("admin_tpl/add_material");
    }

    @action
    public void do_add(){
        String name=I("name")==null || I("name").equals("")?"":I("name").toString();
        String price=I("price")==null || I("price").equals("")?"":I("price").toString();
        String cat_id=I("cat_id")==null || I("cat_id").equals("")?"":I("cat_id").toString();
        HashMap<String,String>  data=new HashMap<>();
        data.put("name",name);
        data.put("price",price);
        data.put("cat_id",cat_id);
        data.put("status",Dictionary.MATERIALADD+"");
        data.put("updatetime",TimeUtil.getShortTimeStamp()+"");
        try{
            long t = M("material").add(data);
            if (t>0) {
                success("1");
            }else{
                error("添加到数据库失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            error("添加失败");
        }

    }


    @action
    public void list(){
        toHtml("admin_tpl/material_list");
    }

    @action
    public void getMaterialList() {
        String page = I("page").toString();
        String key = I("key") == null || I("key").equals("") ? "" : I("key").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        HashMap<Integer, String> statusTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put(Dictionary.MATERIALADD, "新添加");
                put(Dictionary.MATERIALUPDATE,"已修改");
            }
        };
        if ("".equals(key)) {
            ArrayList<HashMap<String, String>> list = M("material").order("updatetime desc").limit(limit).select();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("updatetime",
                        TimeUtil.stampToDate(list.get(i).get("updatetime"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("status",statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            }
            int num=M("material").select().size();
            res.put("list", list);
            res.put("num", num);
        } else {
            ArrayList<HashMap<String, String>> list = M("material").where("name like '%"+key+"%'").order("updatetime desc").limit(limit).select();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("updatetime",
                        TimeUtil.stampToDate(list.get(i).get("updatetime"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("status",statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            }
            int num=M("material").where("name like '%"+key+"%'").select().size();
            res.put("list", list);
            res.put("num", num);
        }
        success(res);
    }

    @action
    public void add_materialByExcel(){
        toHtml("admin_tpl/add_materialByExcel");
    }

    @action
    public void do_addByExcel(){
        String url = I("url").toString();
        MaterialExcelModel mem=new MaterialExcelModel(url);
        try {
            List<HashMap<String, String>> ls = mem.do_excel();
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < ls.size(); i++) {
                ls.get(i).put("status",Dictionary.MATERIALADD+"");
                ls.get(i).put("updatetime",TimeUtil.getShortTimeStamp()+"");
                data = ls.get(i);
                long t = mem.add(data);
            }
            success("导入数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            error("导入数据库失败");
        }
    }


    @action
    public void remove() {
        try {
            String id = I("id").toString();
            int t=M("material").where("id="+id).delete();
            if (t>0)
                success("1");
        } catch (Exception e) {
            error("删除失败");
        }
    }

    @action
    public void edit(){
        String id =I("id").toString();
        HashMap<String, String> map = M("material").where("id="+id).field("id,name,price").find();
        assign("info",JSON.toJSON(map));
        toHtml("admin_tpl/edit_material");
    }

    @action
    public void do_edit(){
        String id =I("id").toString();
        String name=I("name").toString();
        String price=I("price").toString();
        HashMap<String, String> map=new HashMap<>();
        map.put("name",name);
        map.put("price",price);
        map.put("status",Dictionary.MATERIALUPDATE+"");
        map.put("updatetime",TimeUtil.getShortTimeStamp()+"");
        try{
            boolean t = M("material").where("id=" + id).save_string(map);
            if (t){
                success("1");
            }
        }catch (Exception e){
            e.printStackTrace();
            error("修改失败");
        }
    }

    @action
    public void getCatInfo(){
        ArrayList<HashMap<String, String>> list = M("material_cat").field("id,name").select();
        success(list);
    }
}
