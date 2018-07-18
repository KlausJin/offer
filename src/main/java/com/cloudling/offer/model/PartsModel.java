package com.cloudling.offer.model;

import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-12  14:47
 * @Version 1.0
 **/
public class PartsModel extends Model {
    public PartsModel() {

        super("");
    }

    public void add_parts(){



    }


    public ArrayList<HashMap<String, String>> list() {
        return select();
    }
}
