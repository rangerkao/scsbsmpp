package com.scsb.function;

import java.util.HashMap;

public class GetValue {
	
	public static HashMap<String, String> getGender(){
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("0", "先生");
		map.put("1", "小姐");
		return map;
		
	}
	
	public static HashMap<String, String> getOrderPaid(){
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("1", "貨到付款");
		map.put("2", "線上刷卡");
		map.put("3", "ATM轉帳");
		return map;
	}
	
	public static HashMap<String, String> getInvoiceType(){
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("1", "二聯式紙本發票");
		map.put("2", "三聯式紙本發票");
		return map;
	}
	
	public static HashMap<String, String> getDeliverTime(){
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("1", "不指定");
		map.put("2", "中午前");
		map.put("3", "下午(12:00-17:00)");
		map.put("4", "晚間(17:00-20:00)");
		return map;
	}

	
}
