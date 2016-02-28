package org.juice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
	
	
	public static String formatDate(Date date){     
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
	}
	
	public static String formatDate(long date){     
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
	}
	
	/**
	* @Description ��Date��ʽ�����ڣ�ת����"yyyy-MM-dd" String��ʽ
	* @param  date  ��Ҫ���и�ʽ�������ڲ���
	* @return String ת�����ַ���"yyyy-MM-dd"������
	*/
	public static String getFormatDate(Date date){			
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);	
	}
	
	/**
	* @Description ��String��ʽ�����ڣ�ת����Date��ʽ
	* @param  String  ��Ҫ���и�ʽ��������
	* @return Date ת�������ڸ�ʽ"yyyy-MM-dd"
	* @throws ����Ĳ���Date��ʽ�׳��쳣
	*/
	public static Date ParseDate(String date) {		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(date);			
			} 	
		catch (ParseException e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	* @Description �õ���ǰ����"yyyy-MM-dd"���ַ���
	* @return String 
	*/
	public static String getNowDayString(){		
		return getFormatDate(new Date());
	}
	
	/**
	* @Description �õ�date���ڣ�ǰ���Number����ַ�����ʽ"yyyy-MM-dd"����
	* @param date ��������
	* @param Number ��������  ����Ϊ֮ǰ������Ϊ֮��
	* @return String 
	*/
	public static String getForwardDayString(String date,int Number) {	
		Calendar AddDay = Calendar.getInstance();
		AddDay.setTime(ParseDate(date));
		AddDay.add(Calendar.DATE, Number);
		return getFormatDate(AddDay.getTime());		
	}
	
	
}
