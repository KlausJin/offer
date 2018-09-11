package com.cloudling.offer.model;

import com.cloudling.offer.bean.AttrBean;
import com.cloudling.offer.util.DoubleUtil;

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

    public List<AttrBean> getlistBySpareId1(String spare_id,String offer_id,String product_id,String cat_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map =getspareId(spare_id);
        OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id);
        for (int i=0;i<map.size();i++){
            HashMap<String,String> res= map.get(i);
            HashMap<String, String> data= offerAttrModel.getMapBySpareId(res.get("id"), offer_id);
            if (data==null){
                AttrBean bean =new AttrBean(res);
                bean.f_attrBeans=new AttrModel().getBeanByAttrId(bean.id,offer_id,product_id,cat_id);
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
                else bean.f_attrBeans=new AttrModel().getBeanByAttrId(data.get("spare_id"),offer_id,product_id,cat_id);
                list.add(bean);
            }
        }
        return list;
    }


    public List<AttrBean> getBeanByAttrId(String spare_id, String offer_id,String product_id,String cat_id) {
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

                if (cat_id.equals("32")) {
                    if (res1.get("price").equals("-2.0000")) {
                        res1.replace("price", materialModel.getMaterial(res1.get("name")).get("price"));
                        res1.put("is_material","1");
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
            if(map.get("formula").equals("3")){
                map.put("num",box_area(map)+"");
            }

            if(map.get("formula").equals("4")){
                map.put("num",carton_area(map)+"");
            }

            if (cat_id.equals("32")) {

                if (map.get("price").equals("-2.0000")) {
                    map.replace("price", materialModel.getMaterial(map.get("name")).get("price"));
                    map.put("is_material","1");


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
        OfferAttrModel offerAttrModel = OfferAttrModel.getInstance(offer_id);
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
    public double box_area(HashMap<String, String>  attr){
        HashMap<String,String> parent = where("id="+attr.get("parent_id")).find();
        String spare_id = parent.get("spare_id");
        double knum=0,gnum=0,hnum=0;
        double[] size = getXYZ(spare_id);
        knum = size[0];
        gnum = size[1];
        hnum = size[2];
        return DoubleUtil.mul((DoubleUtil.add(DoubleUtil.mul(DoubleUtil.add(knum,hnum),2),3.7)),
                ((DoubleUtil.add(DoubleUtil.add(gnum,hnum),DoubleUtil.mul(hnum,0.5)))+5.4));


    }

    private double[] getXYZ(String spare_id){
        double[] size = new double[3];
        String sql = "select a.name as attr_name,c.name as value from attr a left join offer_attr b " +
                "on a.id = b.spare_id left join attr c on b.attr_id = c.id where a.spare_id = "+spare_id;
        ArrayList<HashMap<String, String>> list = query(sql);
        double knum=0,gnum=0,hnum=0;


        for(int i=0;i<list.size();i++){
            if(list.get(i).get("attr_name").indexOf("宽")>-1){
                knum = Double.parseDouble(list.get(i).get("value"));
            }
            if(list.get(i).get("attr_name").indexOf("高")>-1){
                gnum = Double.parseDouble(list.get(i).get("value"));
            }
            if(list.get(i).get("attr_name").indexOf("厚")>-1){
                hnum = Double.parseDouble(list.get(i).get("value"));
            }
        }
        size[0]=knum;
        size[1]=gnum;
        size[2]=hnum;
        return size;

    }
    /**
     * 计算外箱表面积
     *
     */

    public double carton_area(HashMap<String,String> attr){
        HashMap<String,String> parent = where("id="+attr.get("parent_id")).find();
        HashMap<String, String> spare = new Model("spare").where("id=" + parent.get("spare_id")).find();
        String product_id = spare.get("product_id");
        HashMap<String,String> box_spare = new Model("spare").where("name like '%盒子%' and product_id="+product_id+"").find();
        double[] size = getXYZ(box_spare.get("id"));

        String spare_id = parent.get("spare_id");
        //装橡树
        String sql = "select c.name as value from attr a left join offer_attr b on a.id = b.spare_id " +
                "left join attr c on b.attr_id = c.id where a.spare_id = "+spare_id +
                " and a.name like '%装箱数%'";
        HashMap<String,String> box  = query(sql).get(0);

        //有无垫片
        String sql2 = "select c.name as value from attr a left join offer_attr b on a.id = b.spare_id " +
                "left join attr c on b.attr_id = c.id where a.spare_id = "+spare_id +
                " and a.name like '%有无垫片%'";
        HashMap<String,String> box2  = query(sql2).get(0);

        HashMap<String, String> res = carton_area(size[0] + "", size[1] + "", size[2] + "",
                (int)Float.parseFloat(box.get("value"))+"", box2.get("value"));

       return Double.parseDouble(res.get("面积"))/(int)Float.parseFloat(box.get("value"));



    }

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