package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.UploadUtil;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @Description Excel上传文件
 * @Author 小云网络jxl
 * @Date 2018-07-19  17:39
 * @Version 1.0
 **/
public class CommonController extends AdminController {
    public CommonController(ControllerContext context) {
        super(context);
    }

    @action
    public void upload_excel(){
        try {
            List<String> res = UploadUtil.upload(context);
            success(res);
        } catch (Exception e) {
            e.printStackTrace();
            error(e.getMessage());
        }
    }

}
