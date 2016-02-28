package org.juice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.juice.testbase.TestBase;
import org.juice.util.DateHandler;
import org.juice.util.TxtParser;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;


public abstract class Reporter implements IReporter{

	private static Map<String,String> customReport = new LinkedHashMap<String,String>();
	protected static String PASSED_NUMBER = "passedNumber";
	protected static String FAILED_NUMBER = "failedNumber";
	protected static String SKIPPED_NUMBER = "skippedNumber";
	protected static String TOTAL_NUMBER = "totalNumber";
	protected static String PASS_RATE = "passRate";
	protected static String CUSTOM_LOG = "customLog";
	protected static String RUN_DETAILS = "runDetails";
	protected static String PROJECT_NAME = "projectName";
	protected static String TEST_END_TIME = "testEndTime";
	
	/**
	 * ��¼�Զ��屨����Ϣ
	 * @param message
	 */
	public static synchronized void record(int level, String testName, 
			String caseName, String message){
		customReport.put(testName+caseName, 
						"��"+getStatus(level)+"�� "
						+"["+caseName+"]"
						+message);
	}
	
	public static synchronized void record(int level, String message){
		Map<String, String> runningInfo = TestBase.getRunningInfo(Thread.currentThread().getId());
		customReport.put(runningInfo.get("testName")+runningInfo.get("caseName"), 
						"��"+getStatus(level)+"�� "
						+"["+runningInfo.get("caseName")+"]"
						+message);
	}
	
