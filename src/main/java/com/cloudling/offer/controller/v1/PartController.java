package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PartBean;
import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.model.PersonModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.ImportExcelUtil;
import com.cloudling.offer.util.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  12:15
 * @Version 1.0
 **/
public class PartController extends Controller {
    public PartController(ControllerContext context) {
        super(context);
    }

    @action
    public void add_part(){
        toHtml("admin_tpl/add_part");
    }

    @action
    public void do_add_part() {
        String url = I("url").toString();
        PartModel partModel = new PartModel();
        try {
            List<LinkedHashMap<String, Object>> ls = partModel.fromExcel(url);

            for(int i=0;i<ls.size();i++){
                LinkedHashMap<String,String> res = new LinkedHashMap<>();
                res.put("create_time",TimeUtil.getShortTimeStamp()+"");
                res.put("content",JSON.toJSONString(ls.get(i)));
                partModel.addDraft(res);
            }
           success("1");
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
        }
    }


    @action
    public void edit(){
    }

    @action
    public  void do_edit(){
    }

    @action
    public  void list(){
        toHtml("admin_tpl/part_list");
    }
}
