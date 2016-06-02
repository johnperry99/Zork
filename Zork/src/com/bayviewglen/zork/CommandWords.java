package com.bayviewglen.zork;
/*
 * Author:  Michael Kolling.
 * Version: 1.0
 * Date:    July 1999
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognize commands as they are typed in.
 *
 * This class is part of the "Zork" game.
 */

import java.io.Serializable;

class CommandWords implements Serializable{
	// a constant array that holds all valid command words
	private static final String validCommands[] = { "go", "move", "walk", "run", "save", "quit", "help", "eat", "move", "look", "read",
			 "get", "in", "talk", "attack", "shoot", "kill", "take", "pick", "turn", "on", "off", "drive", "info", "information", "drop",
			 "inventory", "i", "north", "east", "west", "south", "up", "down", "n", "e", "w", "s", "u", "d", "&&t" };
	
	private static final String validNouns[] = { "bag", "note", "car", "gun", "knife", "crossbow", "key", "sign", "ammo", "all", "everything",
			"food", "pizza", "book", "person", "sword", "inventory", "flashlight", "object", "item", "Rick", "Daryl", "Sasha", "Carol", "Carl", "Maggie",
			"henchman", "henchmen", "saviour", "saviours", "saviour henchmen", "saviour henchman", "zombie", "zombies"  };

	/**
	 * Constructor - initialize the command words.
	 */
	public CommandWords() {
		// nothing to do at the moment...
	}

	/**
	 * Check whether a given String is a valid command word. Return true if it
	 * is, false if it isn't.
	 **/
	public boolean isCommand(String aString) {
		for (int i = 0; i < validCommands.length; i++) {
			if (validCommands[i].equalsIgnoreCase(aString))
				return true;
		}
		// if we get here, the string was not found in the commands
		return false;
	}

	public static boolean isNoun(String str) {
		for (int i = 0; i < validNouns.length; i++) {
			if (validNouns[i].equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	/*
	 * Print all valid commands to System.out.
	 */
	public void showAll() {
		for (int i = 0; i < validCommands.length; i++) {
			if(i%10==0){
				System.out.println("");
			}
			if(validCommands[i] != "&&t"){
			System.out.print(validCommands[i] + "  ");
			}
		}
		System.out.println();

	}
}