package com.cloudling.offer.controller.v1;


import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.ControllerContext;


/**
 * @Description 主页面
 * @Author Administrator
 * @Date 2018-07-09  15:06
 * @Version 1.0
 **/
public class IndexController extends AdminController{

	public IndexController(ControllerContext context) {
		super(context);
		
	}

	@action
	public  void  index(){
		String isadmin=user.get("status");
		switch (isadmin) {
		case "1":
			redirect("/v1//list");break;
		case "2":
			redirect("/v1//list");break;
		case "3":
			redirect("/v1//list");break;
		default:
			break;
		}
	}
}
