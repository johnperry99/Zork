package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */

class Game {
	private Parser parser;
	private Room currentRoom;
	private Player user;
	private boolean finished = false;
	// This is a MASTER object that contains all of the rooms and is easily
	// accessible.
	// The key will be the name of the room -> no spaces (Use all caps and
	// underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the
	// Great Room (assuming you have one).
	private HashMap<String, Room> masterRoomMap;

	private void initRooms(String fileName) throws Exception {
		masterRoomMap = new HashMap<String, Room>();
		Scanner roomScanner;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			roomScanner = new Scanner(new File(fileName));
			while (roomScanner.hasNext()) {
				Room room = new Room();
				// Read the Name
				String roomName = roomScanner.nextLine();
				room.setRoomName(roomName.split(":")[1].trim());
				// Read the Description
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());

				// Read the Exits
				String roomExits = roomScanner.nextLine();
				// An array of strings in the format E-RoomName
				String[] rooms = roomExits.split(":")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>();
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}

				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);

				// handleitems
				String itemString = roomScanner.nextLine();
				// An array of strings in the format ObjectName-Weight
				if (itemString.split(":").length == 2) {
					String[] items = itemString.split(":")[1].split(",");
					if (items[0].indexOf("-") != -1) {
						for (String s : items) {
							room.getInventory()
									.addItem(new Item(s.split("-")[0].trim(), Integer.parseInt(s.split("-")[1])));
						}
					}
				}
				// handlecharacters
				String characterString = roomScanner.nextLine();
				// An array of strings in the format characterName
				if (characterString.split(":").length == 2) {
					String[] characters = characterString.split(":")[1].split(",");
					if (characters[0].indexOf("-") != -1) {
						for (String s : characters) {
							room.getRoster().addCharacter(new Character(s.split("-")[0].trim()));
						}
					}
				}
				// This puts the room we created (Without the exits in the
				// masterMap)
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);

				// Now we better set the exits.
			}

			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.

					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);

				}

			}

			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		try {
			initRooms("data/Rooms.dat");
			currentRoom = masterRoomMap.get("BEDROOM");
		} catch (Exception e) {

			e.printStackTrace();
		}
		parser = new Parser();
		user = new Player();
	}

	/**
	 * Main play routine. Loops until end of play.
	 * @throws InterruptedException 
	 */
	public void play() throws InterruptedException {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
			Thread.sleep(1000);
	}

	/**
	 * Print out the opening message for the player.
	 * @throws InterruptedException 
	 */
	private void printWelcome() throws InterruptedException {
		System.out.println();
		System.out.print("Welcome to ZORK:");
		Thread.sleep(2000);
		System.out.println(" THE WALKING DEAD");
		Thread.sleep(2000);
		System.out.println("This is a new take on the original Zork game, but set in the Walking dead universe!");
		Thread.sleep(1000);
		System.out.println("Enter 'help' to see acceptable commands and your objective.");
		Thread.sleep(2000);
		System.out.println();
		System.out.println(currentRoom.longDescription());
		currentRoom.removeFirstTime();
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command
	 * ends the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equalsIgnoreCase("help"))
			printHelp();
		else if (commandWord.equalsIgnoreCase("go") || commandWord.equalsIgnoreCase("move")
				|| commandWord.equalsIgnoreCase("walk") || commandWord.equalsIgnoreCase("run")
				|| commandWord.equalsIgnoreCase("north") || commandWord.equalsIgnoreCase("south")
				|| commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("east")
				|| commandWord.equalsIgnoreCase("up") || commandWord.equalsIgnoreCase("down"))
			goRoom(command);
		else if (commandWord.equalsIgnoreCase("quit")) {
			if (command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true; // signal that we want to quit
		} else if (commandWord.equalsIgnoreCase("eat"))
			System.out.println("Do you really think you should be eating at a time like this?");
		else if (commandWord.equalsIgnoreCase("inventory") || commandWord.equalsIgnoreCase("i"))
			user.displayInventory();
		else if (commandWord.equalsIgnoreCase("look"))
			System.out.println(currentRoom.getDescription());
		else if (commandWord.equalsIgnoreCase("kill") || commandWord.equalsIgnoreCase("attack"))
			validAttackCommand(command);
		else if (commandWord.equalsIgnoreCase("shoot")) {
			if (user.hasItem("gun") || user.hasItem("crossbow"))
				validAttackCommand(command);
			else
				System.out.println("You don't have a gun!");
		} else if (commandWord.equalsIgnoreCase("take")) {
			if (!command.hasSecondWord() || !CommandWords.isNoun(command.getSecondWord()))
				System.out.println("Take what?");
			else if (command.getSecondWord().equals(currentRoom.getInventory().getItemString(command.getSecondWord())))
				takeItems(command, user.getInventory(), currentRoom.getInventory());
			else
				System.out.println("There isn't an item of that sort here...");
		} else
			System.out.println("I don't understand.");

		return false;

	}

	// implementations of user commands:

	private void validAttackCommand(Command command) {
		if (!command.hasSecondWord()) {
			System.out.println("What do you want to attack?");
		} else if (!command.hasFourthWord()) {
			System.out.println("What do you want to attack " + command.getSecondWord() + " with?");
		} else {
			attack(currentRoom.getRoster(), command);
		}

	}

	private void attack(CharacterRoster roster, Command command) {
		if (roster.hasCharacter(command.getSecondWord())) {

			if (command.getSecondWord().equalsIgnoreCase("Zombie")
					|| command.getSecondWord().equalsIgnoreCase("Zombies")) {
				Assault.attackZombie(currentRoom, user, command);
			} else if (command.getSecondWord().equalsIgnoreCase("Henchman")
					|| command.getSecondWord().equalsIgnoreCase("Henchmen")) {
				Assault.attackHenchman(currentRoom, user, command);
			} else if (command.getSecondWord().equalsIgnoreCase("It") || command.getSecondWord().equalsIgnoreCase("Him")
					|| command.getSecondWord().equalsIgnoreCase("Her")) {
				if (currentRoom.getRoster().hasCharacter("zombie")) {
					Assault.attackZombie(currentRoom, user, command);
				} else if (currentRoom.getRoster().hasCharacter("henchman")) {
					Assault.attackHenchman(currentRoom, user, command);
				} else {
					System.out.println("You can't attack an ally!");
				}
				
			}else{
				System.out.println("You can't attack an ally!");
			}

		}else{
			System.out.println("That character is not here!");
		}

	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		System.out.println(""); // prints the objective of saving Maggie ONLY after you read note
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		String direction;
		if (!command.hasSecondWord() && (command.getCommandWord().equalsIgnoreCase("go")
				|| command.getCommandWord().equalsIgnoreCase("move") || command.getCommandWord().equalsIgnoreCase("run")
				|| command.getCommandWord().equalsIgnoreCase("walk"))) {
			// if there is no second word, we don't know where to go...
			System.out.println("Where would you like to go?");
			return;
		} else if (!command.hasSecondWord()) {
			direction = command.getCommandWord();
		} else {
			direction = command.getSecondWord();
		}
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null)
			System.out.println("You can't go that way!");
		else {
			currentRoom = nextRoom;
			if (currentRoom.isFirstTime()) {
				System.out.println(currentRoom.longDescription());
			} else {
				System.out.println(currentRoom.shortDescription());
			}
			currentRoom.removeFirstTime();
		}
	}

	private void takeItems(Command command, Inventory player, Inventory room) {
		Item temp = room.getItem(command.getSecondWord());
		if (currentRoom.getInventory().hasItem("bag")) {
			if (temp.equals(currentRoom.getInventory().getItem("bag"))) {
				user.addToInventoryCapacity(40);
			}
		}
		int x = temp.getWeight() + player.calculateWeight();
		if (room.hasItem(command.getSecondWord()) && x < user.capacity()) {
			System.out.println("Taken.");
			player.addItem(temp);
			room.removeItem(temp);

		} else if (x > user.capacity()) {
			System.out.println("You can't carry that much!");
		} else {
			System.out.println("There is no " + command.getSecondWord() + " here...");
		}
	}
	public void quit(){
		finished = true;
	}

}