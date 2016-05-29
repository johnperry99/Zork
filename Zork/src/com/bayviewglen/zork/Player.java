package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character{
	
	private Inventory inv = new Inventory();
    private int health = 100;
    private int inventoryCapacity = 10;
    private boolean flashlightShining;
    private int currentWeight;
    
    
   
	public Player(Inventory inv, int health, int inventoryCapacity, boolean flashlightShining, int currentWeight) {
		super();
		this.inv = inv;
		this.health = health;
		this.inventoryCapacity = inventoryCapacity;
		this.flashlightShining = flashlightShining;
		this.currentWeight = currentWeight;
	}
	public Player(){
		 flashlightShining = false;
		 currentWeight = 0;
	}
	public boolean isFlashlightOn() {
		return flashlightShining;
	}
	public void setFlashlightOn(boolean flashlightOn) {
		this.flashlightShining = flashlightOn;
	}
	public void displayInventory(){
    	System.out.println(inv);
    	System.out.println("Current Capacity: " + inventoryCapacity);
    	System.out.println("Current Weight: " + currentWeight);
    }
    public Inventory getInventory(){
    	return inv;
    }
    public void addToInventoryCapacity(int x){
    	inventoryCapacity += x;
    }
    public void removeFromInventoryCapacity(int x){
    	inventoryCapacity -= x;
    }
    public int getHealth(){
    	return health;
    }
    public void setHealth(int x){
    	health = x;
    }
    public void removeHealth(int x){ //from zombies
    	health -= x;
    }
    public void addHealth(int x){ //from food
    	health += x;
    }
    public boolean hasItem(String x){
    	if(inv.hasItem(x)){
    		return true;
    	}
    	return false;
    }
    public int capacity(){
    	return inventoryCapacity;
    }
    public void addWeight(int x){
    	currentWeight += x;
    }
    public void removeWeight(int x){
    	currentWeight -= x;
    }
	
}
