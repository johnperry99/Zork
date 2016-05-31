package com.bayviewglen.zork;

public class Flashlight extends Item {
	
	static boolean flashlightShining = false;
	
	//assuming first two words are "turn on"
	public static void turnItOn(Command command, Room currentRoom) {
		if (command.getThirdWord().equalsIgnoreCase("flashlight")) {
			if (currentRoom.getRoomName().equals("Barn (Inside)") || currentRoom.getRoomName().equals("House (Inside)")) {
				if (flashlightShining) {
					System.out.println("Your flashlight is already turned on.");
				}else {
					System.out.println("You turned on your flashlight.");
					System.out.println("You can see in the room now!");
					flashlightShining = true;
					//long description now?
				}
			}else {
				if (flashlightShining) {
					System.out.println("Your flashlight is already turned on... but it's bright in this room!");
					System.out.println("(Why is your flashlight turned on???");
				}else {
				System.out.println("...I'm not sure why you turned it on, it's already bright here... but ok.");
				}
			}
		}else {
			System.out.println("Turn what on?");
		}
	}
	//assuming first two words were "turn off"
	public static void turnItOff(Command command, Room currentRoom) {
		if (command.getThirdWord().equalsIgnoreCase("flashlight")) {
			if (currentRoom.getRoomName().equals("Barn (Inside)") || currentRoom.getRoomName().equals("House (Inside)")) {
				if (flashlightShining) {
					System.out.println("You turned off the flashlight.");
					System.out.println("It is completely dark in the room now.");
					flashlightShining = false;
				}else {
					System.out.println("Your flashlight is already turned off.");
				}
			}else {
				if (flashlightShining) {
					System.out.println("You turned off the flashlight.");
					System.out.println("Good thing too, because its not dark outside and you were wasting battery!");
				}else {
					System.out.println("Your flashlight is already turned off.");
				}
			}
		}else {
			System.out.println("Turn what off?");
		}
	}
	

}
