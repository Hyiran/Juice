package org.juice.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer{

	private int retryCount = 0;		//��ǰ�ǵڼ�������
	private int maxRetryTime = 3;	//���õ�������ܴ���
	
	public int getRetryCount(){
		return retryCount;	
	}
	
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public int getMaxRetryTime() {
		return maxRetryTime;
	}
	
	public void setMaxRetryTime(int maxRetryTime) {
		this.maxRetryTime = maxRetryTime;
	}
	
	@Override
	public boolean retry(ITestResult result) {
		if(this.retryCount<this.maxRetryTime){
			result.setAttribute("RETRY", retryCount);
			retryCount++;
			return true;
		}
		return false;
	}
	
}
