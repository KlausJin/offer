package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.config.Dictionary;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  17:15
 * @Version 1.0
 **/
public class OfferAdminController extends AdminController {
    public OfferAdminController(ControllerContext context) {
        super(context);
        if (admin_type>ADMIN){
            pri=false;
            return;
        }
    }

    @action
    public void list(){
        toHtml("admin_tpl/offer_admin_list");
    }

    @action
    public void getAdminOfferList() {
        String page = I("page").toString();
        String limit = Integer.parseInt(page) * 10 + ",10";
        String search_name=I("search_name")==""?"":I("search_name").toString();
        String search_status=I("search_status").equals("2")?"":I("search_status").toString();
        HashMap<String, Object> res = new HashMap<>();
        HashMap<Integer, String> statusTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put(Dictionary.ISOFFER, "已报价");
                put(Dictionary.NOOFFER, "待报价");
            }
        };
        HashMap<Integer, String> catTypes = new HashMap<Integer, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 2L;

            {
                put(Dictionary.WATCH, "手表");
                put(Dictionary.CLOCK, "时钟");
            }
        };
        String sql="select a.*,b.name as client_name,c.name as sale_name,d.name as m_name from offer a left join client b on a.client_id=b.id left join person c on a.sale_id=c.id LEFT JOIN person d on a.m_id=d.id where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        StringBuffer snum=new StringBuffer(sql);
        if (search_name.length()>0){
            sb.append(" and b.name like '%"+search_name+"%'");
            snum.append(" and b.name like '%"+search_name+"%'");
        }
        if (search_status.length()>0){
            sb.append(" and a.status="+search_status);
            snum.append(" and a.status="+search_status);
        }
        sb.append(" order by id desc ");
        sb.append(" limit "+limit);
        ArrayList<HashMap<String, String>> list = M("offer").query(sb.toString());
        ArrayList<HashMap<String, String>> list1 = M("offer").query(snum.toString());
        for (int i=0;i<list.size();i++){
            list.get(i).put("create_time",
                    TimeUtil.stampToDate(list.get(i).get("create_time"), "yyyy-MM-dd HH:mm:ss"));
            list.get(i).put("status",statusTypes.get(Integer.parseInt(list.get(i).get("status"))));
            list.get(i).put("cat_id",catTypes.get(Integer.parseInt(list.get(i).get("cat_id"))));
            list.get(i).put("m_name",list.get(i).get("m_name")==null || list.get(i).get("m_name").equals("")?"暂无":list.get(i).get("m_name"));
        }
        res.put("list", list);
        res.put("num", list1.size());
        success(res);
    }
}
