package com.bayviewglen.zork;

import java.util.HashMap;

public class Teleport {
	public static void teleportTo(Room currentRoom, Command command, HashMap<String, Room> masterRoomMap){
		String destinationRoom = "";
		if(command.hasSecondWord()){
			destinationRoom += command.getSecondWord();
		}
		if(command.hasThirdWord()){
			destinationRoom+= command.getThirdWord();
		}
		if(command.hasFourthWord()){
			destinationRoom+= command.getFourthWord();
		}
		if(validRoom(masterRoomMap, destinationRoom)){
			currentRoom = masterRoomMap.get(destinationRoom);
		}
		
	}
	
	public static boolean validRoom(HashMap<String, Room> masterRoomMap, String destinationRoom){
		if(masterRoomMap.get(destinationRoom) != null){
			return true;
		}
		return false;
	}
}
