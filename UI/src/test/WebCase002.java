package test;

import java.util.List;
import java.util.Map;

import org.juice.testbase.WebSuite;
import org.testng.annotations.Test;


public class WebCase002 extends WebSuite{
	
	@Test(dataProvider="getData")
	public void case002(Map<String,String> data){
		locator.linkToPage(data.get("url"));
		List<String> productTitles = locator.elementsAttribute("��ҳ", "��ѡ��Ʒ", "title");
		checkPoint.equals(productTitles, "null", "��ҳ  ��ѡ��Ʒ  ��Ʒ����Ϊ null");
		checkPoint.equals(productTitles, "", "��ҳ  ��ѡ��Ʒ  ��Ʒ����Ϊ ��");
		checkPoint.result("��ҳ  ��ѡ��Ʒ  ��Ʒ���� ����");
	}
}
