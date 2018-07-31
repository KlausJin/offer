package com.cloudling.offer.controller.v1;

import com.alibaba.fastjson.JSON;
import com.cloudling.offer.annotation.action;
import com.cloudling.offer.model.PartCatModel;
import com.cloudling.offer.model.SpareModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.TimeUtil;

import javax.print.attribute.HashAttributeSet;
import java.util.*;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  12:15
 * @Version 1.0
 **/
public class PartController extends Controller {
    public PartController(ControllerContext context) {
        super(context);
    }

    @action
    public void add_part() {
        toHtml("admin_tpl/add_part");
    }
}