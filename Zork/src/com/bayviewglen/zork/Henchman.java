package com.bayviewglen.zork;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

//wasn't really sure what to do for this one...
public class Henchman extends Character implements Serializable{
	private int numHenchman;
	
	public Henchman(int numHenchman){
		this.numHenchman = numHenchman;
	}
	
	public int getNumHenchman(){
		return numHenchman;
	}
	
	public void reduceNumHenchman(){
		numHenchman--;
	}
	
	public String[] instantiatePhrases = { "\"Hey! What are you doing here, kid? YOU'RE NOT SUPPOSED TO BE HERE!\"", 
			"\"Hey! You dare challenge the Saviours?\"",
			"\"Hey you! Prepare to face the might of the Saviours!\"",
			"\"An intruder! Show him no mercy!\"",
			"\"Hey you! Let's negotiate. You surrender, and I'll definitely let you live.\"" };

	public void randomPhrase() { // returns one of the phrases above - use
									// this method when a room with any number
									// of henchmen is entered.
		int phraseIndex = ThreadLocalRandom.current().nextInt(1, instantiatePhrases.length);
		if(getNumHenchman() == 1){
			System.out.println("You have approached a Saviour henchman... "
					+ "\nSaviour Henchman: "  + instantiatePhrases[phraseIndex]);
		} else if(getNumHenchman()>1){
			int henchmanNum = ThreadLocalRandom.current().nextInt(1, numHenchman);
			System.out.println("You have approached " + getNumHenchman() + " Saviour henchmen... "
					+ "\nSaviour Henchman: " + henchmanNum + ": " + instantiatePhrases[phraseIndex]);
		}
	}
	
	public void lastPhrase() { //returns the phrase for the last room with henchmen
		System.out.println(instantiatePhrases[0]);
	}

	public int henchmanDamage() {
		final int MIN_DAMAGE = 10;
		final int MAX_DAMAGE = 26;

		int damage = ThreadLocalRandom.current().nextInt(MIN_DAMAGE, MAX_DAMAGE);
		return damage;
	}

}
