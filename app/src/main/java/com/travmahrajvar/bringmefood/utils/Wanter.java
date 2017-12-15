package com.travmahrajvar.bringmefood.utils;

import java.util.ArrayList;

/**
 * Created by Travis on 11/12/17.
 */

public class Wanter {
	private String name;
	private String uid;
	private ArrayList<String> orderList;
	
	public Wanter(String name, String uid){
		this.name = name;
		this.uid = uid;
		this.orderList = new ArrayList<String>();
	}
	
	public Wanter(String name, String uid, ArrayList<String> orderList){
		this.name = name;
		this.uid = uid;
		this.orderList = orderList;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public ArrayList<String> getOrderList() {
		return orderList;
	}
	
	public void setOrderList(ArrayList<String> orderList) {
		this.orderList = orderList;
	}
}