	/**
	 * �����Զ��屨������
	 * @return Map<String,String> key��test����+case��  , value���Զ�����Ϣ
	 */
	public static synchronized Map<String,String> getCustomReport(){
		return customReport;		
	}
	
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> iSuite,
			String output) {
		doReport(getTestData(xmlSuites,iSuite));
	}
			
	//��ȡ������ʽ
	protected String getReportLayout(String layoutPath){
		TxtParser tp = new TxtParser(layoutPath);
		return tp.readByLinesToString();		
	}
	
	//��ȡ��������
	protected Map<String, Map<String, String>> getTestData(List<XmlSuite> xmlSuites, List<ISuite> suites){
   	 	int g_passedNumber = 0;		//ȫ�ֳɹ�������
		int g_failedNumber = 0;		//ȫ��ʧ��������
		int g_skippedNumber = 0;		//ȫ������������
		int g_totalNumber = 0;		//ȫ����������
		float g_passRate = 0;			//ȫ��ͨ����
		String testEndTime = DateHandler.formatDate(new Date());		//���Խ���ʱ��
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Map<String, String>> testData = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> global = new LinkedHashMap<String, String>();		
		global.put("projectName", xmlSuites.get(0).getName());	//Ĭ��ֻ֧��һ��suite
		global.put("testEndTime", testEndTime);		 	
		for (ISuite suite : suites){
			 Map<String, ISuiteResult> suiteResults = suite.getResults();	
	         for (String key : suiteResults.keySet()) {
				 Map<String, String> eachTest = new LinkedHashMap<String, String>();
				 List<String> runDetails = new ArrayList<String>();	
				 StringBuffer testDetails = new StringBuffer();
	        	 StringBuffer reportLog = new StringBuffer();	        	 
	        	 int passedNumber = 0;		//ÿ�����Ե�Ԫ�ĳɹ�������
	    		 int failedNumber = 0;		//ÿ�����Ե�Ԫ��ʧ��������
	    		 int skippedNumber = 0;		//ÿ�����Ե�Ԫ������������
	    		 int totalNumber = 0;		//ÿ�����Ե�Ԫ����������
	    		 float passRate = 0;			//ÿ�����Ե�Ԫ��ͨ����
	             ITestContext testContext = suiteResults.get(key).getTestContext();           
	             IResultMap passedTests = testContext.getPassedTests();
	             IResultMap failedTests = testContext.getFailedTests();
	             IResultMap skippedTests = testContext.getSkippedTests();
	             passedNumber = passedNumber + passedTests.size();
	             failedNumber = failedNumber + failedTests.size();
	             skippedNumber = skippedNumber + skippedTests.size();  
	             g_passedNumber = g_passedNumber + passedNumber;
	     		 g_failedNumber = g_failedNumber + failedNumber;
	     		 g_skippedNumber = g_skippedNumber + skippedNumber;
	             List<IResultMap> iResultMaps = new ArrayList<IResultMap>();
	             iResultMaps.add(passedTests);
	             iResultMaps.add(failedTests);
	             iResultMaps.add(skippedTests);
	             runDetails.addAll(getRunDetails(key,iResultMaps));
	              
	             for(String testName:customReport.keySet()){
	            	 if (testName.contains(key)){	
	            		 String log = customReport.get(testName);
	                	 reportLog.append("<J>"+log+"</J>");
	            	}
	             }
	            
		        for(String detail:runDetails){
		        	testDetails.append("<J>"+detail+"</J>");
		   	    }	
		            
	      		totalNumber = passedNumber + failedNumber + skippedNumber;
	      		g_totalNumber = g_totalNumber + totalNumber;
	      		eachTest.put(PASSED_NUMBER, String.valueOf(passedNumber));
	      		eachTest.put(FAILED_NUMBER, String.valueOf(failedNumber));
	      		eachTest.put(SKIPPED_NUMBER, String.valueOf(skippedNumber));	 
	      		eachTest.put(TOTAL_NUMBER, String.valueOf(totalNumber));
	    		passRate = 100*((float)passedNumber/(totalNumber));
	    		if(Double.isNaN(passRate)){
	    			passRate = 0;
	    		}
	    		eachTest.put(PASS_RATE, df.format(passRate));  
	    		eachTest.put(CUSTOM_LOG, reportLog.toString());
	    		eachTest.put(RUN_DETAILS, testDetails.toString());
	    		testData.put(key, eachTest);	    			             
	         } 
	         
		}
		global.put(PASSED_NUMBER, String.valueOf(g_passedNumber));
		global.put(FAILED_NUMBER, String.valueOf(g_failedNumber));
		global.put(SKIPPED_NUMBER, String.valueOf(g_skippedNumber));
		global.put(TOTAL_NUMBER, String.valueOf(g_totalNumber));
		g_passRate = 100*((float)g_passedNumber/(g_totalNumber));
		if(Double.isNaN(g_passRate)){
			g_passRate = 0;
		}
		global.put(PASS_RATE, df.format(g_passRate));
		testData.put("global", global);
		return testData;		
	}
	
	abstract protected void doReport(Map<String, Map<String, String>> testResultSet);
	
	private List<String> getRunDetails(String testName, List<IResultMap> iResultMap){
		List<ITestResult> results = new ArrayList<ITestResult>();
		List<String> runDetails = new ArrayList<String>();
		for(IResultMap map:iResultMap){
			results.addAll(map.getAllResults());
		} 
		this.sort(results);
	  	for(ITestResult result:results){
			runDetails.add(testName+","
						   +result.getName()+","
						   +getStatus(result.getStatus())+","
						   +(result.getEndMillis()-result.getStartMillis())+"ms");
	  	}
	  	return runDetails;
	}
	
	private static String getStatus(int status){
		String statusString = null;
		switch (status) { 
		case 0:
	    	statusString = "INFO";
	        break;
	    case 1:
	    	statusString = "PASS";
	        break;
	    case 2:
	        statusString = "FAILED";
	        break;
	    case 3:
	        statusString = "SKIP";
	        break;
	    default:           
	        break;
	    }
	    return statusString;
	 }
	     	 
	 private void sort(List<ITestResult> list){
		 Collections.sort(list, new Comparator<ITestResult>() {
		     @Override
		     public int compare(ITestResult r1, ITestResult r2) {
		    	 if(r1.getStartMillis()>r2.getStartMillis()){
		    		 return 1;
		         }else{
		             return -1;
		         }              
		     }
		     });
	 }
	 
}
