package org.juice.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.juice.ErrorHandler;
import org.juice.Locator;
import org.juice.util.BaseDataHandler;
import org.juice.util.XmlParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


public class WebLocator implements Locator{

	private WebDriver driver;
	private String filePath;
	private XmlParser xp;
	private boolean existFlag;
	private int objectWaitTime;
	
	public WebLocator(WebDriver driver, String filePath, int objectWaitTime){
		this.driver = driver;
		this.filePath = filePath;
		this.objectWaitTime = objectWaitTime;
		xp = new XmlParser(filePath);
		existFlag = true;
	}
	
	/**
	* @Description ���ݶ���xml��������Ӧ�ĵ���ҳ��Ԫ�أ�����WebElement��Ĭ����ȴ�ʱ����ʵ����Locatorʱ������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocator
	* @return WebElement 
	*/
	@Override
	public WebElement element(String pageKey, String objKey) {
		return getElement(pageKey, objKey, true, true);
	}

	/**
	 * ��Ԫ�ض�λʱ���ڣ��ȴ�ҳ��ĳ��Ԫ���Ƿ�������
	 * @param pageKey ����xml�е�ҳ������
	 * @param objKey  ����xml�еĶ�������
	 * @return boolean
	 */
	public boolean waitElementToBeNonDisplayed(String pageKey, String objKey) {
		final WebElement element = getElement(pageKey, objKey, false, true);
	    boolean wait = false;
	    if (element == null)
	        return wait;
	    try {
	        wait = new WebDriverWait(driver, objectWaitTime)
	                .until(new ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver d) {
	                        return !element.isDisplayed();
	                    }
	                });
	    } catch (Exception e) {
	    	String message = "�ȴ�"+objectWaitTime+"���Ԫ�أ�"+pageKey+"-"+objKey+" ��Ȼ��ʾ!";
	    	ErrorHandler.continueRunning(e, message, true);
	    }
	    return wait;
	}
	
	/**
	* @Description ���ݶ���xml��������Ӧ�ĵ���ҳ��Ԫ�أ�����WebElement���޵ȴ�ʱ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocator
	* @return WebElement 
	*/
	@Override
	public WebElement elementNoWait(String pageKey, String objKey) {
		return getElement(pageKey, objKey, false, true);
	}

	/**
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ�����WebElement�б�
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocators
	* @return List<WebElement>
	*/
	@Override
	public List<WebElement> elements(String pageKey, String objKey) {
		return getElements(pageKey, objKey);
	}

	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б��е�һ������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theFirstElement(String pageKey,String objKey){
		List<WebElement> elements = getElements(pageKey,objKey);	
		return elements.get(0);
	}
	
	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б������һ������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theLastElement(String pageKey,String objKey){
		List<WebElement> elements = getElements(pageKey,objKey);	
		return elements.get(elements.size()-1);
	}
	
	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б��е�һ���������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theRandomElement(String pageKey,String objKey){
		List<WebElement> elements = getElements(pageKey,objKey);	
		int index = (int)(Math.random()*elements.size());
		return elements.get(index);
	}
	
	/**
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�صĹ̶�һ������ֵ�������ַ�������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @param attribute Ҫ�õ���������
	* @return List<String>
	*/
	@Override
	public List<String> elementsAttribute(String pageKey, String objKey,
			String attribute) {
		List<String> list= new ArrayList<String>();
		List<WebElement> elements = elements(pageKey, objKey);
		if(attribute.equalsIgnoreCase("text")){
			for(WebElement e:elements){		
				list.add(e.getText());				
			}
		}else{
			for(WebElement e:elements){		
				list.add(e.getAttribute(attribute));				
			}
		}		
		return list;
	}
	
	/**
	* @Description ���ݶ���xml���ж�ĳ��ҳ��Ԫ���Ƿ���ڣ����ز������ʽ
	* @param isWait  �����ͱ������Ƿ���Ҫ�ȴ�
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocator
	* @return boolean
	*/
	@Override
	public boolean elementExist(boolean isWait, String pageKey, String objKey) {
		getElement(pageKey, objKey, isWait, false);
		return existFlag;
	}

	@SuppressWarnings("unused")
	public boolean elementExist(String xpathString){		
        try {
           WebElement element = driver.findElement(By.xpath(xpathString));
           existFlag = true;	 
        } catch (Exception e) {
           existFlag = false;
        }
        return existFlag;  
	}
	
	/**
	* @Description ʵ����webdriver��ԭʼActions��
	*/
	@Override
	public Actions action() {
		return new Actions(driver);
	}
	
	/**
	* @Description ʵ����webdriver��ԭʼSelect��
	*/
	@Override
	public Select select(String pageKey, String objKey) {
		return new Select(this.getElement(pageKey, objKey, true,true));
	}
	
	/**
	* @Description  ���������б�ͨ�������б��е�ѡ���value����ѡ��ĳ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @param value
	* @return void
	*/
	public void selectByValue(String pageKey,String objKey,String value){
		Select select = select(pageKey, objKey);
		select.selectByValue(value);	
	}
	
	public void selectByValue(WebElement element,String value){
		Select select = new Select(element);
		select.selectByValue(value);	
	}
	
	/**
	* @Description  ���������б�ͨ�������б���ѡ�������ѡ��ĳ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @param index ����ֵ
	* @return void
	*/
	public void selectByIndex(String pageKey,String objKey,int index){
		Select select = select(pageKey, objKey);
		select.selectByIndex(index);		
	}
	
	public void selectByIndex(WebElement element,int index){
		Select select = new Select(element);
		select.selectByIndex(index);		
	}
	
	/**
	* @Description  ���������б�ͨ�������б���ѡ��Ŀɼ��ı�ѡ��ĳ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @param text �ı�ֵ
	* @return void
	*/
	public void selectByVisibleText(String pageKey,String objKey,String text){
		Select select = select(pageKey, objKey);
		select.selectByVisibleText(text);	
	}
	
	public void selectByVisibleText(WebElement element,String text){
		Select select = new Select(element);
		select.selectByVisibleText(text);	
	}
	
	/**
	* @Description  ���������б����������б�����ѡ���click����ѡ��ѡ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return void
	*/
	public void selectEveryOne(String pageKey,String objKey){
		Select select = select(pageKey, objKey);
		for(WebElement e : select.getOptions()){
			e.click();
			wait(1);
		}
	}
	
	@Override
	public void addJS(String jsCodes) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript(jsCodes);	
	}
	
	/**
	* @Description  ͨ������JavaScript�ű���ȥ��ָ��Ԫ�ص�read only���ԡ�
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return void
	*/
	public void removeReadOnly(String pageKey,String objKey){
    	if (xp.isExist("/����/"+pageKey+"/"+objKey)) {	 
		String type = xp.getElementText("/����/"+pageKey+"/"+objKey+"/type");
		String value = xp.getElementText("/����/"+pageKey+"/"+objKey+"/value");	  
		JavascriptExecutor removeAttribute = (JavascriptExecutor)driver;
		
		//document�е�getElementBy������ֵ����ĸ���Ǵ�д������ת��һ��
		String js ="var js=document.getElementBy"+BaseDataHandler.capitalizesTheFirstLetter(type)
				+"(\""+value+"\");js.removeAttribute('readonly');";
		removeAttribute.executeScript(js);
    	}else{
    		String message = pageKey+"-"+objKey+"δ�ڶ�����ļ��д��ڣ�"+filePath;
			ErrorHandler.stopRunning(message, true);
    	}
	}
	
	public void removeReadOnly(String pageKey,String objKey,String index){
    	if (xp.isExist("/����/"+pageKey+"/"+objKey)) {	 
		String type = xp.getElementText("/����/"+pageKey+"/"+objKey+"/type");
		String value = xp.getElementText("/����/"+pageKey+"/"+objKey+"/value");	  
		JavascriptExecutor removeAttribute = (JavascriptExecutor)driver;
		
		//document�е�getElementBy������ֵ����ĸ���Ǵ�д������ת��һ��
		String js ="var js=document.getElementBy"+BaseDataHandler.capitalizesTheFirstLetter(type)
				+"(\""+value+index+"\");js.removeAttribute('readonly');";
		removeAttribute.executeScript(js);
    	}else{
    		String message = pageKey+"-"+objKey+"δ�ڶ�����ļ��д��ڣ�"+filePath;
			ErrorHandler.stopRunning(message, true);
    	}
	}
	
	/**
	 * @Description �����ҳ��Ԫ�ص�display='block'����ȥ��,�Ӷ�ǿ����ʾ��Ԫ��
	 * @param typeName Ԫ�ض�λ���ͣ�֧��classname,id,tagname
	 * @param typeValue
	 */
	public void elementsToBeDisplayed(String typeName,String typeValue){
		StringBuffer sb = new StringBuffer();
		typeName = typeName.toLowerCase();	

		switch(typeName){
		 case "classname": 
			sb.append("var all = document.getElementsByClassName('"+typeValue+"'); ");
			break;
		 case "id": 
			sb.append("var all = document.getElementsById('"+typeValue+"'); ");
			break;
		 case "tagname": 
			sb.append("var all = document.getElementsByTagName('"+typeValue+"'); ");
			break;
		 default: 
			ErrorHandler.stopRunning("document�����getElements������֧�ִ˲������ͣ�"+typeName, true);
		}
		sb.append("for(var i=0;i<all.length;i++){all[i].style.display='block';}");
		addJS(sb.toString());
	}
	
	/**
	* @Description   ��ת��ָ�����ӣ���ҳ�泬ʱδ����(��׽��TimeoutException)���׳��쳣����¼��־�Ͳ��Ա���
	* @param Url ���ӵ�ַ�ַ���
	* @return boolean ҳ���ѳɹ���ת����true
	*/
	@Override
	public boolean linkToPage(String Url) {
		try {
			driver.get(Url);
		} catch (TimeoutException e) {
			String message = "ҳ�����ʧ��:"+Url;
			ErrorHandler.continueRunning(e, message, true);
			return false;
		}
		return true;
	}

	/**
	* @Description ����ָ��url����ת���ȡҳ��ָ��Ԫ�ص�����ֵ�������ַ�������
	* @param url ��Ҫ������url����
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @param attribute  Ҫ��ȡ��Ԫ����������
	* @return List<String>
	*/
	public  List<String>  linkedAndGet(List<String> url, String pageKey, 
			String objKey, String attribute){	
		List<String> texts = new ArrayList<String>();
		int size = url.size();
			for(int i=0;i<size;i++){
				boolean isLinked = linkToPage(url.get(i));
				if(isLinked){
					if(attribute.equalsIgnoreCase("text")){
						texts.add(element(pageKey,objKey).getText().trim());
					}else texts.add(element(pageKey,objKey).getAttribute(attribute));
				}else texts.add("-1");			
			}	
		return texts;		
	}

	/**
	* @Description ����ָ��url����ת����ҳ��ĳ��ָ��Ԫ���Ƿ���ڡ����ز������ʽ
	* @param url ��Ҫ������url����
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return boolean
	*/
	public boolean linkedAndCheck(List<String> Url, String pageKey,
			String objKey) {
		boolean existFlag = false;
		for(String url:Url){
			boolean isLinked = linkToPage(url);
			if(isLinked){
				waitForPageLoad();
				if(!elementExist(false, pageKey, objKey)){
					ErrorHandler.continueRunning("ҳ��:"+url+" Ԫ��  "+pageKey+"-"+objKey+" ������!", true);
					existFlag = false;
				}else existFlag = true;
			}
		}
		return existFlag;
	}
	
	/**
	* @Description �ȴ�ҳ�������ɡ���ȴ�ʱ��ʵ����Locatorʱ������
	* @return void 
	*/
	@Override
	public void waitForPageLoad() {		
		try {			
			WebDriverWait wait = new WebDriverWait(driver, objectWaitTime);
			wait.until(isPageLoaded());	        
	    }catch(Exception e){	 
	    	String message = "ҳ�棺"+driver.getCurrentUrl()+"δ����"+objectWaitTime+"����ȫ���������!";
	    	ErrorHandler.continueRunning(e, message, true);
	    }	
	}

	/**
	* @Description �̶�ʱ��ȴ�
	* @param time�̶��ȴ���ʱ������λΪ����
	* @return void 
	*/
	@Override
	public void wait(int seconds) {
		try {
			int millis = seconds*1000;
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			ErrorHandler.continueRunning(e, "��ǰ�߳������ڵȴ�״̬!", false);
		}
	}
	
	//	���ݴӶ���xml��ȡ��type��valueֵ����ϳ�find element �������ֵ������By����
	protected By getBy(String type ,String value){
		By by = null;
		switch(type){
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "className":
			by = By.className(value);	
			break;
		case "tagName":
			by = By.tagName(value);	
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;	
		case "cssSelector":
			by = By.cssSelector(value);
			break; 
		default :
			ErrorHandler.stopRunning("Ԫ�ض�λ����! By "+type+"�����ڴ����� ", false);
		}
		return by;	 
	}
		
	//	��config.xml���õĶ�����ȴ�ʱ���ڣ���λָ��by��Ԫ��
	//	��λ�ɹ���existFlag��Ϊtrue�������׳�Exception����ӡ������־
	protected WebElement waitForElement(final By by,boolean isReport,String pageKey,String objKey) {
		WebElement element = null;   
		try {
			element = new WebDriverWait(driver, objectWaitTime)
			 .until(new ExpectedCondition<WebElement>() {
				 @Override
			     public WebElement apply(WebDriver d) {
			     	existFlag = true;
			        return d.findElement(by);
			     }
			 });
		} catch (Exception e) {
			existFlag = false;
			String message = pageKey+"-"+objKey+" �ȴ�"+objectWaitTime+"���δ����!";
			ErrorHandler.stopRunning(e,message,isReport);
		}
		return element;		
	}
	
	//	��config.xml���õĶ�����ȴ�ʱ���ڣ��ж�ҳ��Ԫ���Ƿ�ɼ�
	//	Ԫ�ؿɼ�������ֵΪtrue�������׳�Exception����ӡ������־	
	protected boolean waitElementToBeDisplayed(final WebElement element, 
			String pageKey, String objKey) {
	    boolean wait = false;
	    if (element == null)
	        return wait;
	    try {
	        wait = new WebDriverWait(driver, objectWaitTime)
	                .until(new ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver d) {
	                        return element.isDisplayed();
	                    }
	                });
	    } catch (Exception e) {
	    	String message = pageKey + "-"+objKey + " δ��ҳ����ʾ!";
	    	ErrorHandler.continueRunning(e, message, true);
	    }
	    return wait;
	}
	
	/**
	* @Description ����ҳ��Ԫ�ز�������
	* @param isWait �Ƿ���Ҫ�ȴ�
	* @param isReport  �Ƿ���Ҫ��ӡ���󱨸�
	* @return WebElement 
	*/
	protected WebElement getElement(String pageKey, String objKey, 
			 boolean isWait, boolean isReport){
		WebElement element = null;
		if(xp.isExist("/object/"+pageKey+"/"+objKey)){
			String type = xp.getElementText("/object/"+pageKey+"/"+objKey+"/type");
			String value = xp.getElementText("/object/"+pageKey+"/"+objKey+"/value");
			By by = getBy(type, value);
			if(isWait){
				//�ȴ�Ԫ����ҳ����ڣ���ʼ��δ�������׳��쳣
				element = waitForElement(by, isReport, pageKey, objKey);
				//�ȴ�Ԫ��Ϊ�ɼ�״̬,��ʼ��δ�ɼ��������־���������
				waitElementToBeDisplayed(element,pageKey,objKey);
				try {
					 element = driver.findElement(by);
		             existFlag = true;		//�ṩelementExist����ʹ��  
				} catch (Exception e) {
					 existFlag = false;
					 String message = "δ���ҵ�ҳ��Ԫ�أ�"+pageKey+"-"+objKey;
		             ErrorHandler.continueRunning(e, message, isReport);
				}	                           
			}
		}else {
			String message = pageKey+"-"+objKey+"δ�ڶ�����ļ��д��ڣ�"+filePath;
			ErrorHandler.stopRunning(message, true);
		}		
		return element;		
	}
	
	/**
	* @Description ���ҳ��Ԫ�ز������棬����WebElement�б�����Ԫ�ض�λʧ��ʱ��ֹͣ����
	* @return List<WebElement> 
	*/
	protected List<WebElement> getElements(String pageKey, String objKey){
		List <WebElement> elements = new ArrayList<WebElement>();
		if(xp.isExist("/object/"+pageKey+"/"+objKey)){
			String type = xp.getElementText("/object/"+pageKey+"/"+objKey+"/type");
			String value = xp.getElementText("/object/"+pageKey+"/"+objKey+"/value");
			By by = getBy(type, value);
			elements = driver.findElements(by);   //findElements�����׳��쳣������·���Ҫ��element�����
			if(elements.size()==0){
				String message = "δ���ҵ�ҳ��Ԫ�أ�"+pageKey+"-"+objKey;
				ErrorHandler.stopRunning(message, true);
			}
			for(WebElement e:elements){
				if(e.equals("")||e==null){
					String message = pageKey+"-"+objKey+" ���ض���������д��ڿ�ֵ";
					ErrorHandler.stopRunning(message, true);
				}
			}
		}else{
			String message = pageKey+"-"+objKey+"δ�ڶ�����ļ��д��ڣ�"+filePath;
			ErrorHandler.stopRunning(message, true);
		}
		return elements;
	}
	
	//waitForPageLoad�����ľ���ʵ�ֺ���
	protected Function<WebDriver, Boolean> isPageLoaded() {
        return new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
	}

	@Override
	public WebDriver getDriver() {
		return driver;
	}

	@Override
	public void takeScreenshot(String screenPath) {
		try{
			File srcFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(screenPath));
		}catch(IOException e){
			ErrorHandler.continueRunning(e, "��ͼ�쳣", false);
		}
	}

}
