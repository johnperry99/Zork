package com.bayviewglen.zork;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> itemsInInventory;
	private ArrayList<Character> characters;

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
		if (itemsInInventory.contains(x)) {
			return true;
		}
		return false;
	}

	public boolean hasItem(String x) {
		for (int i = 0; i < itemsInInventory.size(); i++) {
			if (itemsInInventory.get(i).getName().equals(x)) {
				return true;
			}
		}
		return false;
	}

	public Item getItem(String word) {
		for (Item x : itemsInInventory) {
			if (x.getName().equals(word)) {

			}
			return x;
		}
		return null;
	}

}
