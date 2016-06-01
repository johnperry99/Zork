package com.bayviewglen.zork;

import java.io.Serializable;

public class Character implements Serializable{
	private boolean alive;
	private String name;
	
	
	public Character(String name){
		this.name = name;
		alive = true;
	}
	public Character(){
		super();
		alive = true;
	}
	public boolean isAlive(){
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
