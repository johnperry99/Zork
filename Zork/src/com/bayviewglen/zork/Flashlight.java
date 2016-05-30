package com.bayviewglen.zork;

public class Flashlight extends Item {
	
	boolean flashlightShining = false;
	
	
	public boolean isRoomDark(Room currentRoom) {
		if (currentRoom.getRoomName().equals("Barn (Inside)") || currentRoom.getRoomName().equals("House (Inside)")) {
			return true;
		}else {
			return false;
			
		}
	}
	
	public void turnItOn(Command command) {
		if (command.getThirdWord().equalsIgnoreCase("flashlight")) {
			flashlightShining = true;
			System.out.println("You turned on your flaslight.");
			if (isRoomDark(null)) { //idk
				System.out.println("You can see in the room now!");
				//long description now?
			}
		}
	}
	

}
