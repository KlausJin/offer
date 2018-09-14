package com.cloudling.offer.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientModel extends  Model {
    public ClientModel() {
        super("client");
    }

    public ArrayList list(String condition) {
        return where(condition).select();
    }

    public HashMap<String, String> listone(String condition) {
        return where(condition).find();
    }

    public ArrayList list() {
        return select();
    }
    public HashMap<String,String> getClientProfit(String client_id){
        HashMap<String,String> map=where("id="+client_id).find();
        return map;
    }


}
