package com.cloudling.offer.controller.v1;


import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;

import com.cloudling.offer.config.Dictionary;
import com.cloudling.offer.model.ClientExcelModel;

import com.cloudling.offer.model.ClientModel;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-28  12:52
 * @Version 1.0
 **/
public class ClientController extends AdminController {
    public ClientController(ControllerContext context) {
        super(context);
    }


    @action
    public void list() {
        toHtml("admin_tpl/client_list");
    }


    @action
    public void getClientList() {
        String page = I("page").toString();
        String key = I("key") == null || I("key").equals("") ? "" : I("key").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        HashMap<String, Object> res = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        if ("".equals(key)) {
            String sql = "select a.*,b.name as sale_name,c.name as client_from,d.area_name as area_name from client a left join person b on a.sale_id=b.id left join client_from c on a.client_from_id=c.id left join area d on a.area_id=d.id";
            String sql1 = "select a.*,b.name as sale_name,c.name as client_from,d.area_name as area_name from client a left join person b on a.sale_id=b.id left join client_from c on a.client_from_id=c.id left join area d on a.area_id=d.id";
            sb.append(sql);
            sb1.append(sql1);
            if (admin_type == Dictionary.SALESMAN) {
                sb.append(" where a.sale_id=" + user.get("id")+" or a.sale_id=0");
                sb1.append(" where a.sale_id=" + user.get("id")+" or a.sale_id=0");
            } else if (admin_type == Dictionary.FOLLOW) {
                sb.append(" where a.sale_id=" + user.get("follow_id")+" or a.sale_id=0");
                sb1.append(" where a.sale_id=" + user.get("follow_id")+" or a.sale_id=0");
            }
            sb.append(" order by a.create_time desc limit " + limit);
            ArrayList<HashMap<String, String>> list = M("client").query(sb.toString());
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("sale_name",list.get(i).get("sale_name")==null?"公司通用":list.get(i).get("sale_name"));
            }
            res.put("list", list);
            res.put("num", M("client").query(sb1.toString()).size());
        } else {
            String sql = "select a.*,b.name as sale_name,c.name as client_from,d.area_name as area_name from client a left join person b on a.sale_id=b.id left join client_from c on a.client_from_id=c.id left join area d on a.area_id=d.id  where a.name like '%" + key + "%'";
            String sql1 = "select a.*,b.name as sale_name,c.name as client_from,d.area_name as area_name from client a left join person b on a.sale_id=b.id left join client_from c on a.client_from_id=c.id left join area d on a.area_id=d.id where a.name like '%" + key + "%'";
            sb.append(sql);
            sb1.append(sql1);
            if (admin_type == Dictionary.SALESMAN) {
                sb.append(" and a.sale_id=" + user.get("id")+" or a.sale_id=0");
                sb1.append(" and a.sale_id=" + user.get("id")+" or a.sale_id=0");
            } else if (admin_type == Dictionary.FOLLOW) {
                sb.append(" and a.sale_id=" + user.get("follow_id")+" or a.sale_id=0");
                sb1.append(" and a.sale_id=" + user.get("follow_id")+" or a.sale_id=0");
            }
            sb.append(" order by a.create_time desc limit " + limit);
            ArrayList<HashMap<String, String>> list = M("client").query(sb.toString());
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("create_time", TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
                list.get(i).put("sale_name",list.get(i).get("sale_name")==null?"公司通用":list.get(i).get("sale_name"));
            }
            res.put("list", list);
            res.put("num", M("client").query(sb1.toString()).size());
        }
        success(res);
    }

    /**
     * @Description: 客户Excel模板导入
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    @action
    public void add_clientByExcel() {
        toHtml("admin_tpl/add_clientByExcel");
    }

    @action
    public void do_add_excel() {
        String url = I("url").toString();
        ClientExcelModel cem = new ClientExcelModel(url);
        try {
            List<HashMap<String, String>> ls = cem.excelToClientList();
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < ls.size(); i++) {
                ls.get(i).put("create_time", TimeUtil.getShortTimeStamp() + "");
                String sale_name = ls.get(i).get("sale_id");
                String client_from = ls.get(i).get("client_from_id");
                String sale_id = M("person").field("id").where("name='" + sale_name + "'").find().get("id");
                String client_from_id = M("client_from").field("id").where("name='" + client_from + "'").find().get("id");
                ls.get(i).put("sale_id", sale_id);
                ls.get(i).put("client_from_id", client_from_id);
                data = ls.get(i);
                long t = cem.add(data);
            }
            success("导入数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            error("导入数据库失败");
        }
    }

    @action
    public void add() {
        HashMap<String, String> data = null;
        if (admin_type == 4) {
            data = M("person").where("id=" + user.get("follow_id")).find();
        } else {
            data = M("person").where("id=" + user.get("id")).find();
        }
        assign("info", JSON.toJSON(data));
        toHtml("admin_tpl/add_client");
    }

    @action
    public void do_add() {
        String client_name = I("client_name").toString();
        String client_area = I("client_area").toString();
        String client_from = I("client_from").toString();
        String sale_id = admin_type==FOLLOW?user.get("follow_id"):user.get("id");
        ClientModel cm = new ClientModel();
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> cp = M("default_client_profit").where("status=" + Dictionary.COUNTRYTRADE).find();
        HashMap<String, String> ep = M("default_client_profit").where("status=" + Dictionary.FOREIGNTRADE).find();
        try {
            if (client_area.equals(Dictionary.COUNTRYTRADE + "")) {
                data.put("watchle5k", cp.get("watchle5k"));
                data.put("watch5k_10k", cp.get("watch5k_10k"));
                data.put("watch10k_50k", cp.get("watch10k_50k"));
                data.put("watch50k_100k", cp.get("watch50k_100k"));
                data.put("clockle500", cp.get("clockle500"));
                data.put("clock500_1k", cp.get("clock500_1k"));
                data.put("clock1k_3k", cp.get("clock1k_3k"));
                data.put("clock3k_5k", cp.get("clock3k_5k"));
                data.put("clock5k_10k", cp.get("clock5k_10k"));
                data.put("clock10k_20k", cp.get("clock10k_20k"));
                data.put("clockme20k", cp.get("clockme20k"));
            } else {
                data.put("watchle5k", ep.get("watchle5k"));
                data.put("watch5k_10k", ep.get("watch5k_10k"));
                data.put("watch10k_50k", ep.get("watch10k_50k"));
                data.put("watch50k_100k", ep.get("watch50k_100k"));
                data.put("clockle500", ep.get("clockle500"));
                data.put("clock500_1k", ep.get("clock500_1k"));
                data.put("clock1k_3k", ep.get("clock1k_3k"));
                data.put("clock3k_5k", ep.get("clock3k_5k"));
                data.put("clock5k_10k", ep.get("clock5k_10k"));
                data.put("clock10k_20k", ep.get("clock10k_20k"));
                data.put("clockme20k", ep.get("clockme20k"));
            }
            data.put("name", client_name);
            data.put("sale_id", sale_id);
            data.put("client_from_id", client_from);
            data.put("area_id", client_area);
            data.put("create_time", TimeUtil.getShortTimeStamp() + "");
            long t = cm.add(data);
            if (t > 0) {
                success(cm.where("id=" + t).find());
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("添加客户失败");
        }


    }

    @action
    public void remove() {
        try {
            String id = I("id").toString();
            int t = M("client").where("id=" + id).delete();
            if (t > 0)
                success("1");
        } catch (Exception e) {
            error("删除失败");
        }
    }


    @action
    public void getClientFrom() {
        ArrayList<HashMap<String, String>> list = M("client_from").field("id,name").select();
        success(list);
    }

    @action
    public void getArea() {
        ArrayList<HashMap<String, String>> list = M("area").field("id,area_name").select();
        success(list);
    }
}
