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


    /**
     *
     * @param spare_id
     * @param offer_id
     * @param product_id
     * @return
     */

    public List<AttrBean> getlistBySpareId1(String spare_id,String offer_id,String product_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map =getspareId(spare_id);
        OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res= map.get(i);
            HashMap<String, String> data= offerAttrModel.getMapBySpareId(res.get("id"), offer_id);
            if (data==null){
                AttrBean bean =new AttrBean(res);
                bean.f_attrBeans=new AttrModel().getBeanByAttrId(bean.id,offer_id,product_id);
                list.add(bean);
            }
            else{
                res.replace("id",data.get("attr_id"));
                AttrBean bean =new AttrBean(res);
                HashMap<String,String> emptyData = new HashMap<>();
                AttrBean emptyBean = new AttrBean(emptyData);
                if(data.get("attr_id").equals("-1")){
                    emptyBean.id = "-1";
                    emptyBean.name = "不启用";
                    bean.f_attrBeans=new ArrayList<>();
                    bean.f_attrBeans.add(emptyBean);
                }
                else bean.f_attrBeans=new AttrModel().getBeanByAttrId(data.get("spare_id"),offer_id,product_id);
                list.add(bean);
            }
        }
        return list;
    }


    public List<AttrBean> getBeanByAttrId(String spare_id, String offer_id,String product_id) {
        OfferProductModel offerProductModel = new OfferProductModel();
        ProductModel productModel = new ProductModel();
        MaterialModel materialModel = new MaterialModel();
        OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id);
        HashMap<String, String> catid = productModel.getCatId(product_id);
        HashMap<String, String> res = offerAttrModel.getMapBySpareId(spare_id, offer_id);

        List<AttrBean> list = new ArrayList<>();
        if (res==null|| res.get("attr_id").equals("-1")) {
            ArrayList<HashMap<String, String>> data = getListByParentId(spare_id);
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> res1 = data.get(i);

                if (catid.get("cat_id").equals("32")) {
                    if (res1.get("price").equals("-2.0000")) {
                        res1.replace("price", materialModel.getMaterial(res1.get("name")).get("price"));
                        AttrBean bean = new AttrBean(res1);
                        list.add(bean);

                    } else {
                        AttrBean bean = new AttrBean(res1);
                        list.add(bean);

                    }

                } else {
                    if (res1.get("code").equals("0")) {
                        if (res1.get("price").equals("-2.0000")) {
                            res1.replace("price", null);
                            AttrBean bean = new AttrBean(res1);
                            list.add(bean);
                        } else {
                            AttrBean bean = new AttrBean(res1);
                            list.add(bean);

                        }

                    } else {
                        if (res1.get("code").equals("01")) {
                            AttrBean bean = new AttrBean(res1);
                            list.add(bean);

                        }
                        else if (res1.get("spare_id").equals("出货费用")){
                            AttrBean bean = new AttrBean(res1);
                            list.add(bean);
                        }
                        else {
                            HashMap<String, String> pri = where("parent_id=" + res1.get("parent_id") + " and code=" + "01").field("price").find();
                            double price = Double.parseDouble(pri.get("price")) + Double.parseDouble(res1.get("price"));
                            res1.replace("price", String.valueOf(price));
                            AttrBean bean = new AttrBean(res1);
                            list.add(bean);
                        }


                    }

                }
            }


        }

        else {

            HashMap<String, String> map = getListByAttrId(res.get("attr_id"));
            if (catid.get("cat_id").equals("32")) {
                if (map.get("price").equals("-2.0000")) {
                    map.replace("price", materialModel.getMaterial(map.get("name")).get("price"));
                    AttrBean bean = new AttrBean(map);
                    list.add(bean);
                }

                else {
                    AttrBean bean = new AttrBean(map);
                    list.add(bean);
                }

            }

            else if (catid.get("cat_id").equals("22")) {
                if (map.get("code").equals("01") || map.get("code").equals("0")) {
                    AttrBean bean = new AttrBean(map);
                    list.add(bean);
                } else if (getSpareByParentId(map.get("parent_id")).get("name").equals("出货费用")) {
                    AttrBean bean = new AttrBean(map);
                    list.add(bean);
                } else {
                    HashMap<String, String> pri = where("parent_id=" + map.get("parent_id") + " and code=" + "01").field("price").find();
                    double price = Double.parseDouble(pri.get("price")) + Double.parseDouble(map.get("price"));
                    map.replace("price", String.valueOf(price));
                    AttrBean bean = new AttrBean(map);
                    list.add(bean);
                }

            }
        }





        return list;
    }
    /**
     * @Description:渲染到经理报价页面(常用配件)
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public List<AttrBean> getSpareId_real(String spare_id,String offer_id,String product_id){
           ArrayList<HashMap<String,String>> map=getspareId(spare_id);
        List<AttrBean> list =new ArrayList<>();
           for (int i=0;i<map.size();i++){
               HashMap<String,String> res= map.get(i);
               AttrBean bean =new AttrBean(res);
               bean.f_attrBeans=new AttrModel().getBeanByParentId_real(bean.id,offer_id);
               list.add(bean);
           }
        return list;

    }
    public List<AttrBean> getBeanByParentId_real(String spare_id,String offer_id){
        OfferAttrModel offerAttrModel=new OfferAttrModel();
        HashMap<String ,String> map=offerAttrModel.getMapBySpareId(spare_id,offer_id);
        List<AttrBean> list =new ArrayList<>();
        HashMap<String,String>  res=getListByAttrId(map.get("attr_id"));
        AttrBean bean = new AttrBean(res);
        list.add(bean);
        return list;
    }





    public ArrayList<HashMap<String, String>> getspareId(String sapre_id) {
        ArrayList<HashMap<String, String>> list = where("spare_id =" + sapre_id).select();
        return list;
    }
    public HashMap<String,String> getSpareId_real(String spare_id){
        return where("id="+spare_id).find();
    }

    public ArrayList<HashMap<String, String>> getListByParentId(String spare_id){
        ArrayList<HashMap<String, String>> list= where("parent_id ="+ spare_id).select();
        return list ;
    }


    public HashMap<String,String> getListByAttrId(String attr_id){
        HashMap<String,String> map =where("id="+attr_id).find();
        return map;
    }
    public HashMap<String,String> getSpareByParentId(String parent_id){
        HashMap<String, String> list = where("id=" + parent_id).find();
        return list;
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
     * 计算盒子表面积
     */
    public double box_area(String spare_id,String attr_id){
        HashMap<String, String> attr = where("id=" + attr_id).find();
        HashMap<String, String> kmap = where("name='宽' and spare_id="+ spare_id).field("id").find();
        HashMap<String, String> gmap = where("name='高' and spare_id="+ spare_id).field("id").find();
        HashMap<String, String> hmap = where("name='厚' and spare_id="+ spare_id).field("id").find();
        HashMap<String, String> kres = where("code=" + attr.get("code") + "and parent=" + kmap.get("id")).find();
        HashMap<String, String> gres = where("code=" + attr.get("code") + "and parent=" + gmap.get("id")).find();
        HashMap<String, String> hres = where("code=" + attr.get("code") + "and parent=" + hmap.get("id")).find();
        double num;
        double knum=Double.parseDouble(kres.get("name"));
        double gnum=Double.parseDouble(gres.get("name"));
        double hnum=Double.parseDouble(hres.get("name"));
        num=((knum+hnum)*2+3.7)*((gnum+hnum+(hnum*0.5))+5.4);
        return num;

    }
    /**
     * 计算外箱表面积
     *
     */
    public HashMap<String,String> carton_area(String knum,String gnum,String hnum,String num,String name){
        double area;
        HashMap<String,String> res=new HashMap<>();
        if (num.equals("4")||num.equals("5")||num.equals("6")||num.equals("8")||num.equals("10")||num.equals("12")){
            double kuan=Double.parseDouble(knum)+1.3;
            double chang=(Double.parseDouble(hnum)*Double.parseDouble(num))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }

        }
        else if (num.equals("18")||num.equals("20")||num.equals("24")){
            double kuan=Double.parseDouble(knum)+1.3;
            double chang=(Double.parseDouble(hnum)*(Double.parseDouble(num)/2))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)*2+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)*2+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }
        }
        else if (num.equals("40")||num.equals("48")){
            double kuan=Double.parseDouble(knum)*2+1.3;
            double chang=(Double.parseDouble(hnum)*(Double.parseDouble(num)/4))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)*2+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)*2+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }
        }
        else if (num.equals("60")){
            double kuan=Double.parseDouble(knum)*3+1.3;
            double chang=(Double.parseDouble(hnum)*(Double.parseDouble(num)/6))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)*2+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)*2+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }
        }
        else if (num.equals("80")){
            double kuan=Double.parseDouble(knum)*4+1.3;
            double chang=(Double.parseDouble(hnum)*(Double.parseDouble(num)/8))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)*2+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)*2+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }
        }
        else if (num.equals("100")){
            double kuan=Double.parseDouble(knum)*5+1.3;
            double chang=(Double.parseDouble(hnum)*(Double.parseDouble(num)/10))+2.5;
            if (name.equals("有")){
                double gao=Double.parseDouble(gnum)*2+2;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");


            }
            else {
                double gao=Double.parseDouble(gnum)*2+1.5;
                area=(kuan+chang+5)*(chang+gao+3)*2;
                res.put("长",chang+"");
                res.put("宽",kuan+"");
                res.put("高",gao+"");
                res.put("面积",area+"");

            }
        }

        return  res;


    }


}