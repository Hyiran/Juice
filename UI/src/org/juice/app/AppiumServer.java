package org.juice.app;

import java.lang.reflect.Field;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.mina.util.AvailablePortFinder;
import org.juice.ErrorHandler;
import org.juice.Parameter;
import org.jvnet.winp.WinProcess;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class AppiumServer implements AppiumServerType{
	
	private static final String serverPath = "/wd/hub";
	private static final String statusPath = "/wd/hub/status";
	private static final HttpClient httpClient = HttpClients.createDefault();
	private Process process;
	private int maxTryLinks = 3;		//��������Ӵ���
	private long startTimeout = 30;		//������������ȴ�ʱ��
	private String ip = "localhost";
	private String udid = "-1";			//ָ���豸����
	private int appiumPort = -1;
	private int bootstrapPort = -1;
	private int selendroidPort = -1;
	private int chromeDriverPort = -1;
	
	/**
	 * δ�Զ��������ʵ����Ĭ���������Appium����
	 */
	public AppiumServer(){
		this(new Parameter());
	}
		
	public AppiumServer(Parameter parameters){
		init(parameters);	
	}
	
	/**
	 * ����Appium����
	 */
	public void startService(){
		int tryNumber = 0;
		try {
			process = Runtime.getRuntime().exec(commandBuilder());
			} catch (Exception e) {
				ErrorHandler.stopRunning(e, "����AppiumServerʧ��:"+commandBuilder(), true);
		    }

		long start = System.currentTimeMillis();    //��ȡ�ȴ��������ʼʱ��     
		boolean state = isRunning();		//�жϷ����Ƿ���������		
		     
		while (!state) {
			long end = System.currentTimeMillis();       //��ȡ��ǰʱ��	      
		    if (end - start > startTimeout*1000&&tryNumber<maxTryLinks) {
		    	tryNumber = tryNumber+1;
		    	restartService();            	    	
		    }else if(tryNumber==maxTryLinks){
		    	ErrorHandler.stopRunning("����AppiumServerʧ��:",true);
		    }
		    state = isRunning();		 //��ѭ����β�ٻ�ȡ����״̬
		}
	}
	
	/**
	 * ֹͣ����������Appium����
	 */
	public void stopService() {
		try {
			WinProcess winp = new WinProcess(process);
			ErrorHandler.stopRunning("����Appium Server����:"+winp.getPid(),false);
			winp.killRecursively();
		} catch (Exception e) {
			ErrorHandler.stopRunning(e,"����Appium Server�����쳣!",false);
		}
	}
 	
	/**
	 * �ر�Appium���������д���
	 */
	public void closeLaunchedCmdWindow() {
		try {
			WinProcess winp = new WinProcess(this.getPid());
			ErrorHandler.stopRunning("�ر�Appium Server�����д���"+winp.getPid(),false);
			winp.killRecursively();
		} catch (Exception e) {
			ErrorHandler.stopRunning(e,"�ر�Appium Server�����д����쳣!",false);
		}
	}
	
	/**
	 * ����Appium����
	 */
	public void restartService(){
		this.stopService();
		this.startService();
	}
 
	/**
	 * ͨ������http���󣬼��ز�json�е�status�ֶ��ж�Appium�����Ƿ���������
	 */	
   public boolean isRunning() {
       try {
           URI uri = new URIBuilder().setScheme("http").setHost(ip)
                 	  .setPort(appiumPort).setPath(statusPath).build();
           HttpGet httpget;
           HttpResponse response;
           httpget = new HttpGet(uri);
           response = httpClient.execute(httpget);
           HttpEntity entity = response.getEntity();
           String rs = EntityUtils.toString(entity);
           JsonElement json = new JsonParser().parse(rs);
           int status = json.getAsJsonObject().get("status").getAsInt();
           return status == 0;

       } catch (Exception e) {
    	   ErrorHandler.continueRunning(e, "�ж�Appium Server����״̬�쳣!", false);
           return false;
       }
   }
 
   /**
	 * ��ȡAppium����URL�ַ���
	 */
   public String getURL(){
       try {
    	   URI uri = new URIBuilder().setScheme("http").setHost(ip)
                     .setPort(appiumPort).setPath(serverPath).build();
            
           return uri.toURL().toString();
            
       } catch (Exception e) {
    	   ErrorHandler.continueRunning(e, "��ȡAppium Server����URL�쳣!", false);
    	   return "-1";
       }  
   }
 
   /**
	 * ��ȡAppium�������PID
	 */
   public int getPid() {
       try {
           WinProcess winp = new WinProcess(process);
           return winp.getPid();
       } catch (Exception e) {
    	   ErrorHandler.continueRunning(e, "��ȡAppium Server�������PID�쳣!", false);
           return -1;
       }
   }
 
   /**
  	 * ��ȡAppium����IP
  	 */
   public String getIP() {
       return ip;
   }
   
   /**
 	 * ��ȡAppium�����豸����
 	 */
   public String getUdid() {
       return udid;
   }
   
   /**
	 * ��ȡAppium����˿ں�
	 */
   public int getAppiumPort() {
       return appiumPort;
   }
 
   /**
	 * ��ȡAppium����Bootstrap�˿ں�
	 */
   public int getBootstrapPort() {
       return bootstrapPort;
   }
 
   /**
	 * ��ȡAppium����Selendroid�˿ں�
	 */
   public int getSelendroidPort() {
	   return selendroidPort;
   }
 
   /**
	 * ��ȡAppium����ChromeDriver�˿ں�
	 */
   public int getChromeDriverPort() {
	   return chromeDriverPort;
   }
 
   /**
  	 * ��ȡAppium������ϸ������Ϣ(String)
  	 */
   @Override
   public String toString() {
       return "AppiumServer [pid=" + getPid() + ", ip=" + ip + serverPath+", appiumPort="
               + appiumPort + ", bootstrapPort=" + bootstrapPort
               + ", selendroidPort=" + selendroidPort + ", chromeDriverPort="
               + chromeDriverPort + ", udid="+udid+"]";
   }
 
   //��ʼ��Server����
   private void init(Parameter parameters){	   
	   appiumPort = AvailablePortFinder.getNextAvailable();
	   bootstrapPort = AvailablePortFinder.getNextAvailable();
	   selendroidPort = AvailablePortFinder.getNextAvailable();
	   chromeDriverPort = AvailablePortFinder.getNextAvailable();
	      
	   for(String name :parameters.asMap().keySet()){
		   Object value = parameters.get(name);
		   if(value!=null){
			   try {
				   Field field = this.getClass().getDeclaredField(name);
				   field.set(this, value);				
			} catch (NoSuchFieldException e) {
				ErrorHandler.stopRunning(e, "Appium Server���������²�������"+name, true);
			} catch (SecurityException e) {
				ErrorHandler.continueRunning(e, "��ʼ��AppiumServer �����쳣!", false);
			} catch (IllegalArgumentException e) {
				ErrorHandler.stopRunning(e, "AppiumServer ���²���������ֵ���Ͳ���ȷ��"+name, true);
			} catch (IllegalAccessException e) {
				ErrorHandler.continueRunning(e, "��ʼ��AppiumServer �����쳣!", false);
			}
		   
		   }
	   }
   }
  
   //��������Appium�����CMD����
   private String commandBuilder(){
	  StringBuffer cmds = new StringBuffer();
	  cmds.append("cmd /c start appium.cmd");
	  cmds.append(String.format(" --port=%d", appiumPort));
	  cmds.append(String.format(" --chromedriver-port=%d", chromeDriverPort));
	  cmds.append(String.format(" --selendroid-port=%d", selendroidPort));
	  cmds.append(String.format(" --bootstrap-port=%d", bootstrapPort));
	  cmds.append(" --session-override");
	  if(!udid.equals("-1")){
		  cmds.append(String.format(" -U=%s", udid));
	  }  
	  return cmds.toString();	 
   }

}
