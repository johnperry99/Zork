package com.bayviewglen.zork;

import java.io.Serializable;

public class Gun extends Item implements Depletable, Serializable{
	private int ammo;

	public Gun() {
		super();
		ammo = 4;
	}
	
	public boolean noAmmo(){
		if(ammo>0){
			return false;
		} else {
			return true;
		}
	}
	
	public void reduceAmmo(){
		ammo--;
	}
	
	public void setAmmo(int ammoVal){
		ammo++;
	}
	
	public int getAmmo(){
		return ammo;
	}
}
