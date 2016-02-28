package org.juice;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class AssertResult extends Assertion{

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
		Log.error("[����ʧ��]�� " +message
							  +" Ԥ�ڽ��: " 	 +assertCommand.getExpected()
							  +" ʵ�ʽ��: "		 +assertCommand.getActual());		
		Reporter.record(2, message);
	}
	
}
