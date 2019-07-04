package com.klaus.offer.controller.v1;


import com.klaus.offer.annotation.action;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.TimeUtil;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description 日志管理列表
 * @Author Administrator
 * @Date 2018-07-09  15:06
 * @Version 1.0
 **/
public class LogController extends AdminController {

	public LogController(ControllerContext context) {
		super(context);
		if (admin_type > ADMIN) {
			pri = false;
			return;
		}
	}

	@action
	public void list() {
		toHtml("admin_tpl/log_list");
	}

	@action
	public void getSearchList() {
		String page = I("get.page").toString();
		String limit = Integer.parseInt(page) * 10 + ",10";
		String searchname =I("searchname").toString();
		StringBuffer s = new StringBuffer("SELECT * from log  where 1=1");
		StringBuffer snum = new StringBuffer("SELECT * from log where 1=1");
		if (searchname != null && searchname.length() > 0) {
			s.append(" and name like '%" + searchname + "%'");
			snum.append(" and name like '%" + searchname + "%'");
		}
		s.append(" order by create_time desc");
		s.append(" limit " + limit);
		HashMap<String, Object> res = new HashMap<>();
		try {
			ArrayList<HashMap<String, String>> list = M("log").query(s.toString());
			ArrayList<HashMap<String, String>> list1 = M("log").query(snum.toString());
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("create_time",
						TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
			}
			res.put("num", list1.size());
			res.put("list", list);
			success(res);
		} catch (Exception e) {
			error("查询失败");
		}
	}
}
