package com.travmahrajvar.bringmefood.utils;

import java.util.ArrayList;

/**
 * Created by Travis on 11/12/17.
 */

public class Wanter {
	private String name;
	private ArrayList<String> orderList;
	
	public Wanter(String name){
		this.name = name;
		this.orderList = new ArrayList<String>();
	}
	
	public Wanter(String name, ArrayList<String> orderList){
		this.name = name;
		this.orderList = orderList;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getOrderList() {
		return orderList;
	}
	
	public void setOrderList(ArrayList<String> orderList) {
		this.orderList = orderList;
	}
}
