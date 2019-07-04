package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.server.Controller;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.Md5Util;

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



	@action
	public void do_login() {
		String username = I("post.user").toString();
		String password = Md5Util.MD5(I("post.password").toString());
		HashMap<String, String> map = M("person").where("name='" + username + "' and password='" + password + "'").find();
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
