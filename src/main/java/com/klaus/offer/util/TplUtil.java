package com.klaus.offer.util;

import com.klaus.offer.config.Entrance;
import com.klaus.offer.server.Controller;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.FileResourceLoader;


import java.io.IOException;

public class TplUtil {
	
	public static String rootDir;
	static Configuration cfg;
	static String prex = ".html";
	
	static{
		rootDir = System.getProperty("user.dir")+"/tpl/";
		try {
			cfg = Configuration.defaultConfiguration();
			cfg.setErrorHandlerClass(Entrance.PACKAGE_NAME+".error.WebErrorHandler");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String  loadTpl(String tpl, Controller controller){
		FileResourceLoader resourceLoader = new FileResourceLoader(rootDir,"utf-8");
			GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
			String tname = tpl+prex;
				try {
					Template t = gt.getTemplate(tname);
					controller.assigns.put("seo_title", controller.seo_title);
					t.binding(controller.assigns);
					String str = t.render();
					return str;
				} catch (BeetlException e) {
					return e.detailCode+":"+e.resourceId;
				}
				

	}
	
	
	private static String loadErrorTpl(String msg,String tpl,Controller controller) {
		FileResourceLoader resourceLoader = new FileResourceLoader(rootDir,"utf-8");
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		String tname = tpl+prex;
		Template t = gt.getTemplate(tname);
		controller.assigns.put("seo_title", controller.seo_title);
		t.binding(controller.assigns);
		String str = t.render();
		return str;
	}
	
	
	

}
