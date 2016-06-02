package com.bayviewglen.zork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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

class Game implements Serializable {
	private Parser parser;
	private Room currentRoom;
	private Player user;
	private Zombie zombie;
	private Henchman henchman;
	private static boolean finished = false;
	private boolean firstTime = true;
	private boolean gameDone = false;
	private boolean forceQuit;
	private Quit quit;
	private boolean inCar = false;
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
	private void save() {
		try {
			File saveFile = new File("data/Save.dat");
			FileOutputStream fileOutput = new FileOutputStream(saveFile);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			writeObject(objectOutput);
			objectOutput.close();
			fileOutput.close();
			System.out.println("Game Saved.");
		} catch (IOException e) {
			e.printStackTrace();
		} // try catch
	}

	private void loadGame() {
		try {
			File saveFile = new File("data/Save.dat");
			FileInputStream fileInput = new FileInputStream(saveFile);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			readObject(objectInput);
			objectInput.close();
			fileInput.close();
			System.out.println("Game Loaded");
			if (currentRoom.isFirstTime()) {
				System.out.println(currentRoom.longDescription());
			} else {
				System.out.println(currentRoom.shortDescription());
			}
			if (currentRoom.getRoster().hasCharacter("henchman")) {
				henchman = new Henchman(currentRoom.getRoster().getSize());
				if (((currentRoom.getRoomName().equals("House (Inside)"))
						|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
						&& (Flashlight.flashLightState() == false)) {
					System.out.println("...");
				} else {
					if (currentRoom.getRoomName().equals("Outside Saviours Compound")) {
						henchman.lastPhrase();
					} else {
						henchman.randomPhrase();
					}
				}
			}
			if (currentRoom.getRoster().hasCharacter("zombie")) {
				zombie = new Zombie(currentRoom.getRoster().getSize());
				if (((currentRoom.getRoomName().equals("House (Inside)"))
						|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
						&& (Flashlight.flashLightState() == false)) {
					System.out.println("...");
				} else {
					zombie.zombiePhrase();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.writeObject(currentRoom);
		stream.writeObject(masterRoomMap);
		stream.writeObject(user);
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		currentRoom = (Room) stream.readObject();
		masterRoomMap = (HashMap<String, Room>) stream.readObject();
		user = (Player) stream.readObject();
	}

	public Game() {
		try {
			initRooms("data/Rooms.dat");
			currentRoom = masterRoomMap.get("BEDROOM");
		} catch (Exception e) {

			e.printStackTrace();
		}
		parser = new Parser();
		int hp = 100;
		Inventory inven = new Inventory();
		int invCapacity = 10;
		int currWeight = 0;
		user = new Player(inven, hp, invCapacity, currWeight);
	}

	/**
	 * Main play routine. Loops until end of play.
	 * 
	 * @throws InterruptedException
	 */
	public void play() throws InterruptedException {
		Scanner input = new Scanner(System.in);
		printWelcome();
		System.out.println();
		System.out.println("Enter a number:");
		System.out.println();
		System.out.println("1. NEW GAME");
		System.out.println("2. CONTINUE");
		boolean numEntered = false;
		boolean newGame = true;

		while (numEntered == false) {
			try {
				int val = Integer.parseInt(input.nextLine());
				if (val == 1) {
					numEntered = true;

				} else if (val == 2) {
					numEntered = true;
					newGame = false;
				} else {
					System.out.println("Please enter one of the numbers listed above.");
				}
			} catch (Exception ex) {
				System.out.println("Please enter a valid integer.");
			}
		}

		if (!newGame) {
			loadGame();
		} else {
			System.out.println("Enter 'help' to see acceptable commands and your objective.");
			Thread.sleep(1000);
			System.out.println(
					"Directions you can travel are North (n), South (s), East (e), West (w), Up (u), and Down (d).");
		}
		Thread.sleep(2000);
		System.out.println();
		System.out.println(currentRoom.longDescription());
		currentRoom.removeFirstTime();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		while (!finished && user.isAlive()) {
			Command command = parser.getCommand();
			finished = processCommand(command);
			if (gameDone) {
				quit = new Quit(finished, false);
				quit.getFinished();
				finished = quit.isFinished();
			}

		}
		if (gameDone) {
			System.out.println("\nWell well well, look at you. Finished the game. Was it hard?\n"
					+ "Do you know that there are multiple endings? Play again and try to find them!");
		} else if (forceQuit) {
			System.out.println("Thanks for playing! Come play again soon!");
		} else {
			System.out.println("You are dead: GAME OVER.");
		}
		Thread.sleep(2000);
	}

	/**
	 * Print out the opening message for the player.
	 * 
	 * @throws InterruptedException
	 */
	private void printWelcome() throws InterruptedException {
		System.out.println();
		System.out.print("Welcome to ZORK:");
		Thread.sleep(1000);
		System.out.println(" THE WALKING DEAD");
		Thread.sleep(1500);
		System.out.println("This is a new take on the original Zork game, but set in The Walking Dead universe!");
		Thread.sleep(1000);
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command
	 * ends the game, true is returned, otherwise false is returned.
	 * 
	 * @throws InterruptedException
	 */
	private boolean processCommand(Command command) throws InterruptedException {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equalsIgnoreCase("help")) {
			printHelp();
		} else if (command.getCommandWord().equalsIgnoreCase("save")) {
			save();

		} else if (command.getCommandWord().equals("&&t")) {
			Room temp = Teleport.teleportTo(currentRoom, command, masterRoomMap);
			if (temp != null) {
				currentRoom = temp;
				System.out.println(currentRoom.getRoomName() + "\n" + currentRoom.longDescription());
				if (currentRoom.getRoomName().equals("Kitchen") && !user.getInventory().hasItem("bag")) {
					user.addToInventoryCapacity(30);
					Item x = currentRoom.getInventory().getItem("bag");
					user.getInventory().addItem(x);
					currentRoom.getInventory().removeItem(x);
				}
				if (currentRoom.getRoomName().equals("Kitchen") && !user.getInventory().hasItem("knife")
						&& !user.getInventory().hasItem("pizza")) {
					Item k = currentRoom.getInventory().getItem("pizza");
					Item y = currentRoom.getInventory().getItem("knife");
					user.getInventory().addItem(k);
					user.getInventory().addItem(y);
					currentRoom.getInventory().removeItem(k);
					currentRoom.getInventory().removeItem(y);
				}
				if (currentRoom.getRoster().hasCharacter("henchman")) {
					henchman = new Henchman(currentRoom.getRoster().getSize());
					if (((currentRoom.getRoomName().equals("House (Inside)"))
							|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
							&& (Flashlight.flashLightState() == false)) {
						System.out.println("...");
					} else {
						if (currentRoom.getRoomName().equals("Outside Saviours Compound")) {
							henchman.lastPhrase();
						} else {
							henchman.randomPhrase();
						}
					}
				}
				if (currentRoom.getRoster().hasCharacter("zombie")) {
					zombie = new Zombie(currentRoom.getRoster().getSize());
					if (((currentRoom.getRoomName().equals("House (Inside)"))
							|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
							&& (Flashlight.flashLightState() == false)) {
						System.out.println("...");
					} else {
						zombie.zombiePhrase();
					}
				}
			}
			
		} else if (commandWord.equalsIgnoreCase("go") || commandWord.equalsIgnoreCase("move")

				|| commandWord.equalsIgnoreCase("walk") || commandWord.equalsIgnoreCase("run")
				|| commandWord.equalsIgnoreCase("north") || commandWord.equalsIgnoreCase("south")
				|| commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("east")
				|| commandWord.equalsIgnoreCase("up") || commandWord.equalsIgnoreCase("down")
				|| commandWord.equalsIgnoreCase("n") || commandWord.equalsIgnoreCase("s")
				|| commandWord.equalsIgnoreCase("e") || commandWord.equalsIgnoreCase("w")
				|| commandWord.equalsIgnoreCase("u") || commandWord.equalsIgnoreCase("d")) {
			goRoom(command);
		} else if (commandWord.equalsIgnoreCase("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("If you mean quit game, enter \"quit\".");
			} else {
				quit = new Quit(finished, true);
				quit.ask();
				forceQuit = quit.gameNotComplete();
				finished = quit.isFinished();
				return finished; // signal that we want to quit
			}
		} else if (commandWord.equalsIgnoreCase("eat")) {
			eat(command);
		} else if (commandWord.equalsIgnoreCase("inventory") || commandWord.equalsIgnoreCase("i")
				|| commandWord.equalsIgnoreCase("information")) {
			user.displayInventory();
			currentRoom.displayInventory();
		} else if (commandWord.equalsIgnoreCase("look")) {
			if (user.getInventory().hasItem("flashlight") && Flashlight.flashLightState()
					&& ((currentRoom.getRoomName().equals("House (Inside"))
							|| ((currentRoom.getRoomName().equals("Barn (Inside)")))))
				System.out.println(currentRoom.longDescription());
			else if (!((currentRoom.getRoomName().equals("House (Inside"))
					&& !((currentRoom.getRoomName().equals("Barn (Inside)")))))
				System.out.println(currentRoom.longDescription());
			else
				System.out.println("You can't see!");
		} else if (commandWord.equalsIgnoreCase("kill") || commandWord.equalsIgnoreCase("attack"))
			validAttackCommand(command);
		else if (commandWord.equalsIgnoreCase("shoot")) {
			if (user.hasItem("gun") || user.hasItem("crossbow"))
				validAttackCommand(command);
			else
				System.out.println("You don't have a gun or crossbow!");
		} else if (commandWord.equalsIgnoreCase("take")) {
			if (!command.hasSecondWord())
				System.out.println("What would you like to take?");
			else if (command.getSecondWord().equals("all") || command.getSecondWord().equals("everything"))
				System.out.println("Please take one item at a time, as you may not be able to carry everything.");
			else if (command.getSecondWord().equals(currentRoom.getInventory().getItemString(command.getSecondWord()))
					|| (currentRoom.getInventory().hasItem("flashlight")))
				takeItems(command, user.getInventory(), currentRoom.getInventory());
			else if (command.getSecondWord().equals(user.getInventory().getItemString(command.getSecondWord())))
				System.out.println("You already have that!");
			else
				System.out.println("There isn't an item of that sort here...");

		} else if (commandWord.equalsIgnoreCase("pick")) {
			if (command.hasSecondWord() && command.hasThirdWord() && command.getSecondWord().equals("up")) {
				command.setCommandWord("pick up");
				command.setSecondWord(command.getThirdWord());
				takeItems(command, user.getInventory(), currentRoom.getInventory());
			} else {
				System.out.println("What?");
			}
		} else if (commandWord.equalsIgnoreCase("read")) {
			if (!command.hasSecondWord())
				System.out.println("Read what?");
			else if (command.getSecondWord().equals(currentRoom.getInventory().getItemString(command.getSecondWord()))
					|| command.getSecondWord().equals(user.getInventory().getItemString(command.getSecondWord())))
				read(command);
			else
				System.out.println("There's nothing to read...");
		} else if (command.getCommandWord().equalsIgnoreCase("drop") && command.hasSecondWord()
				&& command.isNoun(command.getSecondWord())) {
			dropItem(command);
		} else if (command.getCommandWord().equals("turn")) {
			if (!command.hasSecondWord())
				System.out.println("What do you mean by turn?");
			else if (!(currentRoom.getRoomName().equals("Alexandria Entrance"))
					&& command.getSecondWord().equalsIgnoreCase("on")
					|| command.getSecondWord().equalsIgnoreCase("off"))
				flashlight(command);
			else
				System.out.println("Turn what?");
		} else if (commandWord.equalsIgnoreCase("talk")) {
            if (!command.hasSecondWord())
                  System.out.println("Talk to who?");
            else 
                  talk(command);
		} else if (commandWord.equalsIgnoreCase("drive") || command.hasSecondWord()
				&& (commandWord.equalsIgnoreCase("get") || commandWord.equalsIgnoreCase("turn"))) {
			if (commandWord.equalsIgnoreCase("drive") && !(command.hasSecondWord()))
				System.out.println("Drive what?");
			else if ((commandWord.equalsIgnoreCase("drive")
					|| (commandWord.equalsIgnoreCase("get") || commandWord.equalsIgnoreCase("turn"))
							&& command.hasSecondWord()))
				drive(command);
			else
				System.out.println("What?");
		} else {
			int select = ThreadLocalRandom.current().nextInt(0, 4);
			if (select == 0)
				System.out.println("What do you mean?");
			else if (select == 1)
				System.out.println("Can you speak clearer? Start with a command word (enter 'help' to see them).");
			else if (select == 2)
				System.out.println("I don't understand...");
			else if (select == 3)
				System.out.println("Huh? Speak up!");
		}

		return false;

	}

	// implementations of user commands:

	private void validAttackCommand(Command command) throws InterruptedException {
		if (!command.hasSecondWord()) {
			System.out.println("What do you want to attack?");
		} else if (!command.hasFourthWord()) {
			System.out.println("What do you want to attack a '" + command.getSecondWord() + "' with?");
		} else {
			attack(currentRoom.getRoster(), command);
		}

	}

	private void attack(CharacterRoster roster, Command command) throws InterruptedException {
		if (roster.hasCharacter(command.getSecondWord()) || roster.hasCharacter("zombie")
				|| roster.hasCharacter("henchman")) {

			if (command.getSecondWord().equalsIgnoreCase("zombie")
					|| command.getSecondWord().equalsIgnoreCase("zombies")) {
				Assault.attackZombie(currentRoom, user, command, zombie);
			} else if (command.getSecondWord().equalsIgnoreCase("henchman")
					|| command.getSecondWord().equalsIgnoreCase("henchmen")
					|| command.getSecondWord().equalsIgnoreCase("saviour henchman")
					|| command.getSecondWord().equalsIgnoreCase("saviour henchmen")
					|| command.getSecondWord().equalsIgnoreCase("saviour")
					|| command.getSecondWord().equalsIgnoreCase("saviours")) {
				Assault.attackHenchman(currentRoom, user, command, henchman);
			} else if (command.getSecondWord().equalsIgnoreCase("It") || command.getSecondWord().equalsIgnoreCase("Him")
					|| command.getSecondWord().equalsIgnoreCase("Her")) {
				if (currentRoom.getRoster().hasCharacter("zombie")) {
					Assault.attackZombie(currentRoom, user, command, zombie);
				} else if (currentRoom.getRoster().hasCharacter("henchman")) {
					Assault.attackHenchman(currentRoom, user, command, henchman);
				} else {
					System.out.println("You can't attack an ally!");
				}

			} else {
				System.out.println("You can't attack an ally!");
			}

		} else {
			System.out.println("That character is not here!");
		}

	}

	private void drive(Command command) {
		if (user.getInventory().hasItem("key") && currentRoom.getInventory().hasItem("car")
				&& ((command.getSecondWord().equalsIgnoreCase("on") && command.getThirdWord().equalsIgnoreCase("car"))
						|| (command.getSecondWord().equalsIgnoreCase("in")
								&& command.getThirdWord().equalsIgnoreCase("car"))
				|| (command.getSecondWord().equalsIgnoreCase("car")))) {
			System.out.println("You approach the car and enter it. You then start the engine with the key.");
			System.out.println("Where would you like to drive with the car.");
			inCar = true;
		} else if (!(user.getInventory().hasItem("key")) && currentRoom.getInventory().hasItem("car")
				&& ((command.getSecondWord().equalsIgnoreCase("on") && command.getThirdWord().equalsIgnoreCase("car"))
						|| (command.getSecondWord().equalsIgnoreCase("in")
								&& command.getThirdWord().equalsIgnoreCase("car"))
				|| (command.getSecondWord().equalsIgnoreCase("car")))) {
			System.out.println("You don't have the key to turn on the car...");
		} else if (!(currentRoom.getInventory().hasItem("car"))
				&& ((command.getSecondWord().equalsIgnoreCase("on") && command.getThirdWord().equalsIgnoreCase("car"))
						|| (command.getSecondWord().equalsIgnoreCase("in")
								&& command.getThirdWord().equalsIgnoreCase("car"))
				|| (command.getSecondWord().equalsIgnoreCase("car")))) {
			System.out.println("There is no car here...");
		} else {
			System.out.println("What?");
		}
	}
	
	private void talk(Command command) {
        if (currentRoom.getRoster().hasCharacter("Sasha") && command.getSecondWord().equalsIgnoreCase("Sasha")
                     || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("Sasha"))) {
               System.out.println("Sasha: \"Take this note and read it... I'm really sorry.\"");
        }
        else if (currentRoom.getRoster().hasCharacter("Carol") && command.getSecondWord().equalsIgnoreCase("Carol")
                     || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("Carol"))) {
               System.out.println("Carol: \"Hey... I know you're very upset... "
               		+ "But you really should wait for us to come up with a good plan to save Maggy.\"");
        }
        else if (currentRoom.getRoster().hasCharacter("Carl") && command.getSecondWord().equalsIgnoreCase("Carl")
                     || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("Carl"))) {
               System.out.println("Carl: \"Sorry about Maggy, man. Just don't do anything crazy.\"");
        }
        else if (currentRoom.getRoster().hasCharacter("Rick") && command.getSecondWord().equalsIgnoreCase("Rick")
                     || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("Rick"))) {
               System.out.println("Rick: \"Remember: in the forest you might find some ammo packs. You may also find some in abandoned buildings.\"");
        }
        else if (currentRoom.getRoster().hasCharacter("Daryl") && command.getSecondWord().equalsIgnoreCase("Daryl")
                     || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("Daryl"))) {
               System.out.println("Daryl: Hey man, here's a tip. If you ever encounter a Saviour henchman, kill them immediately, "
                            + "because running away from them will get you killed.");
        }
        else if (currentRoom.getRoster().hasCharacter("zombie") && command.getSecondWord().equalsIgnoreCase("zombie")
                || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("zombie"))) {
        	  System.out.println("The zombie said \"hi\".");
        }
        else if (currentRoom.getRoster().hasCharacter("henchman") && (command.getSecondWord().equalsIgnoreCase("henchman")
                || command.getSecondWord().equalsIgnoreCase("henchmen") || (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("henchmen"))
                || command.getThirdWord().equalsIgnoreCase("henchmen"))) {
        	  System.out.println("Saviour");
        }
        else {
               System.out.println("Either that person doesn't exist or is not in this area.");
        }
 }

