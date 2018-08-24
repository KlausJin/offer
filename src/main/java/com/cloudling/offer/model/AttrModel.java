package com.cloudling.offer.model;

import com.cloudling.offer.bean.AttrBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttrModel extends Model {
    public AttrModel (){
        super("attr");
    }



    /**
     * @Description:根据spre_id获取配件属性(固定配件)
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public List<AttrBean> getlistBySpareId(String spare_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map =getspareId(spare_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res= map.get(i);
            AttrBean bean =new AttrBean(res);
            bean.f_attrBeans=new AttrModel().getBeanByParentId(bean.id);
            list.add(bean);}
        return list;
    }

    public List<AttrBean> getBeanByParentId(String spare_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map = getListByParentId(spare_id);
        for (int i=0;i<map.size(); i++){
            HashMap<String,String>  res=map.get(i);
            AttrBean bean = new AttrBean(res);
            list.add(bean);}
        return list;
    }




    /**
     * @Description:渲染到经理报价页面(固定配件)
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */






    public List<AttrBean> getlistBySpareId1(String spare_id,String offer_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map =getspareId(spare_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res= map.get(i);
            AttrBean bean =new AttrBean(res);
            bean.f_attrBeans=new AttrModel().getBeanByAttrId(bean.id,offer_id);
            list.add(bean);}
        return list;
    }


    public List<AttrBean> getBeanByAttrId(String spare_id, String offer_id){
        ArrayList<HashMap<String, String>> res=new OfferattrModel().getMapBySpareId(spare_id,offer_id);
        List<AttrBean> list =new ArrayList<>();

        if (res==null){
            HashMap<String, String> attr_data = where("spare_id=" + spare_id).find();
            ArrayList<HashMap<String, String>> data=getListByParentId(attr_data.get("id"));
            for (int i=0;i<data.size(); i++){
                HashMap<String,String>  res1=data.get(i);
                AttrBean bean = new AttrBean(res1);
                list.add(bean);}
        }
        else{


            for (int j=0;j<res.size();j++){
                HashMap<String, String> map =getListByAttrId(res.get(j).get("attr_id"));
                AttrBean bean =new AttrBean(map);
                list.add(bean);
            }

        }

        return  list;
    }

    public ArrayList<HashMap<String, String>> getspareId(String sapre_id) {
        ArrayList<HashMap<String, String>> list = where("spare_id =" + sapre_id).select();
        return list;
    }

    public ArrayList<HashMap<String, String>> getListByParentId(String spare_id){
       ArrayList<HashMap<String, String>> list= where("parent_id ="+ spare_id).select();
        return list ;
    }


    public HashMap<String,String> getListByAttrId(String attr_id){
        HashMap<String,String> map =where("id="+attr_id).find();
        return map;
    }


    /**
     * 根据配件id删除属性
     * @param spare_id
     */
    public void removeAttrBySpareId(String spare_id){
        ArrayList<HashMap<String, String>> map = where("spare_id =" + spare_id).select();
        for (int i=0;i<map.size();i++){
            where("id="+map.get(i).get("id")).delete();

            removeAttrByParentId(map.get(i).get("id"));
        }
    }

    /**
     * 根据父类id删除子属性
     * @param parent_id
     */
    public void removeAttrByParentId(String parent_id){
        ArrayList<HashMap<String, String>> map = where("parent_id=" + parent_id).select();
        for ( int i=0;i<map.size();i++){
            where("id=" + map.get(i).get("id")).delete();
        }

    }
/**
 * 根据父类id删除子属性
 * @param parent_id
 */
}