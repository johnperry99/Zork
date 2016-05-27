package com.bayviewglen.zork;

public abstract class Character {
	private boolean alive;
	private String name;
	
	
	
	public boolean getState(){
		return alive;
	}
	public void kill(){
		alive = false;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	

	
}
