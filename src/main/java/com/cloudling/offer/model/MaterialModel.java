package com.cloudling.offer.model;

import java.util.HashMap;

public class MaterialModel extends Model {
    public MaterialModel() {
        super("material");
    }

    public HashMap<String, String> getMaterial(String name){
       return where("name="+name).find();
    }
}
