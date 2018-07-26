package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.Md5Util;

import java.util.HashMap;


/**
 * @Description 后台登录
 * @Author Administrator
 * @Date 2018-07-09  15:06
 * @Version 1.0
 **/
public class AccessController extends Controller {

	public AccessController(ControllerContext context) {
		super(context);
	}

	@action
	public void login() {
		toHtml("admin_tpl/staff_login");
	}

	public  void test(String s){

	}
	@action
	public void do_login() {
		String username = I("post.user").toString();
		String password = Md5Util.MD5(I("post.password").toString());
		HashMap<String, String> map = M("person").where("username=" + username + " and password='" + password + "'").find();
		if (map != null) {
			try {
				session(map);
				success("1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			error("请输入正确的用户名密码");
		}

	}
}
