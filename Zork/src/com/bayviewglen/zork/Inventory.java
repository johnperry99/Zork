package com.bayviewglen.zork;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<String> itemsInInventory;

	public Inventory() {
		itemsInInventory = new ArrayList<String>();
		
	}

	public Inventory(ArrayList<String> list) {
		itemsInInventory = new ArrayList<String>();
		for (String s : list) {
			itemsInInventory.add(s);
		}
	}

	public ArrayList<String> getInventory() {
		return itemsInInventory;
	}

	public String toString() {
		String items = "";
		for (String s : itemsInInventory) {
			items += " " + s;
		}
		return items;
	}
	public void add(String item){
		itemsInInventory.add(item);
	}
}
