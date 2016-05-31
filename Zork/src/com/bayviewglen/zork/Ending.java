package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Scanner;

public class Ending {
	static boolean playing = true;
	static Scanner input = new Scanner(System.in);
	private static Inventory originalInv;
	public static void ending(Player user) throws InterruptedException {
		originalInv = user.getInventory();
		while (playing) {
			System.out.println("\nAs you gaze around the massive, maze-like compound, you once again "
					+ "\nacknowledge the great difficulty of this task. You immediately push the thought aside."
					+ "\nIt's your wife Maggie they have captured and you must save her:");
			while (playing) {
				firstChoice(user);
			}
			Thread.sleep(2000);

		}
	}

	private static void firstChoice(Player user) throws InterruptedException {
		int choice = 0;
		System.out.println(
				"\nAfter waiting a bit, you decide it's safe to go and enter a room. Upon entering the room, you see "
						+ "\na saviour sitting nearby, but he is looking the other direction.\n");
		System.out.println("1. Hide under the table | 2. Attack him | 3. Try to run past him.");
		choice = getChoice(3);
		if (choice == 1) {
			System.out.println("The saviour turns around and easily sees you. You have been caught. \nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nStarting from previous checkpoint.");
			Thread.sleep(2000);
		} else if (choice == 2) {
			System.out.println("You successfully kill the saviour before he sounds the alarm.  "
					+ "\nYou exit the room and see another room to the north that you wisely decide not to enter.");
			secondChoice(user);
		} else {
			System.out.println(
					"The saviour notices you before you get halfway across the room and shoots you.\nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nStarting from previous checkpoint.");
			Thread.sleep(2000);
		}

	}

	private static void secondChoice(Player user) throws InterruptedException {
		int choice = 0;
		System.out.println("\n1. Take the East corridor | 2. Take the South corridor");
		choice = getChoice(2);
		if (choice == 1) {
			System.out.println(
					"You continue along the corridor and approach a room full of saviours. You have been caught.\nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nStarting from previous checkpoint.");
			Thread.sleep(2000);
		} else {
			thirdChoice(user);
		}

	}

	private static void thirdChoice(Player user) throws InterruptedException {
		int choice = 0;
		System.out.println("You continue south, then turn east once more until you can turn again.");
		System.out.println("\n1. Continue going East | 2. Take the South corridor");
		choice = getChoice(2);
		if (choice == 1) {
			fourthChoice(user);
		} else {
			System.out.println("You turn to the south corridor and take a few twists and turns before encountering a "
					+ "\nroom full of saviours. You have been caught.\nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nStarting from previous checkpoint.");
			Thread.sleep(2000);
		}

	}

	private static void fourthChoice(Player user) throws InterruptedException {
		int choice = 0;
		System.out.println("You continue along the corridor, then turn south before reaching another intersection of "
				+ "corridors.");
		System.out.println("\n1. Take the East corridor | 2. Continue going South");
		choice = getChoice(2);
		if (choice == 1) {
			theShowdownPartOne(user);
		} else {
			System.out.println(
					"You continue along the corridor and approach a room full of saviours. You have been caught.\nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nStarting from previous checkpoint.");
			Thread.sleep(2000);
		}

	}

	private static void theShowdownPartOne(Player user) throws InterruptedException {
		String choice;
		
		System.out.println("\nYou continue going east and enter a room with a large man. He has a baseball bat wrapped "
				+ "\nin barbed wire in one hand and a large assault rifle in the other. ");
		Thread.sleep(1000);
		System.out.println("\nYou realize with horror, this is Negan, the big boss of the Saviours.");
		Thread.sleep(1000);
		System.out.println("\nHe looks at you and says: " + "\n\"Who the hell are you?\" "
				+ "\nYou weakly reply: \n\"My name is Glenn. You kidnapped my wife, and I'm going to take her back.\""
				+ "\nHe laughs deeply and says: " + "\n\"And how do you suppose you're gonna do that?\"");
		ArrayList<String> availableWeapons = possibleChoice(user);
		Thread.sleep(3000);
		System.out.println("\nThere's no mercy for this guy. You decide to attack him.");
		choice = getChoice(user, availableWeapons);
		if (choice.equals("crossbow") || choice.equals("gun")) {
			System.out.println("\nYou hit him in the shoulder, wounding him."
					+ "\nHe seems a bit mad now... It kinda sucks that the weapon you just used is out of ammo...");
			user.getInventory().removeItem(choice);
			theShowDownPartTwo(user, originalInv);
		} else {
			System.out.println("\nHe evades your pathetic attempt and kills you.\nGAME OVER");
			Thread.sleep(3000);
			System.out.println("\nResuming from last checkpoint");
			Thread.sleep(2000);
			user.getInventory().setInventory(originalInv);
		}

	}

