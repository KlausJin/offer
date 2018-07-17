package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartsModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  16:12
 * @Version 1.0
 **/
public class PersonController extends Controller {
    public PersonController(ControllerContext context) {
        super(context);
    }


    @action
    public void Adminlist(){
        toHtml("tpl/admin_tpl/admin_list");
    }

    @action
    public void Managerlist(){
        toHtml("tpl/admin_tpl/manager_list");
    }

    @action
    public void Salesmanlist(){
        toHtml("tpl/admin_tpl/salesman_list");
    }

    @action
    public void getAdminlist() {
        PersonModel person = new PersonModel("person");
        success(person.list("status=1"));
    }


    @action
    public void getManagerlist() {
        PersonModel person = new PersonModel("person");
        success(person.list("status=2"));
    }

    @action
    public void getSalesmanlist() {
        PersonModel person = new PersonModel("person");
        success(person.list("status=3"));
    }

    @action
    public void add() {

    }

    @action
    public void delete() {
        PersonModel person = new PersonModel("person");
        try {
            String id = I("id").toString();
            person.delete_person(Integer.parseInt(id));
            success("删除成功");
        } catch (Exception e) {
            error("删除失败");
        }
    }

    @action
    public void update_pwd() {
        PersonModel person = new PersonModel("person");
        try {
            String id = I("id").toString();
            String password = I("password").toString();
            int t = person.update_pwd(Integer.parseInt(id), password);
            if (t > 0) {
                success("修改密码成功");
            }
        } catch (Exception e) {
            error("修改密码失败");
        }
    }


}
