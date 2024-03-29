package com.bayviewglen.zork;
/*
 * Author:  Michael Kolling
 * Version: 1.0
 * Date:    July 1999
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * This parser reads user input and tries to interpret it as a "Zork"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.StringTokenizer;

class Parser implements Serializable{

	private CommandWords commands; // holds all valid command words

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand() {
		String inputLine = ""; // will hold the full input line
		String word1;
		String word2;
		String word3;
		String word4;

		System.out.print("> "); // print prompt

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine();
		} catch (java.io.IOException exc) {
			System.out.println("There was an error during reading: " + exc.getMessage());
		}

		StringTokenizer tokenizer = new StringTokenizer(inputLine);

		if (tokenizer.hasMoreTokens())
			word1 = tokenizer.nextToken(); // get first word
		else
			word1 = null;
		if (tokenizer.hasMoreTokens())
			word2 = tokenizer.nextToken(); // get second word
		else
			word2 = null;
		if (tokenizer.hasMoreTokens()) // get third word
			word3 = tokenizer.nextToken();
		else
			word3 = null;
		if (tokenizer.hasMoreTokens()) // get fourth word
			word4 = tokenizer.nextToken();
		else
			word4 = null;

		// note: we just ignore the rest of the input line.

		// Now check whether this word is known. If so, create a command
		// with it. If not, create a "nil" command (for unknown command).

		if (commands.isCommand(word1) && commands.isNoun(word2) && commands.isNoun(word4))
			return new Command(word1, word2, word3, word4);
		else if (commands.isCommand(word1) && commands.isCommand(word2) && commands.isNoun(word3))
			return new Command(word1, word2, word3, null);
		else if (commands.isCommand(word1) && commands.isCommand(word2))
			return new Command(word1, word2, null, null);
		else if (commands.isCommand(word1) && commands.isNoun(word2))
			return new Command(word1, word2, null, null);	
		else
			return new Command(word1, word2, word3, word4);
	}

	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		commands.showAll();
	}
}