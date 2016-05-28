package com.bayviewglen.zork;

import java.util.Scanner;

public class Ending {
	static boolean playing = true;
	Scanner input = new Scanner(System.in);

	public void ending() {
		while (playing) {
			System.out.println("As you gaze around the massive, maze-like compound, you once again "
					+ "\nacknowledge the great difficulty of this task. You immediately push the thought aside. It's your wife "
					+ "\nMaggie they have captured and you must save her. Enter just the number of one of the options "
					+ "\nto make a decision:");
			firstChoice();
			

		}
	}

	private void firstChoice() {
		int choice = 0;
		System.out.println(
				"After waiting a bit, you decide it's safe to go and enter a room. Upon entering the room, you see "
				+ "\na saviour sitting nearby, but he is looking the other direction.");
		System.out.println("1. Hide under the table | 2. Attack him | 3. Try to run past him.");
		getChoice(3);
		if(choice == 1){
			System.out.println("The saviour sees you, you have been caught. \nGAME OVER");
		}else if(choice == 2){
			System.out.println("You successfully kill the saviour before he sounds the alarm.  "
					+ "\nYou exit the room and see another room to the north that you wisely decide not to enter.");
			secondChoice();
		}else{
			System.out.println("The saviour sees you before you get halfway across the room and shoots you.\nGAME OVER");
		}
		
	}

	private void secondChoice() {
		int choice = 0;
		System.out.println("1. Take the East corridor | Take the South corridor");

	}

	private void thirdChoice() {
		// TODO Auto-generated method stub

	}

	private void fourthChoice() {
		// TODO Auto-generated method stub

	}

	private void theShowdown() {
		// TODO Auto-generated method stub

	}

	private int getChoice(int numberOptions) {
		int choice = 0;
		boolean validOption = false;
		System.out.println("What do you choose to do? Enter the number coressponding with your option:");
		while (!validOption) {
			try {
				choice = input.nextInt();
				if(choice > numberOptions || choice <= 0){
					System.out.println("Not a valid option!");
				}else{
					validOption = true;
				}
			
			} catch (Exception ex) {
				System.out.println("Not a valid option!");
			}
		}
		return choice;
	}
}
