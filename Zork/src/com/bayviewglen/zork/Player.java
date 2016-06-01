package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {

	private Inventory inv = new Inventory();
	private int health = 100;
	private int inventoryCapacity = 10;
	private int currentWeight;
	private int gunAmmo = 10;
	private int crossbowAmmo = 10;

	public Player(Inventory inv, int health, int inventoryCapacity, int currentWeight) {
		super();
		this.inv = inv;
		this.health = health;
		this.inventoryCapacity = inventoryCapacity;
		this.currentWeight = currentWeight;
	}

	public void displayInventory() {
		System.out.println(inv);
		System.out.println("Current Capacity: " + inventoryCapacity);
		System.out.println("Current Weight: " + currentWeight);
		System.out.println("Current Health:  " + health);
	}

	public Inventory getInventory() {
		return inv;
	}

	public void addGunAmmo(int x) {
		gunAmmo += x;

	}

	public void addCrossbowAmmo(int x) {
		crossbowAmmo += x;
	}

	public void addToInventoryCapacity(int x) {
		inventoryCapacity += x;
	}

	public void removeFromInventoryCapacity(int x) {
		inventoryCapacity -= x;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int x) {
		health = x;
	}

	public void removeHealth(int x) { // from zombies
		health -= x;
	}

	public void addHealth(int x) { // from food
		health += x;
	}

	public boolean hasItem(String x) {
		if (inv.hasItem(x)) {
			return true;
		}
		return false;
	}

	public int capacity() {
		return inventoryCapacity;
	}

	public void addWeight(int x) {
		currentWeight += x;
	}

	public void removeWeight(int x) {
		currentWeight -= x;
	}

}
