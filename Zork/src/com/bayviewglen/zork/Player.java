package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player {
	
	private Inventory inv = new Inventory();
    private int health = 100;
    private int inventoryCapacity = 10;
    
    
    public void displayInventory(){
    	System.out.println(inv);
    }
    public void addToInventoryCapacity(int x){
    	inventoryCapacity += x;
    }
    public void removeFromInventoryCapacity(int x){
    	inventoryCapacity -= x;
    }
    public void setHealth(int x){
    	health = x;
    }
    public void removeHealth(int x){
    	health -= x;
    }
	
}
