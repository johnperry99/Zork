package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character{
	
	private Inventory inv = new Inventory();
    private int health = 100;
    private int inventoryCapacity = 10;
    private boolean flashlightShining;
    
    
   
	public Player(Inventory inv, int health, int inventoryCapacity, boolean flashlightShining) {
		super();
		this.inv = inv;
		this.health = health;
		this.inventoryCapacity = inventoryCapacity;
		this.flashlightShining = flashlightShining;
	}
	public Player(){
		boolean flashlightShining = false;
	}
	public boolean isFlashlightOn() {
		return flashlightShining;
	}
	public void setFlashlightOn(boolean flashlightOn) {
		this.flashlightShining = flashlightOn;
	}
	public void displayInventory(){
    	System.out.println(inv);
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
    public void removeHealth(int x){
    	health -= x;
    }
    public void addHealth(int x){
    	health += x;
    }
    public boolean hasItem(String x){
    	if(inv.hasItem(x)){
    		return true;
    	}
    	return false;
    }
	
}
