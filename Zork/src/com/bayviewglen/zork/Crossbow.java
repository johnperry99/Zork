package com.bayviewglen.zork;

public class Crossbow extends Item implements Depletable{
	private int ammo;

	public Crossbow() {
		super();
		ammo = 10;
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
