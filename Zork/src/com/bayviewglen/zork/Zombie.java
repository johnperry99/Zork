package com.bayviewglen.zork;

import java.util.concurrent.ThreadLocalRandom;

public class Zombie {

	public int zombieMovement() {
		final int MIN_MOVEMENT_ZOMBIE = 2;
		final int MAX_MOVEMENT_ZOMBIE = 4;

		int zRandMovement = ThreadLocalRandom.current().nextInt(MIN_MOVEMENT_ZOMBIE, MAX_MOVEMENT_ZOMBIE);
		return zRandMovement;
	}

	public int zombieDamage() throws InterruptedException {
		final int MIN_DAMAGE = 15;
		final int MAX_DAMAGE = 20;
		boolean takeDamage = runAway(0, 0, 4);

		if (takeDamage) {
			int damageTaken = ThreadLocalRandom.current().nextInt(MIN_DAMAGE, MAX_DAMAGE);
			Thread.sleep(500);
			System.out.println("The zombies caught up with you and attacked, dealing " + damageTaken + " damage");
			return damageTaken;
		} else {
			return 0;
		}
	}

	public int playerMovement() {
		final int MIN_MOVEMENT_PLAYER = 1;
		final int MAX_MOVEMENT_PLAYER = 6;

		int pRandMovement = ThreadLocalRandom.current().nextInt(MIN_MOVEMENT_PLAYER, MAX_MOVEMENT_PLAYER);
		return pRandMovement;
	}

	public boolean runAway(int zombieMovement, int playerMovement, int numZomb) throws InterruptedException {
		int raceLength = 30;
		int totalPMovement = 0;
		boolean result = false;
		int[] totalZMovement = new int[numZomb];
		String[] raceParticipants = new String[numZomb + 1];

		for (int i = 0; i < raceParticipants.length; i++) {
			if (i == 0) {
				raceParticipants[i] = "YOU";
			} else {
				raceParticipants[i] = "Zombie #" + i;
			}
		}

		for (int i = 0; i < numZomb; i++) {
			totalZMovement[i] = 0;
		}

		for (int i = 0; i <= raceLength; i++) {

			for (int j = 0; j < numZomb + 1; j++) {
				System.out.println("--------------------------------------------------------------------------");
				int position = totalPMovement + 1;
				System.out.printf("%-20s %" + position + "d %n", raceParticipants[j], (j + 1));
				System.out.println("--------------------------------------------------------------------------");
			}
			System.out.println("\n");
			for (int k = 0; k < raceParticipants.length; k++) {
				totalPMovement += playerMovement;
				totalZMovement[k] += zombieMovement;
				Thread.sleep(30);
				if (totalPMovement >= raceLength) {
					for (int l = 0; l < raceParticipants.length; l++) {
						System.out
								.println("--------------------------------------------------------------------------");
						int position = totalPMovement + 1;
						System.out.printf("%-20s %" + position + "d %n", raceParticipants[l], (l + 1));
						System.out
								.println("--------------------------------------------------------------------------");
					}
					result = false;
					Thread.sleep(500);
					System.out.println("You escaped from the zombies without harm");
				} else if (totalZMovement[k] >= raceLength) {
					for (int l = 1; l < raceParticipants.length; l++) {
						System.out
								.println("--------------------------------------------------------------------------");
						int position = totalZMovement[l] + 1;
						System.out.printf("%-20s %" + position + "d %n", raceParticipants[l], (l + 1));
						System.out
								.println("--------------------------------------------------------------------------");
					}
					result = true;
					// prints out stuff in zombie damage so it can say "You took
					// ___ damage"
				}
			}
		}
		return result;
	}

}
