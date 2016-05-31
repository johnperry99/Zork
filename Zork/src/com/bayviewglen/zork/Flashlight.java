package com.bayviewglen.zork;

public class Flashlight extends Item {

	static boolean flashlightShining = false;

	// assuming first two words are "turn on"
	public static void turnItOn(Command command, Room currentRoom) {
		if (command.getThirdWord().equalsIgnoreCase("flashlight")) {
			if (currentRoom.getRoomName().equals("Barn (Inside)")
					|| currentRoom.getRoomName().equals("House (Inside)")) {
				if (flashlightShining) {
					System.out.println("Your flashlight is already turned on.");
				} else {
					System.out.println("You turned on your flashlight.");
					System.out.println("You can see in the room now!");
					flashlightShining = true;
					if (currentRoom.getRoster().hasCharacter("zombie")) {
						Zombie zombie = new Zombie(currentRoom.getRoster().getSize());
						if (((currentRoom.getRoomName().equals("House (Inside)"))
								|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))) {
							
							zombie.zombiePhrase();
						}
					} else if (currentRoom.getRoster().hasCharacter("henchman")) {
						Henchman henchman = new Henchman(currentRoom.getRoster().getSize());
						if (((currentRoom.getRoomName().equals("House (Inside)"))
								|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))) {
							
							henchman.randomPhrase();

						}
					}
				}
			} else {
				if (flashlightShining) {
					System.out.println("Your flashlight is already turned on... but you can see just fine!");
					System.out.println("Why is your flashlight turned on???");
				} else {
					System.out.println("I'm not sure why you turned it on, it's not dark here...but ok.");
					flashlightShining = true;
				}
			}
		} else {
			System.out.println("Turn what on?");
		}

	}

	// assuming first two words were "turn off"
	public static void turnItOff(Command command, Room currentRoom) {
		if (command.getThirdWord().equalsIgnoreCase("flashlight")) {
			if (currentRoom.getRoomName().equals("Barn (Inside)")
					|| currentRoom.getRoomName().equals("House (Inside)")) {
				if (flashlightShining) {
					System.out.println("You turned off the flashlight.");
					System.out.println("It is completely dark here now.");
					flashlightShining = false;
				} else {
					System.out.println("Your flashlight is already turned off.");
				}
			} else {
				if (flashlightShining) {
					System.out.println("You turned off the flashlight.");
					System.out.println("Good thing too, because its not dark outside and you were wasting battery!");
					flashlightShining = false;
				} else {
					System.out.println("Your flashlight is already turned off.");
				}
			}
		} else {
			System.out.println("Turn what off?");
		}
	}

	public static boolean flashLightState() {
		return flashlightShining;
	}

}
