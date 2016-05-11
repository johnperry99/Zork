package com.bayviewglen.zork;

public class Item {
	private int weight;
	private String name;
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public Item(String name, int weight) {
		super();
		this.weight = weight;
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
}
	
