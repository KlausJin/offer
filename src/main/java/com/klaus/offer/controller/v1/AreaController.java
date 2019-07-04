package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.model.AreaExcelModel;
import com.klaus.offer.model.EnglishReferModel;
import com.klaus.offer.server.ControllerContext;

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
        if (admin_type>MANAGER){
            pri=false;
            return;
        }
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

    @action
    public void add_english_refer(){
        toHtml("admin_tpl/add_english_refer");
    }

    @action
    public void do_add_english_refer(){
        String url = I("url").toString();
        EnglishReferModel erm=new EnglishReferModel(url);
        try {
            List<HashMap<String, String>> ls = erm.do_excel();
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < ls.size(); i++) {
                data = ls.get(i);
                long t = erm.add(data);
            }
            success("导入数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            error("导入数据库失败");
        }
    }
}
