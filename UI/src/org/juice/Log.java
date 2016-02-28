package org.juice;


import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.juice.testbase.TestBase;


public class Log {

	static boolean isLog = true;
	static final Logger logger = LogManager.getLogger(Log.class.getName());
	static long id = 0;
	
	static{
		DOMConfigurator.configure("./src/Log4j.xml");
	}
	
	private synchronized static String buildRunningInfo(){
		Map<String, String> runningInfo = TestBase.getRunningInfo(Thread.currentThread().getId());
		return   "["+
					 runningInfo.get("testName") +" - " + 
					 runningInfo.get("testName") +
				 "] ";	 
	}
	
	/**
	 * Debug����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */
	public synchronized static void debug(String msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. DEBUG, buildRunningInfo()+msg, null);
		}
	}
	
	public synchronized static void debug(Object msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. DEBUG, buildRunningInfo()+msg, null);
		}
	}
	
	/**
	 * Info����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */
	public synchronized static void info(String msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. INFO, buildRunningInfo()+msg, null);
		}
	}
	
	public synchronized static void info(Object msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. INFO, buildRunningInfo()+msg, null);
		}
	}
	
	/**
	 * Warn�����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */
	public synchronized static void warn(String msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, null);
		}
	}

	public synchronized static void warn(Object msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, null);
		}
	}
	
	/**
	 * Error�����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */
	public synchronized static void error(String msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, null);
		}
	}
	
	public synchronized static void error(String msg, Throwable t){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, t);
		}
	}
	
	public synchronized static void error(Object msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, null);
		}
	}
	
	public synchronized static void error(Object msg, Throwable t){
		if(isLog){
			logger.log(Log.class.getName(), Level. WARN, buildRunningInfo()+msg, t);
		}
	}
	
	/**
	 * Fatal�����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */
	public synchronized static void fatal(String msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. FATAL, buildRunningInfo()+msg, null);
		}
	}
	
	public synchronized static void fatal(Object msg){
		if(isLog){
			logger.log(Log.class.getName(), Level. FATAL, buildRunningInfo()+msg, null);
		}
	}
	
	
}
