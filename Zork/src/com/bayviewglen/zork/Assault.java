package com.bayviewglen.zork;
public abstract class Assault {
       
       public static int maxPercent = 100;
       public static int gunHitRate = 100;
       public static int knifeHitRate;
       public static int swordHitRate;
       public static int crossbowHitRate;
 
       //this method is assuming that the player has already typed "attack zombie with ___"
       public static void attackZombie(Room currentRoom, Player user, Command command) throws InterruptedException {
             Character x = currentRoom.getRoster().getCharacterString("zombie");
             if (currentRoom.getRoster().hasCharacter(x)) {
                    if (command.getFourthWord() == "knife") {
                           if (user.getInventory().hasItem("knife")) {
                                 knifeHitRate = 40;
                                 int sucessHit = (int) Math.random() * maxPercent;
                                 if (sucessHit <= knifeHitRate) {
                                        currentRoom.getRoster().removeCharacter(x);
                                        System.out.println("You killed the zombie.");
                                 }else {
                                        int damageDone = ((Zombie) x).zombieDamage();
                                        user.removeHealth(damageDone);
                                        System.out.println("You missed and the zombie dealt " + damageDone + " damage");
                                 }
                           }else {
                                 System.out.println("You don't have a knife...");
                                 //should you take damage here if you attack with something you don't have?
                                 System.out.println("What would you like to do now?");
                           }
                    }else if (command.getFourthWord() == "sword") {
                           if (user.getInventory().hasItem("sword")) {
                                 swordHitRate = 60;
                                 int sucessHit = (int) Math.random() * maxPercent;
                                 if (sucessHit <= swordHitRate) {
                                        currentRoom.getRoster().removeCharacter(x);
                                        System.out.println("You killed the zombie.");
                                 }else {
                                        int damageDone = ((Zombie) x).zombieDamage();
                                        user.removeHealth(damageDone);
                                        System.out.println("You missed and the zombie dealt " + damageDone + " damage");
                                 }
                           }else {
                                 System.out.println("You don't have a sword...");
                                 //see last comment
                                 System.out.println("What would you like to do now?");
                           }
                    }else if (command.getFourthWord() == "crossbow") {
                           if (user.getInventory().hasItem("crossbow")) {
                                 currentRoom.getRoster().removeCharacter(x);
                                 System.out.println("You killed the zombie.");
                                 }else {
                                 System.out.println("You don't have a crossbow...");
                                 //see last comment
                                 System.out.println("What would you like to do now?");
                           }
                    }else if (command.getFourthWord() == "gun" || command.getFourthWord() == "handgun") {
                           if (user.getInventory().hasItem("gun")) {
                                 currentRoom.getRoster().removeCharacter(x);
                                 System.out.println("You killed the zombie.");
                                 }else {
                                 System.out.println("You don't have a gun...");
                                 //see last comment
                                 System.out.println("What would you like to do now?");
                           }
                    }
             }else {
                    System.out.println("There are no zombies here...");
             }
       }
       
       //this method is assuming that the player has already typed "attack henchman with ___"
       public static void attackHenchman(Room currentRoom, Player user, Command command) {
             Character x = currentRoom.getRoster().getCharacterString("henchman");
             if (currentRoom.getRoster().hasCharacter(x)) {
                    if (command.getFourthWord().equalsIgnoreCase("knife")) {
                           if (user.getInventory().hasItem("knife")) {
                                 knifeHitRate = 30;
                                 int sucessHit = (int) Math.random() * maxPercent;
                                 if (sucessHit <= knifeHitRate) {
                                        int damageDone = ((Henchman) x).henchmenDamage();
                                        user.removeHealth(damageDone);
                                        currentRoom.getRoster().removeCharacter(x);
                                        System.out.println("You killed the henchman, but took " + damageDone + " damage.");
                                        }else {
                                        user.kill();
                                        System.out.println("You missed and the henchman killed you.");
                                 }
                           }else {
                                 System.out.println("You don't have a knife...");
                                 //should you die if you attack with weapon but don't have that weapon?
                                 System.out.println("What would you like to do now?");
                           }
                    }else if (command.getFourthWord().equalsIgnoreCase("sword")) {
                           if (user.getInventory().hasItem("sword")) {
                                 swordHitRate = 50;
                                 int sucessHit = (int) Math.random() * maxPercent;
                                 if (sucessHit <= swordHitRate) {
                                        currentRoom.getRoster().removeCharacter(x);
                                        System.out.println("You killed the henchman.");
                                        }else {
                                        user.kill();
                                        System.out.println("You missed and the henchman killed you.");
                                 }
                    }else {
                           System.out.println("You don't have a sword...");
                           System.out.println("What would you like to do now?");
                    }
                    }else if (command.getFourthWord().equalsIgnoreCase("crossbow")) {
                           if (user.getInventory().hasItem("crossbow")) {
                                 crossbowHitRate = 70;
                                 int sucessHit = (int) Math.random() * maxPercent;
                                 if (sucessHit <= crossbowHitRate) {
                                        currentRoom.getRoster().removeCharacter(x);
                                        System.out.println("You killed the henchman.");
                                        }else {
                                        int damageDone = ((Henchman) x).henchmenDamage();
                                        user.removeHealth(damageDone);
                                        System.out.println("You missed and the henchman did " + damageDone + " damage.");
                                 }
                           }else {
                                 System.out.println("You don't have a crossbow...");
                                 System.out.println("What would you like to do now?");
                           }
                    }else if (command.getFourthWord().equalsIgnoreCase("gun") || command.getFourthWord().equalsIgnoreCase("handgun")) {
                           if (user.getInventory().hasItem("gun")) {
                                 currentRoom.getRoster().removeCharacter(x);
                                 System.out.println("You killed the henchman");
                           }
                    }else {
                           System.out.println("You can't attack the henchman with that!");
                           System.out.println("What do you want to do now?");
                    }
             }else {
                    System.out.println("There are no henchmen here...");
             }
       }
       
 
}