	private static void theShowDownPartTwo(Player user, Inventory originalInv) throws InterruptedException {
		String choice;
		ArrayList<String> availableWeapons = possibleChoice(user);
		Thread.sleep(3000);
		System.out.println("\nYou have to attack him again!");
		choice = getChoice(user, availableWeapons);
		if (choice.equals("sword")) {
			System.out.println("\nYou stab him in the stomach. He tumbles over and smacks his head against a table...\n"
					+ "Now barely concious on the floor.");
			user.getInventory().removeItem(choice);
			theShowDownPartThree(user, originalInv);
		} else {
			System.out.println("\nEven with the shoulder injury he still manages to evade your attack."
					+ "\nyou hear a loud bang...complete darkness."
					+ "\nWhen you wake up he has disappeared and Maggie is nowhere to be found.\nGAME OVER");
			user.getInventory().setInventory(originalInv);
			Thread.sleep(3000);
			System.out.println("Resuming from last checkpoint");
			Thread.sleep(2000);
		}

	}

	private static void theShowDownPartThree(Player user, Inventory originalInv) throws InterruptedException {
		int choice;
		Thread.sleep(2000);
		System.out.println("\n1. Spare him | 2. Finish Him");
		choice = getChoice(2);
		if (choice == 1) {
			System.out.println("\nYou decide that sparing him will teach him a lesson in messing with you.\n"
					+ "That day you prove that being human is still possible.");
			playing = false;

		} else {
			System.out.println("\nYou dangle the knife by your side. \n\"This is for all of the lives you've ruined. "
					+ "\nAnd now I want you to die knowing you failed to ruin mine.\"");
			playing = false;

		}

	}

	private static int getChoice(int numberOptions) {
		int choice = 0;
		boolean validOption = false;
		System.out.println("What do you choose to do? Enter the number coresponding to your option:");
		System.out.print("> ");
		while (!validOption) {
			try {
				choice = Integer.parseInt(input.nextLine());
				if (choice > numberOptions || choice <= 0) {
					System.out.println("Not a valid option!");
				} else {
					validOption = true;
				}

			} catch (Exception ex) {
				System.out.println("Not a valid option!");
			}
		}
		return choice;
	}

	private static ArrayList<String> possibleChoice(Player user) {
		ArrayList<String> possibilities = new ArrayList<String>();
		if (user.getInventory().hasItem("knife")) {
			possibilities.add("knife");
		}
		if (user.getInventory().hasItem("sword")) {
			possibilities.add("sword");
		}
		if (user.getInventory().hasItem("gun")) {
			possibilities.add("gun");
		}
		if (user.getInventory().hasItem("crossbow")) {
			possibilities.add("crossbow");
		}
		return possibilities;
	}

	private static String getChoice(Player user, ArrayList<String> x) {
		boolean valid = false;
		System.out.println("Please enter your selection:");
		for (String s : x) {
			System.out.println(" " + s);
		}
		System.out.print("> ");
		while (!valid) {
			String choice = input.nextLine();
			if (choice.equalsIgnoreCase("sword") && user.getInventory().hasItem("sword")) {
				valid = true;
				return "sword";
			} else if (choice.equalsIgnoreCase("knife") && user.getInventory().hasItem("knife")) {
				valid = true;
				return "knife";
			} else if (choice.equalsIgnoreCase("crossbow") && user.getInventory().hasItem("crossbow")) {
				valid = true;
				return "crossbow";
			} else if (choice.equalsIgnoreCase("gun") && user.getInventory().hasItem("gun")) {
				valid = true;
				return "gun";
			} else {
				System.out.println("Not a valid option!");
			}
		}
		return null;

	}
}
