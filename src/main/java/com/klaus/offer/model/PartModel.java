package com.klaus.offer.model;

import com.klaus.offer.bean.PartBean;
import com.klaus.offer.util.TimeUtil;

import java.util.*;


/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  14:47
 * @Version 1.0
 **/
public class PartModel extends Model {
    public static final String CODE = "code";
    public static final String ATTR = "attr";
    public static final String PRICE = "price";

    public PartModel() {

        super("part");
    }

    public void add_part(PartBean bean) throws Exception {
        bean.create_time = TimeUtil.getShortTimeStamp();
        add(bean);
    }

    public void delete_part(int id) {
        where("id=" + id).delete();
    }

    public void addDraft(HashMap<String, String> data) throws Exception {
        new Model("part_draft").add(data);
    }

    public ArrayList<HashMap<String, String>> DraftList(String limit) throws Exception {
        return new Model("part_draft").limit(limit).select();
    }

    public ArrayList<HashMap<String, String>> DraftList() throws Exception {
        return new Model("part_draft").select();
    }

    public int draftCount() {
        return new Model("part_draft").count();
    }


    public void update(int id, HashMap<String, String> map) {
        where("id=" + id).save_string(map);
    }


    public ArrayList<HashMap<String, String>> list(String condition) {
        return where(condition).select();
    }

    public HashMap<String, String> listone(String condition) {
        return where(condition).find();
    }

    public ArrayList<HashMap<String, String>> list() {
        return select();
    }
}
