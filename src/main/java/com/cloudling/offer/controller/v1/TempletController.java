package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-18  14:51
 * @Version 1.0
 **/
public class TempletController extends AdminController {
    public TempletController(ControllerContext context) {
        super(context);
    }


    //模板系统导入
    @action
    public void addAllTemplet() {
        HashMap<String, String> res = new HashMap<>();
        String path = "assets/templet/clock";//"assets/templet/watch"   //要遍历的路径
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        for (int i = 0; i < fs.length; i++) {
            String[] pic_name = (fs[i] + "").split("\\\\|\\.");
            res.put("url", fs[i]+"");
            res.put("name",pic_name[3]);
            res.put("cat_id", Dictionary.CLOCK + "");//Dictionary.WATCH
            try {
                long t = M("offer_templet").add(res);
                if(t>0){
                    success("1");
                }
            } catch (Exception e) {
                e.printStackTrace();
                error("0");
            }
        }
    }

    @action
    public void templet_list(){
        toHtml("admin_tpl/templet_list");
    }

    @action
    public void getTempletList(){
        String page = I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        String sql="select a.*,b.name as pro_name,c.name as sale_name from offer_templet a left join part_cat b on a.cat_id=b.id left join person c on a.sale_id=c.id where 1=1";
        StringBuffer sb=new StringBuffer(sql);
        try {
            ArrayList<HashMap<String, String>> snum = M("offer_templet").query(sb.toString());
            ArrayList<HashMap<String, String>> list = M("offer_templet").query(sb.append(" limit "+limit).toString());
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("create_time",
                        TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("sale_name",list.get(i).get("sale_name")==null || list.get(i).get("sale_name").equals("") ? "公司通用":list.get(i).get("sale_name"));
            }
            res.put("list", list);
            res.put("num", snum.size());
            success(res);
        }catch (Exception e){
            e.printStackTrace();
            error("查看模板列表失败");
        }

    }



    @action
    public void remove() {
        try {
            String id = I("id").toString();
            int t = M("offer_templet").where("id=" + id).delete();
            if(t>0) {
                success("删除成功");
            }
        } catch (Exception e) {
            error("删除失败");
        }
    }




}
