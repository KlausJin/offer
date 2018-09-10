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


}
