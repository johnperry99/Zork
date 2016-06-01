package com.bayviewglen.zork;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Assault {

	public static int maxPercent = 100;
	public static int gunHitRate = 100;
	public static int knifeHitRate;
	public static int swordHitRate;
	public static int crossbowHitRate;

	// this method is assuming that the player has already typed "attack zombie
	// with ___"
	public static void attackZombie(Room currentRoom, Player user, Command command, Zombie zomb)
			throws InterruptedException {
		Character x = currentRoom.getRoster().getCharacterString("zombie");
		Zombie zombie = zomb;

		if (currentRoom.getRoster().hasCharacter(x)) {
			if (command.getFourthWord().equalsIgnoreCase("knife")) {
				if (user.getInventory().hasItem("knife")) {
					knifeHitRate = 35;
					int sucessHit = ThreadLocalRandom.current().nextInt(0, maxPercent);
					if (sucessHit <= knifeHitRate) {
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You killed the zombie.");
						zombie.reduceNumZombies();
						if (zombie.getNumZombies() == 0) {
							zombie.kill();
							System.out.println("You have killed all the zombies in the area.");
						} else {
							System.out.println("There are/is " + zombie.getNumZombies() + " left.");
						}
					} else {
						int damageDone = zomb.zombieDamageAttack();
						user.removeHealth(damageDone);
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You missed and the zombie dealt " + damageDone
								+ " damage before you finally killed it.");
						System.out.println("Your current health is :  " + user.getHealth());
						zombie.reduceNumZombies();
						if (zombie.getNumZombies() == 0) {
							zombie.kill();
							System.out.println("You have killed all the zombies in the area.");
						} else {
							System.out.println("There are/is " + zombie.getNumZombies() + " left.");
						}
					}
				} else {
					System.out.println("You don't have a knife...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("sword")) {
				if (user.getInventory().hasItem("sword")) {
					swordHitRate = 50;
					int sucessHit = ThreadLocalRandom.current().nextInt(0, maxPercent);
					if (sucessHit <= swordHitRate) {
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You killed the zombie.");
						zombie.reduceNumZombies();
						if (zombie.getNumZombies() == 0) {
							zombie.kill();
							System.out.println("You have killed all the zombies in the area.");
						} else {
							System.out.println("There are/is " + zombie.getNumZombies() + " left.");
						}
					} else {
						int damageDone = zomb.zombieDamageAttack();
						user.removeHealth(damageDone);
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You missed and the zombie dealt " + damageDone
								+ " damage before you finally killed it.");
						System.out.println("Your current health is:  " + user.getHealth());
						zombie.reduceNumZombies();
						if (zombie.getNumZombies() == 0) {
							zombie.kill();
						} else {
							System.out.println("There are/is " + zombie.getNumZombies() + " left.");
						}
					}
				} else {
					System.out.println("You don't have a sword...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("crossbow")) {
				if (user.getInventory().hasItem("crossbow") && user.getCrossbowAmmo() > 0) {
					currentRoom.getRoster().removeCharacter(x);
					System.out.println("You killed the zombie.");
					System.out.println("You have " + user.getCrossbowAmmo() + " arrows left");
					user.removeCrossbowAmmo(1);
					zombie.reduceNumZombies();
					if (zombie.getNumZombies() == 0) {
						zombie.kill();
						System.out.println("You have killed all the zombies in the area.");
					} else {
						System.out.println("There are/is " + zombie.getNumZombies() + " left.");
					}
					
				} else if(user.getCrossbowAmmo() <=0 && user.hasItem("crossbow")){
					System.out.println("You're out of ammo!");
				}else {
					System.out.println("You don't have a crossbow...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("gun")
					|| command.getFourthWord().equalsIgnoreCase("handgun")) {
				if (user.getInventory().hasItem("gun") && user.getGunAmmo() > 0) {
					currentRoom.getRoster().removeCharacter(x);
					user.removeGunAmmo(1);
					System.out.println("You killed the zombie.");
					System.out.println("You have " + user.getGunAmmo() + " bullets left");
					zombie.reduceNumZombies();
					if (zombie.getNumZombies() == 0) {
						zombie.kill();
						System.out.println("You have killed all the zombies in the area.");
					} else {
						System.out.println("There are/is " + zombie.getNumZombies() + " left.");
					}
				} else if (user.getGunAmmo() <= 0 && user.hasItem("crossbow")) {
					System.out.println("You're out of ammo!");
				} else {
					System.out.println("You don't have a gun...");
				}
			}
		} else {
			System.out.println("There are no zombies here...");
		}
	}

	// this method is assuming that the player has already typed "attack
	// henchman with ___"
	public static void attackHenchman(Room currentRoom, Player user, Command command, Henchman hench) {
		Character x = currentRoom.getRoster().getCharacterString("henchman");
		Henchman henchman = hench;

		if (currentRoom.getRoster().hasCharacter(x)) {
			if (command.getFourthWord().equalsIgnoreCase("knife")) {
				if (user.getInventory().hasItem("knife")) {
					knifeHitRate = 30;
					int sucessHit = ThreadLocalRandom.current().nextInt(0, maxPercent);
					if (sucessHit <= knifeHitRate) {
						int damageDone = henchman.henchmanDamage();
						user.removeHealth(damageDone);
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You killed the henchman, but took " + damageDone + " damage.");
						System.out.println("Your current health is:  " + user.getHealth());
						henchman.reduceNumHenchman();
						if (henchman.getNumHenchman() == 0) {
							henchman.kill();
							System.out.println("You have killed all the saviours in the area.");
						} else {
							System.out.println("There are/is " + henchman.getNumHenchman() + " left.");
						}
					} else {
						System.out.println("You missed and the henchman killed you.");
						user.kill();
					}
				} else {
					System.out.println("You don't have a knife...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("sword")) {
				if (user.getInventory().hasItem("sword")) {
					swordHitRate = 50;
					int sucessHit = ThreadLocalRandom.current().nextInt(0, maxPercent);
					if (sucessHit <= swordHitRate) {
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You killed the henchman.");
						henchman.reduceNumHenchman();
						if (henchman.getNumHenchman() == 0) {
							henchman.kill();
							System.out.println("You have killed all the saviours in the area.");
						} else {
							System.out.println("There are/is " + henchman.getNumHenchman() + " left.");
						}
					} else {
						System.out.println("You missed and the henchman killed you.");
						user.kill();
					}
				} else {
					System.out.println("You don't have a sword...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("crossbow")) {
				if (user.getInventory().hasItem("crossbow")) {
					crossbowHitRate = 60;
					int sucessHit = ThreadLocalRandom.current().nextInt(0, maxPercent);
					if (sucessHit <= crossbowHitRate) {
						currentRoom.getRoster().removeCharacter(x);
						System.out.println("You killed the henchman.");
						System.out.println("You have " + user.getCrossbowAmmo() + " arrows left");
						user.removeCrossbowAmmo(1);
						henchman.reduceNumHenchman();
						if (henchman.getNumHenchman() == 0) {
							henchman.kill();
							System.out.println("You have killed all the saviours in the area.");
						} else {
							System.out.println("There are/is " + henchman.getNumHenchman() + " left.");
						}
					} else {
						int damageDone = henchman.henchmanDamage();
						currentRoom.getRoster().removeCharacter(x);
						System.out.println(
								"You missed and the henchman did " + damageDone + " damage before you could kill him.");
						user.removeHealth(damageDone);
						System.out.println("Your current health is:  " + user.getHealth());
						henchman.reduceNumHenchman();
						if (henchman.getNumHenchman() == 0) {
							henchman.kill();
							System.out.println("You have killed all the saviours in the area.");
						} else {
							System.out.println("There are/is " + henchman.getNumHenchman() + " left.");
						}
					}
				} else {
					System.out.println("You don't have a crossbow...");
				}
			} else if (command.getFourthWord().equalsIgnoreCase("gun")
					|| command.getFourthWord().equalsIgnoreCase("handgun")) {
				if (user.getInventory().hasItem("gun")) {
					currentRoom.getRoster().removeCharacter(x);
					System.out.println("You killed the henchman");
					System.out.println("You have " + user.getGunAmmo() + " bullets left");
					henchman.reduceNumHenchman();
					user.removeGunAmmo(1);
					if (henchman.getNumHenchman() == 0) {
						henchman.kill();
						System.out.println("You have killed all the saviours in the area.");
					} else {
						System.out.println("There are/is " + henchman.getNumHenchman() + " left.");
					}
				} else {
					System.out.println("You don't have a gun...");
				}
			} else {
				System.out.println("You can't attack the henchman with that!");
			}
		} else {
			System.out.println("There are no henchmen here...");
		}
	}

}
