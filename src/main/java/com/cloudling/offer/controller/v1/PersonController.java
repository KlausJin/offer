package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PersonBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PartsModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.JsonToMapList;

import java.lang.reflect.InvocationTargetException;
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
        PersonModel person = new PersonModel();
        success(person.list("status=1"));
    }


    @action
    public void getManagerList() {
        PersonModel person = new PersonModel();
        success(person.list("status=2"));
    }

    @action
    public void getSalesmanList() {
        PersonModel person = new PersonModel();
        success(person.list("status=3"));
    }

    @action
    public void add() {
        toHtml("admin_tpl/add_staff");
    }

    @action
    public void do_add(){
        PersonBean bean;
        try {
            bean  = (PersonBean) BeanUtil.getBean("person",context,BeanUtil.POST);
        } catch (ParamsErrorException e) {
            e.printStackTrace();
            error(e.getMessage());
            return;
        }
        try{
            PersonModel personModel = new PersonModel();

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
            String password = I("post.new_pwd").toString();
            int t = person.update_pwd(Integer.parseInt(id), password);
            if (t > 0) {
                success("1");
            }
        } catch (Exception e) {
            error("修改密码失败");
        }
    }


}
