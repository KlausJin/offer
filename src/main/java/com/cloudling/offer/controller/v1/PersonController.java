package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PersonBean;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PersonExcelModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.Md5Util;
import com.cloudling.offer.util.StringUtil;
import com.cloudling.offer.util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  16:12
 * @Version 1.0
 **/
public class PersonController extends AdminController {
    public PersonController(ControllerContext context) {
        super(context);
    }


    @action
    public void adminList() {
        toHtml("admin_tpl/admin_list");
    }

    @action
    public void managerList() {
        toHtml("admin_tpl/manager_list");
    }

    @action
    public void salesmanList() {
        toHtml("admin_tpl/salesman_list");
    }

    @action
    public void getAdminList() {
        String page = I("page").toString();
        String key = I("key") == null || I("key").equals("") ? "" : I("key").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        PersonModel person = new PersonModel();
        if ("".equals(key)) {
            ArrayList<HashMap<String, String>> list = person.list("status=1", limit);
            res.put("list", list);
            res.put("num", person.list("status=1").size());
        } else {
            ArrayList<HashMap<String, String>> list = person.list("status=1 and name like '%" + key + "%'", limit);
            res.put("list", list);
            res.put("num", person.list("status=1 and name like '%"+key+"%'").size());
        }
        success(res);
    }


    @action
    public void getManagerList() {
        String page = I("page").toString();
        String key = I("key") == null || I("key").equals("") ? "" : I("key").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        PersonModel person = new PersonModel();
        if ("".equals(key)) {
            ArrayList<HashMap<String, String>> list = person.list("status=2", limit);
            res.put("list", list);
            res.put("num", person.list("status=2").size());
        } else {
            ArrayList<HashMap<String, String>> list = person.list("status=2 and name like '%" + key + "%'", limit);
            res.put("list", list);
            res.put("num", person.list("status=2  and name like '%" + key + "%'").size());
        }
        success(res);
    }

    @action
    public void getSalesmanList() {
        String page = I("page").toString();
        String key = I("key") == null || I("key").equals("") ? "" : I("key").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        PersonModel person = new PersonModel();
        if ("".equals(key)) {
            ArrayList<HashMap<String, String>> list = person.list("status=3 or status=4", limit);
            for (int i=0;i<list.size();i++){
                list.get(i).put("last_login_time",list.get(i).get("last_login_time").equals("0")?"":TimeUtil.stampToDate(list.get(i).get("last_login_time"),"yyyy-MM-dd HH:mm:ss"));
                String follow_id=list.get(i).get("follow_id");
                if (!follow_id.equals("0")){
                    list.get(i).put("follow_id",M("person").field("name").where("id="+follow_id).find().get("name"));
                }else{
                    list.get(i).put("follow_id","");
                }
            }
            res.put("list", list);
            res.put("num", person.list("status=3 or status=4").size());
        }else{
            ArrayList<HashMap<String, String>> list = person.list("(status=3 or status=4) and name like '%" + key + "%' ", limit);
            for (int i=0;i<list.size();i++){
                list.get(i).put("last_login_time",list.get(i).get("last_login_time").equals("0")?"":TimeUtil.stampToDate(list.get(i).get("last_login_time"),"yyyy-MM-dd HH:mm:ss"));
                String follow_id=list.get(i).get("follow_id");
                if (!follow_id.equals("0")){
                    list.get(i).put("follow_id",M("person").field("name").where("id="+follow_id).find().get("name"));
                }else{
                    list.get(i).put("follow_id","");
                }
            }
            res.put("list", list);
            res.put("num", person.list("(status=3 or status=4) and name like '%" + key + "%'").size());
        }
        success(res);
    }

    @action
    public void add() {
        toHtml("admin_tpl/add_staff");
    }

    @action
    public void do_add() {
        PersonBean bean;
        PersonModel pm = new PersonModel();
        try {
            bean = (PersonBean) BeanUtil.getBean("person", context, BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try {
            pm.add_person(bean);
            success("1");
        } catch (Exception e) {
            e.printStackTrace();
            error("录入员工信息失败");
        }
    }

    @action
    public void add_personByExcel(){
        toHtml("admin_tpl/add_personByExcel");
    }


    /**
     * @Description: 读取人员Excel并存入数据库
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void do_add_personByExcel(){
        String url = I("url").toString();
        PersonExcelModel pm = new PersonExcelModel(url);
        try {
            List<HashMap<String, String>> ls = pm.excelToPersonList();
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < ls.size(); i++) {
                ls.get(i).put("password", Md5Util.MD5("123456"));
                ls.get(i).put("create_time", TimeUtil.getShortTimeStamp() + "");
                String follow_name=ls.get(i).get("follow_id");
                if(StringUtil.isEmpty(follow_name)){
                    ls.get(i).remove("follow_id");
                }else{
                    String follow_id = M("person").field("id").where("name='" + follow_name + "'").find().get("id");
                    ls.get(i).put("follow_id",follow_id);
                }
                String a[]=ls.get(i).get("tel").split("\\.");
                ls.get(i).put("tel",a[0]);
                data = ls.get(i);
                long t = pm.add(data);
            }
            success("导入数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            error("导入数据库失败");
        }
    }

    @action
    public void remove() {
        PersonModel person = new PersonModel();
        try {
            String id = I("id").toString();
            person.delete_person(Integer.parseInt(id));
            success("删除成功");
        } catch (Exception e) {
            error("删除失败");
        }
    }

    @action
    public void reset_pwd() {
        PersonModel person = new PersonModel();
        String id = I("id").toString();
        HashMap<String, String> map = person.listone("id=" + id);
        assign("info", JSON.toJSON(map));
        toHtml("admin_tpl/reset_pwd");
    }

    @action
    public void getSaleInfo(){
        PersonModel pm=new PersonModel();
        ArrayList<HashMap<String, String>> list = pm.field("id,name").where("status=" + Dictionary.SALESMAN).select();
        success(list);

    }




}
