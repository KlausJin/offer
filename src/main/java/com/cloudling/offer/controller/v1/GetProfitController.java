package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.OfferModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

import java.util.ArrayList;
import java.util.HashMap;

public class GetProfitController extends Controller {
    public GetProfitController(ControllerContext context) {
        super(context);
    }

    @action
    public void getProfit(){

        String offer_id = I("offer_id") == null ? "" : I("offer_id").toString();
        String num = I("num") == null ? "" : I("num").toString();
        HashMap<String, String> map = M("offer").where("id=" + offer_id).find();
        HashMap<String, String> res = M("client").where("id=" + map.get("client_id")).find();
        ArrayList<HashMap<String, String>> list = M("offer_product").where("offer_id=" + offer_id).select();
        HashMap<String, String> data = M("product").where("id=" + list.get(0).get("product_id")).find();
        HashMap<String,String> pro=new HashMap<>();
       if (data.get("cat_id").equals("22")){
           if (Integer.parseInt(num)<=5000){
               pro.put("profit",res.get("watchle5k"));
           }
           if ( Integer.parseInt(num)>5000 && Integer.parseInt(num)<=10000 ){
               pro.put("profit",res.get("watch5k_10k"));
           }
           if ( Integer.parseInt(num)>10000 && Integer.parseInt(num)<=50000 ){
               pro.put("profit",res.get("watch10k_50k"));
           }
           if ( Integer.parseInt(num)>50000 && Integer.parseInt(num)<=100000 ){
               pro.put("profit",res.get("watch50k_100k"));
           }

       }
       else{
           if (Integer.parseInt(num)<=500){
               pro.put("profit",res.get("clockle500"));
           }
           if ( Integer.parseInt(num)>500 && Integer.parseInt(num)<=1000 ){
               pro.put("profit",res.get("clock500_1k"));
           }
           if ( Integer.parseInt(num)>1000 && Integer.parseInt(num)<=3000 ){
               pro.put("profit",res.get("clock1k_3k"));
           }
           if ( Integer.parseInt(num)>3000 && Integer.parseInt(num)<=5000 ){
               pro.put("profit",res.get("clock3k_5k"));
           }
           if ( Integer.parseInt(num)>5000 && Integer.parseInt(num)<=10000 ){
               pro.put("profit",res.get("clock5k_10k"));
           }
           if ( Integer.parseInt(num)>10000 && Integer.parseInt(num)<=20000 ){
               pro.put("profit",res.get("clock10k_20k"));
           }
           if ( Integer.parseInt(num)>20000){
               pro.put("profit",res.get("clockme20k"));
           }

       }
          success(pro);
    }


}
