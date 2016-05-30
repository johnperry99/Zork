package com.bayviewglen.zork;
/*
 * Author:  Michael Kolling.
 * Version: 1.0
 * Date:    July 1999
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * This class is part of the "Zork" game.
 */

class CommandWords {
	// a constant array that holds all valid command words
	private static final String validCommands[] = { "go", "move", "walk", "run", "save", "quit", "help", "eat", "move", "look", "read", "shine", "jump",
			"sit down", "stand up", "talk", "attack", "shoot", "kill", "open", "close", "take", "all", "everything", "turn on", "drive", "get in",
			"inventory", "i", "north", "east", "west", "south", "up", "down", "n", "e", "w", "s", "u", "d" };
	
	private static final String validNouns[] = { "bag", "note", "car", "gun", "knife", "crossbow", "desk", "chair", "table", "door", "sign",
			"food", "book", "person", "sword", "inventory", "flashlight", "zombies", "zombie", "Rick", "Daryl", "Sasha", "Carol", "Carl", "Maggie",
			"henchman", "henchmen", "saviour", "saviours", "saviour henchmen", "saviour henchman", "zombie", "zombies" };

	/**
	 * Constructor - initialise the command words.
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

	public boolean isNoun(String str) {
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
			if(i%7==0){
				System.out.println("");
			}
			System.out.print(validCommands[i] + "  ");
		}
		System.out.println();

	}
}