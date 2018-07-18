package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.PartBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PartsModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-18  12:15
 * @Version 1.0
 **/
public class PartsController extends Controller {
    public PartsController(ControllerContext context) {
        super(context);
    }

    @action
    public void add() {
        try {
            PartBean bean  = (PartBean) BeanUtil.getBean("part",context,BeanUtil.POST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
