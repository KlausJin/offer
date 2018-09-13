package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.OfferModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfitController extends Controller {
    public ProfitController(ControllerContext context) {
        super(context);
    }

    @action
    public void getProfit(){

        String offer_id = I("offer_id") == null ? "" : I("offer_id").toString();
        String num = I("num") == null ? "" : I("num").toString();
        String is_tax = I("is_tax") == null ? "" : I("is_tax").toString();
        String price = I("price") == null ? "" : I("price").toString();
        Double dollar=Double.parseDouble((M("dictionary").where("id="+2).find()).get("value"));
        Double  tax=Double.parseDouble((M("dictionary").where("id="+1).find()).get("value"));
        HashMap<String, String> map = M("offer").where("id=" + offer_id).find();
        HashMap<String, String> res = M("client").where("id=" + map.get("client_id")).find();
        ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + offer_id).select();
        HashMap<String, String> data = M("product").where("id=" + list.get(0).get("product_id")).find();
        HashMap<String,String> pro=new HashMap<>();
       if (data.get("cat_id").equals("22")){
           if (Integer.parseInt(num)<=5000){
               pro.put("profit",res.get("watchle5k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watchle5k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                  Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watchle5k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watchle5k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }

           }
           if ( Integer.parseInt(num)>5000 && Integer.parseInt(num)<=10000 ){
               pro.put("profit",res.get("watch5k_10k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch5k_10k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch5k_10k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch5k_10k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>10000 && Integer.parseInt(num)<=50000 ){
               pro.put("profit",res.get("watch10k_50k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch10k_50k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch10k_50k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch10k_50k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>50000 && Integer.parseInt(num)<=100000 ){
               pro.put("profit",res.get("watch50k_100k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch50k_100k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch50k_100k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("watch50k_100k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }

       }
       else{
           if (Integer.parseInt(num)<=500){
               pro.put("profit",res.get("clockle500"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockle500")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockle500"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockle500")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>500 && Integer.parseInt(num)<=1000 ){
               pro.put("profit",res.get("clock500_1k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock500_1k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock500_1k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock500_1k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>1000 && Integer.parseInt(num)<=3000 ){
               pro.put("profit",res.get("clock1k_3k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock1k_3k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock1k_3k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock1k_3k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>3000 && Integer.parseInt(num)<=5000 ){
               pro.put("profit",res.get("clock3k_5k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock3k_5k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock3k_5k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock3k_5k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>5000 && Integer.parseInt(num)<=10000 ){
               pro.put("profit",res.get("clock5k_10k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock5k_10k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock5k_10k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock5k_10k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>10000 && Integer.parseInt(num)<=20000 ){
               pro.put("profit",res.get("clock10k_20k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock10k_20k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock10k_20k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clock10k_20k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }
           if ( Integer.parseInt(num)>20000){
               pro.put("profit",res.get("clockme20k"));
               if (is_tax.equals("03")){
                   Double total= DoubleUtil.div( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockme20k")))),dollar,2);
                   pro.put("total",total+"");
                   pro.put("rate","$");
               }
               if (is_tax.equals("01")){
                   Double total= DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockme20k"))));
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
               if (is_tax.equals("02")){
                   Double total= DoubleUtil.mul( DoubleUtil.mul(Double.parseDouble(price),(1+Double.parseDouble(res.get("clockme20k")))),1+tax);
                   pro.put("total",total+"");
                   pro.put("rate","￥");
               }
           }

       }
          success(pro);
    }


}
