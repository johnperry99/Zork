package com.bayviewglen.zork;

import java.util.concurrent.ThreadLocalRandom;

//wasn't really sure what to do for this one...
public class Henchman extends Character {
	public String[] instantiatePhrases = { "Hey! What are you doing here, kid?", "Hey! Who's that??", "I see someone!",
			"An intruder! Get him!", "Someone's here!", "We've been infiltrated!",
			"Hey! You dare challenge the Saviours?", "Hey! Prepare to face the might of the Saviours!", "I see you!",
			"An intruder! Show him no mercy!",
			"Hey you! Let's negotiate. You concede, and I'll definitely let you live." };

	public String randomPhrase() { // returns one of the phrases above - use
									// this method when a room with any number
									// of henchmen is entered.
		int phraseIndex = ThreadLocalRandom.current().nextInt(0, instantiatePhrases.length);
		return instantiatePhrases[phraseIndex];
	}

	public int henchmenDamage() {
		final int MIN_DAMAGE = 6;
		final int MAX_DAMAGE = 30;

		int damage = ThreadLocalRandom.current().nextInt(MIN_DAMAGE, MAX_DAMAGE);
		return damage;
	}

}
