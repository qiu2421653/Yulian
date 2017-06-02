package com.naga.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ServletContextListener
 *
 */
public class ContextInitDestroyedListener implements ServletContextListener {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ContextInitDestroyedListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		// Context结束时需执行的内容 不建议销毁时做处理
		logger.info("ServletContext is destroyed!");
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		// 初始化完成时需执行的内容
		logger.info("ServletContext is Initialized!");

		// 权限 加载权限验证用信息
//		AuthorityManager.load();
	}
    
}