package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.HashMap;

public class PictureController extends AdminController {
    public PictureController(ControllerContext context) {
        super(context);
    }

    @action
    public void add() {
        toHtml("admin_tpl/picture_edit");
    }

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
}
