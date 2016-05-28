package com.bayviewglen.zork;

public abstract class Assault {

	public static void attackZombie(Room currentRoom, Player user, Command command) {
		Character x = currentRoom.getRoster().getCharacterString("zombie");
		currentRoom.getRoster().removeCharacter(x);
		System.out.println("Slain.");
	}

	public static void attackHenchman(Room currentRoom, Player user, Command command) {
		Character x = currentRoom.getRoster().getCharacterString("henchman");
		currentRoom.getRoster().removeCharacter(x);
		System.out.println("Slain.");
	}

}
