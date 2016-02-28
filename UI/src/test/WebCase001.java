package test;

import java.util.Map;
import org.juice.testbase.WebSuite;
import org.testng.annotations.Test;

public class WebCase001 extends WebSuite{

	@Test(dataProvider="getData")
	public void webCase001(Map<String,String> data){
		locator.linkToPage(data.get("url"));
		locator.element("��ҳ", "Ŀ�ĵ�").clear();
		locator.element("��ҳ", "Ŀ�ĵ�").sendKeys(data.get("Ŀ�ĵ�"));
		locator.element("��ҳ", "�ؼ���").clear();
		locator.element("��ҳ", "�ؼ���").sendKeys(data.get("�ؼ���"));
		locator.element("��ҳ", "����").click();
		String totalNumber = locator.element("�Ƶ��������ҳ", "�������").getAttribute("Text");
		String serchInfo = "Ŀ�ĵأ�"+data.get("Ŀ�ĵ�")+" �ؼ��ʣ�"+data.get("�ؼ���");
		checkPoint.equals(totalNumber, "0", serchInfo+"���������");		
		checkPoint.result(serchInfo+" ��������  "+totalNumber+" �����");		
	}	
}
