package com.bayviewglen.zork;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Zork implements Serializable{

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		Game game = new Game();
		game.play();

	}

}