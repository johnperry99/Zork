package com.bayviewglen.zork;

public class Character {
	private boolean alive;
	private String name;
	
	
	public Character(String name){
		this.name = name;
		boolean alive = true;
	}
	public Character(){
		super();
		boolean alive = true;
	}
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
