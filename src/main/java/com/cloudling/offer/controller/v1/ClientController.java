package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientController extends Controller {

    public ClientController(ControllerContext context) {
        super(context);
    }
    /**
     * 增加客户
     *
     * @param
     */
    @action
    public void add (){
        toHtml("");
    }
    @action
    public void do_add(){
        String name, logo;

        try{
            name= I("post.name").toString();
            logo=I("post.logo").toString();
        }
        catch (Exception e){
            // TODO: handle exception
            error("参数提交错误");
            return;

        }
        String remark = I("post.remark") == null ? "0" : I("post.remark").toString();
        HashMap<String, String> res = new HashMap<>();
        res.put("name",name);
        res.put("logo",logo);
        if (isempty(remark)){res.put("remark",remark);}
        try {
            M("client").add(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("数据加载到数据库失败");

        }
        assign("res",JSON.toJSON(res));

    }
    /**
     * 修改客户
     *
     * @param
     */
    @action
    public void  edit_client() {
        String id = I("post.id") == null ? "" : I("post.id").toString();
        String name = I("post.name") == null ? "" : I("post.name").toString();
        String logo = I("post.logo") == null ? "" : I("post.logo").toString();

        if (id.equals("")) {
            error("id不能为空");
            return;
        }
        HashMap<String, Object> res = new HashMap<>();
        if (isempty(name)){res.put("name",name);}
        if (isempty(logo)){res.put("logo",logo);}
        try {
            M("client").where("id=" + id).save(res);
            success("数据库更新成功");
        } catch (Exception e) {
            // TODO: handle exception
            error("参数修改错误");
        }

    }
    /**
     * 客户列表
     *
     * @param
     */
    @action
    public void get_list(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String sql="select * , (select count(*) from offer where offer.client_id=client.id) as count,(select max(create_time) from offer where offer.client_id=client.id) as max from client  limit "+limit;
        ArrayList<HashMap<String, String>> list = M("client").query(sql);
        for (int i =0;i<list.size();i++){
            HashMap<String, String> map = list.get(i);
            int count = Integer.parseInt(map.get("count"));
            map.put("create_time", TimeUtil.stampToDate(map.get("create_time"), "yyyy-MM-dd HH:mm:ss"));
        }
        success(list);
    }
    /**
     * 查看报价详情
     *
     * @param
     */
@action
public void check_offer(){
    String id = I("id") == null ? "0" : I("id").toString();
    String sql="select * from offer where client_id="+id+" and create_time=(select max(create_time) from offer where client_id= "+id+") ";
    ArrayList<HashMap<String, String>> list = M("offer").query(sql);
    for (int i=0;i<list.size();i++){
        ArrayList<HashMap<String, String>> map = M("offer_product").where("id =" + list.get(i).get("id")).select();
        ArrayList<HashMap<String, String>> map1 = M("offer_spare").where("id=" + list.get(i).get("id")).select();
        list.addAll(map);
        list.addAll(map1);
    }
    success(list);
}
    /**
     * 删除客户
     *
     * @param
     */
@action
public void remove(){
    String id = I("id") == null ? "0" : I("id").toString();
    try {
        M("client").where("id=" + id).delete();
        success("删除成功");
    } catch (Exception e) {
        // TODO: handle exception
        error("删除失败");

    }
}


@action
public void do_get_client(){
    String page = I("page") == null ? "0" : I("page").toString();
    String limit = Integer.parseInt(page) * 10 + ",10";
    String client_name = I("post.client_name") == null ? "0" : I("post.client_name").toString();
    String sql="select * from client where name like %" + client_name+"% limit " + limit;
}
    /**
     * 搜索客户
     *
     * @param
     */
    @action
    public void get_client(){
        String page = I("page") == null ? "0" : I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String client_name = I("post.client_name") == null ? "0" : I("post.client_name").toString();
        String sql="select * , (select count(*) from offer where offer.client_id=client.id) as count,(select max(create_time) from offer where offer.client_id=client.id) as max from client where name like %" + client_name+"% limit " + limit;
        ArrayList<HashMap<String, String>> list = M("client").query(sql);
        for (int i =0;i<list.size();i++){
            HashMap<String, String> map = list.get(i);
            int count = Integer.parseInt(map.get("count"));
            map.put("create_time", TimeUtil.stampToDate(map.get("create_time"), "yyyy-MM-dd HH:mm:ss"));
        }
        success(list);
    }

}
