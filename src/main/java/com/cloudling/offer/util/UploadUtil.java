package com.cloudling.offer.util;

import com.cloudling.offer.server.ControllerContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class UploadUtil {
	
	public static String SPATH ="upload";

    public static String NEW ="templet";

	public static String PATH = "";

    public static String NEWPATH = "";

	static{
		try {
			PATH =  new File("").getCanonicalPath()+"/assets/"+SPATH;
            NEWPATH =  new File("").getCanonicalPath()+"/assets/"+NEW;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isMultipart(HttpServletRequest request){
		return ServletFileUpload.isMultipartContent(request);
	}



    public static List<String>  uploadOffer(ControllerContext controllerContext) throws IOException, ServletException, FileUploadException{


        //获取tomcat下的up目录的路径
        HttpServletRequest request = controllerContext.request;
        List<String> reStrings = new ArrayList<>();

        if(!isMultipart(request)) return reStrings;
        //1,声明DiskFileItemFactory工厂类，用于在指定磁盘上设置一个临时目录
        DiskFileItemFactory disk = new DiskFileItemFactory(1024*10,new File(NEWPATH));
        //2,声明ServletFileUpload，接收上边的临时文件。也可以默认值
        ServletFileUpload up = new ServletFileUpload(disk);
        //3,解析request

        List<FileItem> list = up.parseRequest(request);

        for(int i=0;i<list.size();i++){
            //如果就一个文件，
            FileItem file = list.get(i);
            //获取文件名：
            String fileName = file.getName();
            //获取文件的类型：
            InputStream in = file.getInputStream();
            //文件大小
            OutputStream out = new FileOutputStream(NEWPATH+"/"+fileName);
            //文件copy
            byte[] b = new byte[1024];
            int len = 0;
            while((len=in.read(b))!=-1){
                out.write(b, 0, len);
            }
            out.flush();
            out.close();

            //删除上传生成的临时文件
            file.delete();
            reStrings.add(fileName);
        }
        return reStrings;
    }

    public static List<String>  upload(ControllerContext controllerContext) throws IOException, ServletException, FileUploadException{
    	
    	
    	//获取tomcat下的up目录的路径   
    	HttpServletRequest request = controllerContext.request;
    	List<String> reStrings = new ArrayList<>();
    	
    	if(!isMultipart(request)) return reStrings;
        //1,声明DiskFileItemFactory工厂类，用于在指定磁盘上设置一个临时目录
        DiskFileItemFactory disk = new DiskFileItemFactory(1024*10,new File(PATH));
        //2,声明ServletFileUpload，接收上边的临时文件。也可以默认值
        ServletFileUpload up = new ServletFileUpload(disk);
        //3,解析request
      
            List<FileItem> list = up.parseRequest(request);
            
            for(int i=0;i<list.size();i++){
            	 //如果就一个文件，
                FileItem file = list.get(i);
                //获取文件名：
                String fileName = file.getName();
                //获取文件的类型：
                String fileType = file.getContentType();
                //获取文件的字节码：
                InputStream in = file.getInputStream();
                //文件大小
                int size = file.getInputStream().available();
                
                String extesion = fileName.split("\\.")[1];
                String nfile = TimeUtil.getLongTimeStamp()+"."+extesion;
                //声明输出字节流
                OutputStream out = new FileOutputStream(PATH+"/"+nfile);
                //文件copy
                byte[] b = new byte[1024];
                int len = 0;
                while((len=in.read(b))!=-1){
                    out.write(b, 0, len);
                }
                out.flush(); 
                out.close();
                
                //删除上传生成的临时文件
                file.delete();
                reStrings.add("/"+SPATH+"/"+nfile);
            }
           
            return reStrings;
    }
    
 
    

	
}
