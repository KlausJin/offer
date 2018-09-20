package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PictureModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PictureController extends AdminController {
    public PictureController(ControllerContext context) {
        super(context);
    }

    @action
    public void add() {
        toHtml("admin_tpl/picture_edit");
    }
    /**
     * 管理员上传图片
     *
     * @param
     */
    @action
    public void addPicture() {
        String url = I("url") == null ? "" : I("url").toString();
        String name = I("name") == null ? "" : I("name").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("pic", url);
        res.put("name", name);

        try {
            M("picture").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }

    /**
     * 业务员上传图片
     *
     * @param
     */

    @action
    public void addPicture_sale() {
        String url = I("url") == null ? "" : I("url").toString();
        String name = I("name") == null ? "" : I("name").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("pic", url);
        res.put("name", name);
        res.put("sale_id",user.get("id"));
        try {
            M("picture").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }

    /**
     * 遍历文件中的图片
     *
     *
     * @param
     */
    @action
    public void addAllPicture(){
        HashMap<String, String> res = new HashMap<>();
        String path = "assets/images/offer_picture1";	//要遍历的路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
               for (int i=0;i<fs.length;i++){
                   String[] pic_name=(fs[i]+"").split("\\\\|\\.");
                   res.put("name",pic_name[3]);
                   res.put("pic",fs[i]+"");
                   try {
                       M("picture").add(res);
                   } catch (Exception e) {
                       e.printStackTrace();
                       // TODO: handle exception
                       error("数据加载到数据库失败");
                   }
               }

          success("上传成功");



    }


    /**
     *选择图片
     *
     * @param
     */
    @action
    public void select_picture() {
        toHtml("admin_tpl/picture_edit_sale");
    }


    @action
    public void getPicture(){
        PictureModel pictureModel=new PictureModel();

        String name = I("name") == null ? "" : I("name").toString();
        ArrayList<HashMap<String, String>> list = pictureModel.getPictureByName(name,user.get("id"));
        try {

            success(list);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("没有此产品");
        }
    }

    /**
     *选择的图片存到offer_product
     *
     * @param
     */
    @action
    public void addProductPic(){
        String id = I("id") == null ? "" : I("id").toString();
        String pic_id = I("pic_id") == null ? "" : I("pic_id").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("picture_id", pic_id);
        try {
            M("offer_product").where("id="+id).add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            error("数据加载到数据库失败");
        }
    }

}

