package com.bayviewglen.zork;

import java.util.concurrent.ThreadLocalRandom;

public class Zombie extends Character {
	private int numZombies;

	public Zombie(int numZombies) { // constructs the zombie class
		this.numZombies = numZombies;
	}

	public int getNumZombies() { // returns current number of zombies
		return numZombies;
	}

	public int zombieMovement() { // returns number of steps each zombie takes
									// per round
		final int MIN_MOVEMENT_ZOMBIE = 2;
		final int MAX_MOVEMENT_ZOMBIE = 4;

		int zRandMovement = ThreadLocalRandom.current().nextInt(MIN_MOVEMENT_ZOMBIE, MAX_MOVEMENT_ZOMBIE);
		return zRandMovement;
	}

	public int zombieDamage() throws InterruptedException { // returns amount of
															// dmg dealt by
															// zombie(s)
		final int MIN_DAMAGE = 10;
		final int MAX_DAMAGE = 21;


		
			int damageTaken = ThreadLocalRandom.current().nextInt(MIN_DAMAGE, MAX_DAMAGE);
			Thread.sleep(500);
			System.out.println("A zombie caught up with you and attacked, dealing " + damageTaken
					+ " damage before you could escape.");
			return damageTaken;
		
	}

	public void reduceNumZombies() {
		numZombies--;
	}

	public int zombieDamageAttack() {
		final int MIN_DAMAGE = 10;
		final int MAX_DAMAGE = 15;
		int damageTaken = ThreadLocalRandom.current().nextInt(MIN_DAMAGE, MAX_DAMAGE);
		return damageTaken;
	}

	public int playerMovement() { // returns number of steps the player takes
									// per round
		final int MIN_MOVEMENT_PLAYER = 1;
		final int MAX_MOVEMENT_PLAYER = 5;

		int pRandMovement = ThreadLocalRandom.current().nextInt(MIN_MOVEMENT_PLAYER, MAX_MOVEMENT_PLAYER);
		return pRandMovement;
	}

	// returns false if you escaped and true if you didn't
	public boolean runAway(int numZomb, Player user) throws InterruptedException {
		String[] names = new String[numZombies + 1];
		boolean isWinner = false;
		String winner = null;
		int[] placeInRace = new int[numZombies + 1];
		for (int i = 0; i < placeInRace.length; i++) {
			placeInRace[i] = 0;
		}
		for (int i = 0; i < names.length; i++) {
			if (i == 0) {
				names[i] = "YOU";
			} else {
				names[i] = "Zombie " + i;
			}
		}

		System.out.println("\n\tRUN!!!");
		try {
			Thread.sleep(700);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		while (!isWinner) {
			System.out.print(
					"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			for (int j = 0; j < placeInRace.length; j++) {
				int x = placeInRace[j] + 1;
				System.out.printf("%-30s|%" + x + "s%n", names[j], "x");
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------------");
			}

			for (int j = 0; j < names.length; j++) {
				if (j == 0) {
					placeInRace[j] += playerMovement();
				} else {
					placeInRace[j] += zombieMovement();
				}
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (placeInRace[j] >= 100) {
					winner = names[j];
					placeInRace[j] = 100;
					System.out.print(
							"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					for (int k = 0; k < placeInRace.length; k++) {
						int x = placeInRace[k] + 1;
						System.out.printf("%-30s|%" + x + "d%n", names[k], placeInRace[k]);
						System.out.println(
								"------------------------------------------------------------------------------------------------------------------------------------");
					}
						if (winner.equalsIgnoreCase("YOU")) {
							System.out.println("You got away!");
							return false;
						} else {
							user.removeHealth(zombieDamage());
							return true;
						}
					

				}
			}

		}
		return true;

	}

	public void zombiePhrase() {
		if (getNumZombies() == 1) {
			System.out.println("There is a zombie nearby, all alone...poor zombie. #ForeverAlone");
		} else if (getNumZombies() > 1) {
			System.out.println("There are " + getNumZombies() + " zombies nearby. Watch out!");
		}

	}

}
