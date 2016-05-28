package com.bayviewglen.zork;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> itemsInInventory;
	

	public Inventory() {
		itemsInInventory = new ArrayList<Item>();

	}

	public Inventory(ArrayList<Item> list) {
		itemsInInventory = new ArrayList<Item>();
		
	}

	public ArrayList<Item> getInventory() {
		return itemsInInventory;
	}

	public String toString() {
		String items = "";
		for (Item s : itemsInInventory) {
			items += " " + s.getName() +"\n";
		}
		return "Inventory:\n" + items;
	}

	public void addItem(Item item) {
		itemsInInventory.add(item);
	}

	public void removeItem(Item item) {
		try {
			itemsInInventory.remove(item);
		} catch (Exception ex) {
			System.out.println(item.getName() + " is not a valid object in an inventory.");
		}
	}

	public boolean hasItem(Item x) {
		for (int i = 0; i < itemsInInventory.size(); i++) {
			if (itemsInInventory.get(i).equals(x)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasItem(String x) {
		for (int i = 0; i < itemsInInventory.size(); i++) {
			if (itemsInInventory.get(i).getName().equalsIgnoreCase(x)) {
				return true;
			}
		}
		return false;
	}

	public Item getItem(String word) {
		for (Item x : itemsInInventory) {
			if (x.getName().equalsIgnoreCase(word)) {
				return x;
			}
			
		}
		return null;
	}
	public String getItemString(String word){
		for (Item x: itemsInInventory){
			if(x.getName().equalsIgnoreCase(word)){
				return x.getName();
			}
		}
		return null;
	}
	public int calculateWeight(){
		int weight = 0;
		for(Item x: itemsInInventory){
			weight += x.getWeight();
		}
		return weight;
	}

}
