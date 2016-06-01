package com.bayviewglen.zork;

import java.util.HashMap;

public class Teleport {
	public static Room teleportTo(Room currentRoom, Command command, HashMap<String, Room> masterRoomMap){
		String destinationRoom = "";
		if(command.hasSecondWord()){
			destinationRoom += command.getSecondWord().toUpperCase() + "_";
		}
		if(command.hasThirdWord()){
			destinationRoom+= command.getThirdWord().toUpperCase() + "_";
		}
		if(command.hasFourthWord()){
			destinationRoom+= command.getFourthWord().toUpperCase();
		}
		if(validRoom(masterRoomMap, destinationRoom)){
			return masterRoomMap.get(destinationRoom);
		}else{
			System.out.println("Not a valid room.");
		}
		return null;
	}
	
	public static boolean validRoom(HashMap<String, Room> masterRoomMap, String destinationRoom){
		if(masterRoomMap.get(destinationRoom) != null){
			return true;
		}
		return false;
	}
	
}
