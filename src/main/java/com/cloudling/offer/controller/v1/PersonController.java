package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PersonBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.Md5Util;
import com.cloudling.offer.util.StringUtil;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  16:12
 * @Version 1.0
 **/
public class PersonController extends Controller {
    public PersonController(ControllerContext context)
    {
        super(context);
    }


    @action
    public void adminList(){
        toHtml("admin_tpl/admin_list");
    }

    @action
    public void managerList()
    {
        toHtml("admin_tpl/manager_list");
    }

    @action
    public void salesmanList()
    {
        toHtml("admin_tpl/salesman_list");
    }

    @action
    public void getAdminList() {
        String page= I("get.page").toString();
        String limit=Integer.parseInt(page) * 10 +",10";
        HashMap<String,Object> res=new HashMap<>();
        PersonModel person = new PersonModel();
        ArrayList<HashMap<String,String>> list = person.list("status=1",limit);
        res.put("list", list);
        res.put("num", person.list("status=1").size());
        success(res);
    }


    @action
    public void getManagerList() {
        String page= I("get.page").toString();
        String limit=Integer.parseInt(page) * 10 +",10";
        HashMap<String,Object> res=new HashMap<>();
        PersonModel person = new PersonModel();
        ArrayList<HashMap<String,String>> list = person.list("status=2",limit);
        int num=list.size();
        res.put("list", list);
        res.put("num", person.list("status=2").size());
        success(res);
    }

    @action
    public void getSalesmanList() {
        String page= I("get.page").toString();
        String limit=Integer.parseInt(page) * 10 +",10";
        HashMap<String,Object> res=new HashMap<>();
        PersonModel person = new PersonModel();
        ArrayList<HashMap<String,String>> list = person.list("status=3",limit);
        int num=list.size();
        res.put("list", list);
        res.put("num", person.list("status=3").size());
        success(res);
    }

    @action
    public void add() {
        toHtml("admin_tpl/add_staff");
    }

    @action
    public void do_add(){
        PersonBean bean;
        PersonModel pm = new PersonModel();
        try {
            bean  = (PersonBean) BeanUtil.getBean("person",context,BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{
            pm.add_person(bean);
            success("1");
        }catch (Exception e){
            e.printStackTrace();
            error("录入员工信息失败");
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
    public void do_reset_pwd() {
        PersonModel person = new PersonModel();
        try {
            String id = I("post.id").toString();
            String password = Md5Util.MD5(I("post.old_pwd").toString());
            String passwordn = Md5Util.MD5(I("post.new_pwd").toString());
            HashMap<String, String> map = person.listone("id=" + id + " and password='" + password+"'");
            if (map != null) {
                person.update_pwd(Integer.parseInt(id),passwordn);
                success("1");
            }else{
                error("原密码错误，请重新输入原密码");
            }
        } catch (Exception e) {
            error("修改密码失败");
        }
    }


}
