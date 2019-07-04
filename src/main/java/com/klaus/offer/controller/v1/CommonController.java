package com.klaus.offer.controller.v1;

import com.klaus.offer.annotation.action;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.UploadUtil;
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
    @action
    public void upload() {
        try {
            success(UploadUtil.upload(context));
        } catch (IOException | ServletException | FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error("上传失败");
        }
    }

}
