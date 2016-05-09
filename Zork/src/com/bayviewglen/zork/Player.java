package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player {
	
	private Inventory inv = new Inventory();
    private int health = 100;
    private int inventoryCapacity = 100;
    
    
    public void displayInventory(){
    	System.out.println(inv);
    }
	
}
