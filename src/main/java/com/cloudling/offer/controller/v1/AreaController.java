package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.AreaExcelModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.Md5Util;
import com.cloudling.offer.util.TimeUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-23  17:46
 * @Version 1.0
 **/
public class AreaController extends AdminController {
    public AreaController(ControllerContext context) {
        super(context);
    }


    @action
    public void add_area(){
        toHtml("admin_tpl/add_areaByExcel");
    }

    @action
    public void do_add(){
        String url = I("url").toString();
        AreaExcelModel aem=new AreaExcelModel(url);
        try {
            List<HashMap<String, String>> ls = aem.do_excel();
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < ls.size(); i++) {
                data = ls.get(i);
                long t = aem.add(data);
            }
            success("导入数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            error("导入数据库失败");
        }
    }
}
