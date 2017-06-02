package com.njkj.yulian.controller;

/**
 * 异常处理类
 * 
 * @author Qiu
 * 
 */
public class LogController {
	private static LogController logController;
	// 创建
	public static LogController getLogController() {
		if (logController == null) {
			synchronized (LogController.class) {
				if (logController == null) {
					logController = new LogController();
				}
			}
		}
		return logController;
	}

	/**
	 * 异常处理
	 */
	public void doLog(String message) {
		System.out.println("执行了异常处理:" + message);

		// 根据各种异常进行处理
		// java.lang.nullpointerException
		// java.lang.classnotfoundException
		// java.lang.arrayindexoutofboundsException
		// java.lang.arithmeticException
		// java.lang.illegalargumentException
		// java.lang.illegalaccessException
		// ...
	}
}
