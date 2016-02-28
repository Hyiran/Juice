package org.juice;

import java.util.HashSet;
import java.util.List;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;


public class CheckPoint extends Assertion{

	private int flag = 0;
	
	@Override
	public void onAssertSuccess(IAssert assertCommand) {
		Log.info("[���Գɹ�]�� "
				  +"Ԥ�ڽ��: "+assertCommand.getExpected()
				  +"ʵ�ʽ��: "+assertCommand.getActual());
	}

	@Override
	public void onAssertFailure(IAssert assertCommand) {
		String message = "";
		if(assertCommand.getMessage()!=null){
			message = assertCommand.getMessage();
		}
		Log.error("[����ʧ��]�� "
				  +"Ԥ�ڽ��: "+assertCommand.getExpected()
				  +"ʵ�ʽ��: "+assertCommand.getActual());

		Reporter.record(2, message);
		flag = flag+1;
	}
	
	public void equals(boolean actual,boolean expected,String message){
		try{
			assertEquals(actual, expected, message);
		}catch(Error e){}
	}
	
	public void equals(boolean actual,boolean expected){
		try{
			assertEquals(actual, expected);
		}catch(Error e){}
	}
	
	public void equals(String actual,String expected,String message){
		try{
			assertEquals(actual, expected, message);
		}catch(Error e){}
	}
	
	public void equals(String actual,String expected){
		try{
			assertEquals(actual, expected);
		}catch(Error e){}
	}
	
	public void equals(long actual,long expected,String message){
		try{
			assertEquals(actual, expected, message);
		}catch(Error e){}				
	}
	
	public void equals(long actual,long expected){
		try{
			assertEquals(actual, expected);
		}catch(Error e){}				
	}
	
	public void equals(int actual,int expected,String message){
		try{
			assertEquals(actual, expected, message);
		}catch(Error e){}				
	}
	
	public void equals(int actual,int expected){
		try{
			assertEquals(actual, expected);
		}catch(Error e){}				
	}
	
	public void equals(List<String> actual,List<String> expected ,String message){
		if(actual.size()!=0){
			try{
				assertEquals(actual, expected, message);
			}catch(Error e){}		
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);			
	}
	
	public void equals(List<String> actual,List<String> expected){
		if(actual.size()!=0){
			try{
				assertEquals(actual, expected);
			}catch(Error e){}		
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);				
	}
	
	public void equals(String[] actual,String[] expected,String message){
		if(actual.length!=0){
			try{
				assertEquals(actual, expected, message);
			}catch(Error e){}		
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);				
	}
	
	public void equals(String[] actual,String[] expected){
		if(actual.length!=0){
			try{
				assertEquals(actual, expected);
			}catch(Error e){}		
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);			
	}
	
