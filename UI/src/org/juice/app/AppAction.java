package org.juice.app;

public class AppAction {

	private AppLocator locator;
	
	public AppAction(AppLocator locator){
		this.locator = locator;
	}
	
	/**
	 * ͨ��������������������ҳ
	 * @param int times ��������
	 */
	public void skipToMain(int times){	
		if(locator.elementExist(false, "����ҳ", "����ͼ")){
			for(int i=0; i<times; i++){
				locator.swipeActivity("left");
				locator.wait(1);
			}
			locator.element("����ҳ", "��ʼ��ť").click();
		}
	}
	
}
