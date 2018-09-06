package com.cloudling.offer.controller.v1;


import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.StringUtil;


import java.net.URLDecoder;
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


	// @SuppressWarnings("deprecation")
	// @action
	// public void do_excel() {
	// String searchname=StringUtil.isEmpty(I("post.searchname"))? ""
	// :URLDecoder.decode(I("post.searchname").toString());
	// StringBuffer s=new StringBuffer("SELECT a.*,b.name as tea_name FROM log a
	// LEFT JOIN teacher b on a.uid=b.uid where 1=1");
	// StringBuffer snum=new StringBuffer("SELECT count(*) FROM log a LEFT JOIN
	// teacher b on a.uid=b.uid where 1=1");
	// if(searchname != null && searchname.length()>0) {
	// s.append(" and b.name like '%" + searchname + "%'");
	// snum.append(" and b.name like '%" + searchname + "%'");
	// }
	// String sql=s.toString();
	// try {
	// ArrayList<HashMap<String, String>> list = M("log").query(sql);
	// for(int i=0;i<list.size();i++) {
	// list.get(i).put("create_time",
	// TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
	// }
	// HashMap<String, String> map = new HashMap<>();
	// map.put("id","日志编号");
	// map.put("controller","操作的控制器名称");
	// map.put("action","操作的方法");
	// map.put("create_time","操作时间");
	// map.put("tea_name","操作人员姓名");
	// map.put("getdata","get方式的数据");
	// map.put("postdata","post方式的数据");
	// ExcelMap.exportXls(map, list, "assets/resourses/teacherlog.xls");
	// success("/resourses/teacherlog.xls");
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
