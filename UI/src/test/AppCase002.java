package test;

import io.appium.java_client.android.AndroidKeyCode;

import java.util.List;

import org.juice.testbase.AndroidSuite;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class AppCase002 extends AndroidSuite{

	@Test		
	public void case002(){
	
	List<WebElement> links = locator.elements("��ҳ", "Ƶ������");
	String title = "";
	
		links.get(0).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "��������", "��������ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.wait(1);

		links.get(1).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "��������", "��������ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.wait(1);

		links.get(3).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "���λ�", "���Ż���ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.wait(1);

		checkPoint.result("��ҳ�������Σ������Σ����Ż�������ת������");
	}	
}