	private void eat(Command command) {
		if (command.hasSecondWord()
				&& (command.getSecondWord().equalsIgnoreCase("food")
						|| command.getSecondWord().equalsIgnoreCase("pizza"))
				&& user.getInventory().hasItem("pizza") || currentRoom.getInventory().hasItem("pizza")) {
			if (user.getHealth() <= 50) {
				user.addHealth(50);
				System.out.println("You ate the pizza. It tasted delicious");
				System.out.println("You now have " + user.getHealth() + " health.");
			} else if (user.getHealth() > 50 && user.getHealth() < 100) {
				user.setHealth(100);
				System.out.println("You ate the pizza. It tasted delicious");
				System.out.println("You now have 100 health!");

			} else {
				System.out.println("Do you really think you should be eating at a time like this?");
				System.out.println("At least wait until you can heal health with it.\n(Food heals a lot of health.)");
			}

		} else if (!command.hasSecondWord()) {
			System.out.println("Eat What?");

		} else {
			System.out.println("Do you really think you should be eating at a time like this?");
		}
	}

	private void read(Command command) throws InterruptedException {
		if (command.getSecondWord().equalsIgnoreCase("note")) {
			if (firstTime == false) {
				System.out.println("\"There's something very important that you need to know.");
				System.out.println(
						"We're all very sorry to say this but... Glenn... Your wife, Maggy, has been kidnapped.");
				System.out.println("We know you are going to go look for her, but we strongly advise against it.");
				System.out.println(
						"If anything you should wait a few days for us to plan out how we are going to find her.\"");
			} else {
				System.out.println("There's some very important that you need to know.");
				System.out.println(
						"We're all very sorry to say this but... Glenn... Your wife, Maggy, has been kidnapped by the Saviours.");
				System.out.println("We know you are going to go look for her, but we strongly advise against it.");
				System.out.println(
						"If anything you should wait a few days for us to plan out how we are going to find her.");
				Thread.sleep(5000);
				System.out.println("\nYour heart begins to beat rapidly, your breath shortens,");
				System.out.println("and it takes all your strength to stop yourself from passing out.");
				System.out.println(
						"You immediately decide to leave the safety of your home, Alexandria, and search for your wife.");
				firstTime = false;
			}
		} else if (command.getSecondWord().equalsIgnoreCase("sign")) {
			if (currentRoom.getRoomName().equals("Forest Section 1")) {
				System.out.println(
						"Beware... Zombies and enemies are everwhere. You may be able to leave an area with zombies-");
				System.out.println(
						"but there is a chance they'll damage you. *You won't always kill an enemy and go unharmed*");
				System.out.println("If you ever happen to encounter the Saviour Compound, procede with caution...");
				System.out.println("...Or you will die.");
			} else if (currentRoom.getRoomName().equals("House (Outside)")) {
				System.out.println("Bob's abandoned home.");
				System.out.println("Take what you need and leave, before it's too late.");
			} else {
				System.out.println("Uncle Jeffrey's abandoned barn.");
				System.out.println("Take what you need and leave, before it's too late");
			}
		} else {
			System.out.println("There's nothing to read...");
		}

	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("Read the note for your objective.");
		System.out.println("Max number of words per command: 4");
		System.out.println("Avoid using words like 'the', 'a', 'this', 'your', 'my', etc...\n");
		System.out.println("Enter i, info, information, or inventory to display your stats/inventory\n");
		System.out.println("Your command words are:");
		parser.showCommands();
		System.out.println("Common command words include: go, read, take, drop, and attack/kill");
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 * 
	 * @throws InterruptedException
	 */
	private void goRoom(Command command) throws InterruptedException {
		String direction;
		if (!command.hasSecondWord() && (command.getCommandWord().equalsIgnoreCase("go")
				|| command.getCommandWord().equalsIgnoreCase("move") || command.getCommandWord().equalsIgnoreCase("run")
				|| command.getCommandWord().equalsIgnoreCase("walk"))) {
			// if there is no second word, we don't know where to go...
			System.out.println("Where would you like to go?");
			return;
		} else if (!command.hasSecondWord()) {
			String dir = command.getCommandWord();
			if (dir.equalsIgnoreCase("n")) {
				direction = "north";
			} else if (dir.equalsIgnoreCase("s")) {
				direction = "south";
			} else if (dir.equalsIgnoreCase("w")) {
				direction = "west";
			} else if (dir.equalsIgnoreCase("e")) {
				direction = "east";
			} else if (dir.equalsIgnoreCase("d")) {
				direction = "down";
			} else if (dir.equalsIgnoreCase("u")) {
				direction = "up";
			} else {
				direction = dir;
			}

		} else {
			String dir = command.getSecondWord();
			if (dir.equalsIgnoreCase("n")) {
				direction = "north";
			} else if (dir.equalsIgnoreCase("s")) {
				direction = "south";
			} else if (dir.equalsIgnoreCase("w")) {
				direction = "west";
			} else if (dir.equalsIgnoreCase("e")) {
				direction = "east";
			} else if (dir.equalsIgnoreCase("d")) {
				direction = "down";
			} else if (dir.equalsIgnoreCase("u")) {
				direction = "up";
			} else {
				direction = dir;
			}
		}
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null) {
			System.out.println("You can't go that way!");
		} else if (currentRoom.getRoomName().equals("Alexandria Entrance") && !(inCar) && direction.equals("east")) {

			System.out.println("You're gonna WALK all the way to the forest? Bad idea.");

		} else if (currentRoom.getRoomName().equals("Front of House") && (inCar) && direction.equals("north")) {

			System.out.println("9/10 doctors recommend not driving into your house!");
		} else if (currentRoom.getRoster().hasCharacter("zombie") || currentRoom.getRoster().hasCharacter("henchman")) {
			if (currentRoom.getRoster().hasCharacter("zombie")) {
				zombie.runAway(currentRoom.getRoster().getSize(), user);
				System.out.println("You have " + user.getHealth() + " health.");
				if (user.getHealth() > 0) {
					currentRoom = nextRoom;
					if (currentRoom.isFirstTime()) {
						System.out.println(currentRoom.longDescription());
					} else {
						System.out.println(currentRoom.shortDescription());
					}
					currentRoom.removeFirstTime();
					if (currentRoom.getRoster().hasCharacter("henchman")) {
						henchman = new Henchman(currentRoom.getRoster().getSize());
						if ((((currentRoom.getRoomName().equals("House (Inside"))
								|| ((currentRoom.getRoomName().equals("Barn (Inside)")))))
								&& (Flashlight.flashLightState() == false)) {
							System.out.println("...");
						} else {
							if (currentRoom.getRoomName().equals("Outside Saviours Compound")) {
								henchman.lastPhrase();
							} else {
								henchman.randomPhrase();
							}
						}
					}
					if (currentRoom.getRoster().hasCharacter("zombie")) {
						zombie = new Zombie(currentRoom.getRoster().getSize());
						if ((((currentRoom.getRoomName().equals("House (Inside"))
								|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
								&& (Flashlight.flashLightState() == false))) {
							System.out.println("...");
						} else {
							zombie.zombiePhrase();
						}
					}
				} else {
					user.kill();
				}
			} else {
				System.out.println("The henchman shoots you.");
				user.kill();

			}
		} else {
			currentRoom = nextRoom;
			if (currentRoom.isFirstTime()) {
				System.out.println(currentRoom.longDescription());
			} else {
				System.out.println(currentRoom.shortDescription());
			}
			currentRoom.removeFirstTime();
			if (currentRoom.getRoomName().equals("Inside Saviours Compound")) {
				if (user.getInventory().hasItem("sword")) {
					gameDone = true;
				}
				Ending.ending(user);
				quit = new Quit(finished, false);
				quit.getFinished();
				finished = quit.isFinished();
			}
			if (currentRoom.getRoomName().equals("Kitchen") && !user.getInventory().hasItem("bag")) {
				user.addToInventoryCapacity(30);
				Item x = currentRoom.getInventory().getItem("bag");
				user.getInventory().addItem(x);
				currentRoom.getInventory().removeItem(x);
			}
			if (currentRoom.getRoomName().equals("Kitchen") && !user.getInventory().hasItem("knife")
					&& !user.getInventory().hasItem("pizza")) {
				Item k = currentRoom.getInventory().getItem("pizza");
				Item y = currentRoom.getInventory().getItem("knife");
				user.getInventory().addItem(k);
				user.getInventory().addItem(y);
				currentRoom.getInventory().removeItem(k);
				currentRoom.getInventory().removeItem(y);
			}
			if (currentRoom.getRoster().hasCharacter("henchman")) {
				henchman = new Henchman(currentRoom.getRoster().getSize());
				if (((currentRoom.getRoomName().equals("House (Inside)"))
						|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
						&& (Flashlight.flashLightState() == false)) {
					System.out.println("...");
				} else {
					if (currentRoom.getRoomName().equals("Outside Saviours Compound")) {
						henchman.lastPhrase();
					} else {
						henchman.randomPhrase();
					}
				}
			}
			if (currentRoom.getRoster().hasCharacter("zombie")) {
				zombie = new Zombie(currentRoom.getRoster().getSize());
				if (((currentRoom.getRoomName().equals("House (Inside)"))
						|| ((currentRoom.getRoomName().equals("Barn (Inside)"))))
						&& (Flashlight.flashLightState() == false)) {
					System.out.println("...");
				} else {
					zombie.zombiePhrase();
				}
			}
		}
	}

	private void flashlight(Command command) {
		if (user.getInventory().hasItem("flashlight") && command.getCommandWord().equals("turn")) {
			if (command.getSecondWord().equals("on")) {
				Flashlight.turnItOn(command, currentRoom);
			} else if (command.getSecondWord().equals("off")) {
				Flashlight.turnItOff(command, currentRoom);
			} else {
				System.out.println("Turn what?");
			}
		} else {
			System.out.println("You don't have anything to turn on/off.");
		}
	}

	private void takeItems(Command command, Inventory player, Inventory room) {
		if (command.hasThirdWord() && command.getThirdWord().equalsIgnoreCase("and")) {
			System.out.println("Please take one item at a time.");
			return;
		}
		Item temp;
		String secondWord = command.getSecondWord();
		if (command.getSecondWord().equalsIgnoreCase("item") || command.getSecondWord().equalsIgnoreCase("object")
				|| command.getSecondWord().equalsIgnoreCase("flashlight")) {
			temp = room.getItem("flashlight");
			secondWord = ("flashlight");
		} else {
			temp = room.getItem(command.getSecondWord());
		}
		if (command.getSecondWord().equalsIgnoreCase("ammo")) {
			if (currentRoom.getInventory().hasItem("crossbow ammo")) {
				user.addCrossbowAmmo(7);
			} else if (currentRoom.getInventory().hasItem("gun ammo")) {
				user.addGunAmmo(5);
			} else {
				System.out.println("There is no ammo here!");
			}
		}

		int x = temp.getWeight() + player.calculateWeight();
		if (room.hasItem(secondWord) && x <= user.capacity()) {
			System.out.println("Taken.");
			player.addItem(temp);
			user.addWeight(temp.getWeight());
			room.removeItem(temp);

		} else if (x > user.capacity()) {
			System.out.println("You can't carry that much!");
		} else {
			System.out.println("There is no " + secondWord + " here...");
		}
	}

	public void dropItem(Command command) {
		if (user.getInventory().hasItem(command.getSecondWord())) {
			Item x = user.getInventory().getItem(command.getSecondWord());
			currentRoom.getInventory().addItem(x);
			user.getInventory().removeItem(x);
			user.removeWeight(x.getWeight());

			System.out.println("You dropped the " + x.getName() + " here.");
		} else {
			System.out.println("You cannot drop an item that is not in your inventory!");
		}
	}

	public void setRoom(Room room) {
		currentRoom = room;
	}

}