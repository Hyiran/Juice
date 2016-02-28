package org.juice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDataHandler {
	
	
	
	/**
	 * @Description ��Mapת����String Map��ÿһ��key,value��ֵ�Ի�����ʾ
	 * @param Map<String,String>
	 * @return
	 */
	public static String transMapToString(Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		for(String s:map.keySet()){
			sb.append(s+" "+map.get(s)+"\n");
		}
		return sb.toString();	
	}
	
	/**
	 * @Description ��Listת����String List��ÿ��ֵ֮�任����ʾ
	 * @param List<String>
	 * @return
	 */
	public static String transListToString(List<String> list){
		StringBuffer sb = new StringBuffer();
		for(String s:list){
			sb.append(s+"\n");
		}
		return sb.toString();	
	}
	
	/**
	* @Description ����ĸת��д
	* @param String����������ַ���
	* @return String
	*/
	public static String capitalizesTheFirstLetter(String s){
		 if(Character.isUpperCase(s.charAt(0)))
	            return s;
	        else
	            return (new StringBuilder()).append(Character.
	            		toUpperCase(s.charAt(0))).append(s.substring(1)).toString();	 
	}
	
	/**
	* @Description ����ĸתСд
	* @param String����������ַ���
	* @return String
	*/
	public static String lowercaseTheFirstLetter(String s){
		 if(Character.isLowerCase(s.charAt(0)))
	            return s;
	        else
	            return (new StringBuilder()).append(Character.
	            		toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	/**
	* @Description ���ַ�������String[]ת��List<String>
	* @param String[]����������ַ�������
	* @return List<String>
	*/
	public static List<String> stringArrayToList(String[] str){
		List<String> list = new ArrayList<String>();
		for(int i=0;i<str.length;i++){
			if(str[i].startsWith("http"))
			list.add(str[i]);
		}
		return list; 
	}
	    
	/**
	* @Description ���ַ���text����startString��ͷ��endString��β������ƥ���ַ�����һ���ַ��������з���
	* @param text����������ַ���
	* @param startString��������ʼ�ַ���
	* @param endString��������β�ַ���
	* @return String[] �����������ַ�������
	*/
	public static List<String> subTextString(String text, String startString, String endString){
		List<String> Text = stringArrayToList(text.split(startString));
		List<String> finalText = new ArrayList<String>();
		for (int i=1;i<Text.size();i++){		
			finalText.add(i-1, Text.get(i).substring(0,  Text.get(i).indexOf(endString)).trim());
		}	
		return finalText;
	}
	
	/**
	* @Description ��ȡ�ַ���startString�״γ���֮�������(������startString)
	* @param text����������ַ���
	* @param startString��������ʼ�ַ���
	* @return String �������ַ���
	*/
	public static String subTextString(String text, String startString){
		int index = text.indexOf(startString)+startString.length();
		return  text.substring(index);
	}
	
	
	/**
	 * �ж�String�Ƿ�Ϊ��
	 * 
	 * @param s
	 * @return ����ַ���Ϊ�ջ����ַ���ȥ����β�ո�Ϊ���ַ����򷵻�true,��֮����false
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж�map�Ƿ�Ϊ��
	 * 
	 * @param map
	 * @return ���map==null����map.size()==0�򷵻�true,��֮����false
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0) {
			return true;
		}
		return false;
	}

	/***
	 * �ж�list�Ƿ�Ϊ��
	 * 
	 * @param list
	 * @return ���list==null����list.size==�򷵻�true,��֮����false
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

}
