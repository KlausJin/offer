package com.klaus.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.klaus.offer.model.Model;
import com.klaus.offer.server.Controller;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.ModelUtil;
import com.klaus.offer.util.TimeUtil;


import java.util.HashMap;

/**
 * @Description 后台权限设置
 * @Author Administrator
 * @Date 2018-07-09  15:06
 * @Version 1.0
 **/
public class AdminController extends Controller {

    public static final int ADMIN = 1;
    public static final int MANAGER = 2;
    public static final int SALESMAN = 3;
    public static final int FOLLOW = 4;

    protected HashMap<String, String> user;
    protected int admin_type = FOLLOW;

    @SuppressWarnings("unchecked")
    public AdminController(ControllerContext context) {
        super(context);
        assign("controller", context.CONTROLLER);
        assign("action", context.ACTION);
        HashMap<String, String> map = M("session").where("sessionid='" + sessionID + "'").find();
        if (map == null) {
            redirect("/v1/access/login");
            pri = false;
            return;
        }
        try {
            assign("session_id", map.get("id"));
            user = JSON.parseObject(map.get("object"), new HashMap<>().getClass());
            admin_type = Integer.parseInt(user.get("status"));
            assign("admin_type", admin_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assign("user", user.get("name"));
        assign("id", admin_type==4?user.get("follow_id"):user.get("id"));
        String role = "";
        switch (admin_type) {
            case 1:
                role = "管理员";
                break;
            case 2:
                role = "经理";
                break;
            case 3:
                role = "业务员";
                break;
            case 4:
                role = "跟单员";
                break;
            default:
                break;
        }
        assign("role", role);
        setLog();
    }

    public void setLog() {
        new Thread() {
            public void run() {
                Model logModel = ModelUtil.getModel("log");
                HashMap<String, String> res = new HashMap<>();
                res.put("controller", context.CONTROLLER);
                res.put("name",user.get("name"));
                res.put("action", context.ACTION);
                res.put("create_time", TimeUtil.getShortTimeStamp() + "");
                res.put("getdata", JSON.toJSONString(context.GET));
                res.put("postdata", JSON.toJSONString(context.POST));
                try {
                    logModel.add(res);
                } catch (Exception e) {
                    e.printStackTrace();
                    error("日志记录失败");
                }
            }
        }.start();
    }

}