	public void equals(String[] actuals,String expected,String message){
		if(actuals.length!=0){
			for(String actual:actuals){
				try{
					assertEquals(actual, expected, message);
				}catch(Error e){}	
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void equals(String[] actuals,String expected){
		if(actuals.length!=0){
			for(String actual:actuals){
				try{
					assertEquals(actual, expected);
				}catch(Error e){}	
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void equals(List<String> actuals, String expected ,String message){
		if(actuals.size()!=0){
			for(String actual:actuals){
				try{
					assertEquals(actual, expected, message);
				}catch(Error e){}	
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void equals(List<String> actuals, String expected){
		if(actuals.size()!=0){
			for(String actual:actuals){
				try{
					assertEquals(actual, expected);
				}catch(Error e){}	
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void notEquals(boolean actual,boolean expected,String message){
		try{
			assertNotEquals(actual, expected, message);
		}catch(Error e){}				
	}
	
	public void notEquals(boolean actual,boolean expected){
		try{
			assertNotEquals(actual, expected);
		}catch(Error e){}				
	}
	
	public void notEquals(String actual,String expected,String message){
		try{
			assertNotEquals(actual, expected, message);
		}catch(Error e){}				
	}

	public void notEquals(String actual,String expected){
		try{
			assertNotEquals(actual, expected);
		}catch(Error e){}				
	}
	
	public void notEquals(long actual,long expected){
		try{
			assertNotEquals(actual, expected);
		}catch(Error e){}				
	}
	
	public void notEquals(long actual,long expected,String message){
		try{
			assertNotEquals(actual, expected, message);
		}catch(Error e){}				
	}
	
	public void notEquals(int actual,int expected,String message){
		try{
			assertNotEquals(actual, expected, message);
		}catch(Error e){}			
	}	
	
	public void notEquals(int actual,int expected){
		try{
			assertNotEquals(actual, expected);
		}catch(Error e){}			
	}
	
	public void notEquals(String[]actual,String expected,String message){
		if(actual.length!=0){	
			for(int i=0;i<actual.length;i++){				
				notEquals(actual[i], expected, message);
			}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void notEquals(String[]actual,String expected){
		if(actual.length!=0){	
			for(int i=0;i<actual.length;i++){				
				notEquals(actual[i], expected);
			}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void notEquals(List<String>actual,String expected,String message){
		if(actual.size()!=0){
			for(int i=0;i<actual.size();i++){			
				notEquals(actual.get(i), expected, message);
			}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void notEquals(List<String>actual,String expected){
		if(actual.size()!=0){
			for(int i=0;i<actual.size();i++){			
				notEquals(actual.get(i), expected);
			}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void startWith(String actual,String expected,String message){		
		if (!actual.startsWith(expected)){			
			equals(true, false ,message+"[ʵ�ʽ��:"+actual+" Ԥ�ڽ��:"+expected+"]");
		}			
	}
	
	public void startWith(String[] actual,String expected,String message){	
		if(actual.length!=0){
		for(String act : actual )			
			if (!act.startsWith(expected)){				
				equals(true, false ,message+"[ʵ�ʽ��:"+act+" Ԥ�ڽ��:"+expected+"]");
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void startWith(List<String> actual,String expected,String message){	
		if(actual.size()!=0){
		for(String act : actual )			
			if (!act.startsWith(expected)){				
				equals(true, false ,message+"[ʵ�ʽ��:"+act+" Ԥ�ڽ��:"+expected+"]");
			}	
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void contains(String[] actual,String expected,String message){
		if(actual.length!=0){
			for(String act : actual )			
				if (!act.contains(expected)){			
	
				}
			}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void contains(String[] actual,String expected1,String expected2,String message ){
		if(actual.length!=0){
			for(String act : actual )			
				if (!act.contains(expected1) && !act.contains(expected2) ){			
					equals(true, false ,message+"[ʵ�ʽ��:"+act+" Ԥ�ڽ��:"+expected1+" "+expected2+"]");
				}
			}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void contains(List<String> actual,String expected,String message){
		if(actual.size()!=0){
			for(String act : actual )			
				if (!act.contains(expected)){			
					equals(true, false ,message+"[ʵ�ʽ��:"+act+" Ԥ�ڽ��:"+expected+"]");
				}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void contains(List<String> actual,String expected1,String expected2,String message ){
		if(actual.size()!=0){
			for(String act : actual )			
				if (!act.contains(expected1) && !act.contains(expected2) ){			
					equals(true, false ,message+"[ʵ�ʽ��:"+act+" Ԥ�ڽ��:"+expected1+" "+expected2+"]");
				}
		}else ErrorHandler.continueRunning("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!", false);
	}
	
	public void isRepeat(List<String> actual,String message){
		HashSet<String> set=new HashSet<String>();
		for(String s:actual){
			set.add(s);
		}		
		if(!(set.size()==actual.size())){
			equals(true, false ,message);
		}
	}
	
	/**
	* @Description ��case����Ҫ�õ�if�������������ʱʹ�øü���
	* @param message��Faildʱ��Ҫ��¼����־����Ϣ
	*/
	public void isFaild(String message){
		equals(true, false, message);
	}
	
	/**
	* @Description ��case����Ҫ�õ�if���������passʱǿ��ʹ�øü���
	* @param message��passʱ��Ҫ��¼��Ϣ
	*/
	public void isSuccess(String message){
		equals(true, true, message);
	}
	
	public void addInfor(String message){
		Log.info(message);
		Reporter.record(0, message);
	}
	
	/**
	* @Description ����flag���жϴ�ǰ���м������Ƿ��д���ֵ����ʧ�ܣ��˳���ǰ���е�.java����
	* 			         ʧ�ܺ�result�����·��Ĵ��뽫���ᱻִ��
	* @param message:case�ɹ�ʱ��Ҫ��¼����־����Ϣ
	*/
	public void result(String message){	
		org.testng.Assert.assertEquals(flag, 0);
		Log.info(message);
		Reporter.record(1, message);
	}
	
	public void result(int iFlag,String message){	
		org.testng.Assert.assertEquals(iFlag, 0);
		Log.info(message);
		Reporter.record(1, message);
	}

	/**
	* @Description �׶��Լ��㺯��.����flag���жϴ�ǰ���м������Ƿ��д���ֵ.�����ڣ�����ֹ��ǰ������ֻ���ñ�־λ.
	* @param message:ʧ��ʱ��Ҫ��¼����Ϣ
	*/
	public void phaseResult(String message){
		equals(flag, 0, message);
		initFlag();
	}
	
	public int getFlag(){
		return flag;		
	}
	
	public void initFlag(){
		flag = 0;		
	}	
}
