package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.config.Dictionary;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.TimeUtil;
import com.klaus.offer.util.UploadUtil;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
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
        if (admin_type!=MANAGER){
            pri=false;
            return;
        }
    }


    //模板系统导入
    @action
    public void addAllTemplet() {
        HashMap<String, String> res = new HashMap<>();
        String path = "assets/templet";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        for (int i = 0; i < fs.length; i++) {
            String[] pic_name = (fs[i] + "").split("\\\\|\\.");
            String x=fs[i]+"";
            res.put("url", x.replaceAll("\\\\","/"));
            res.put("name",pic_name[2]);
            res.put("cat_id", Dictionary.WATCH + "");//Dictionary.WATCH
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
    public void addTemplet(){
        toHtml("admin_tpl/add_templet");
    }


    @action
    public void upload() {
        try {
            success(UploadUtil.uploadOffer(context));
        } catch (IOException | ServletException | FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error("上传失败");
        }
    }

    @action
    public void do_addTemplet(){
        String cat_id=I("cat_id").toString();
        String sale_id=I("sale_id").toString();
        String file_name=I("url").toString();
        String name=file_name.substring(0,file_name.indexOf("."));
        String file_url="assets/templet/"+file_name;
        HashMap<String,String> data=new HashMap<>();
        data.put("cat_id",cat_id);
        data.put("url",file_url);
        data.put("sale_id",sale_id);
        data.put("name",name);
        data.put("create_time",TimeUtil.getShortTimeStamp()+"");
        try {
            long t = M("offer_templet").add(data);
            if(t>0){
                success("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("报价模板新增失败");
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
            ArrayList<HashMap<String, String>> list = M("offer_templet").query(sb.append(" order by create_time desc limit "+limit).toString());
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


    @action
     public void getSaleList(){
        ArrayList<HashMap<String, String>> list = M("person").field("id,name").where("status=" + Dictionary.SALESMAN).select();
        success(list);
    }


    @action
    public void getCatInfo() {
        ArrayList<HashMap<String, String>> list = M("part_cat").field("id,name").select();
        success(list);
    }



}
