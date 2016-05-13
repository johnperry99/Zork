package com.bayviewglen.zork;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> itemsInInventory;

	public Inventory() {
		itemsInInventory = new ArrayList<Item>();

	}

	public Inventory(ArrayList<Item> list) {
		itemsInInventory = new ArrayList<Item>();
		for (Item s : list) {
			itemsInInventory.add(s);
		}
	}

	public ArrayList<Item> getInventory() {
		return itemsInInventory;
	}

	
	public String toString() {
		String items = "";
		for (Item s : itemsInInventory) {
			items += " " + s.getName();
		}
		return "Inventory:\n" + items;
	}

	public void add(Item item) {
		itemsInInventory.add(item);
	}
	public boolean hasItem(String x){
		if(itemsInInventory.contains(x)){
			return true;
		}
		return false;
	}
}
