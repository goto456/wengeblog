package com.goto456.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.goto456.Parameters;
import com.goto456.utils.DBUtils;

public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 检测表是否已经创建
		DBUtils.detectTable();
		String webRootPath = arg0.getServletContext().getRealPath("/");
		Parameters.WEB_ROOT_PATH = webRootPath;
	}

}
