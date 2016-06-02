package com.bayviewglen.zork;

import java.io.Serializable;
import java.util.Scanner;

public class Quit implements Serializable{
	private boolean finished;
	private boolean forceQuit;
	
	public Quit(boolean finished, boolean forceQuit){
		this.finished = finished;
		this.forceQuit = forceQuit;
	}
	
	public void ask(){
		Scanner input = new Scanner(System.in);
		boolean noSelection = true;
		System.out.println("Are you sure you would like to quit? "
				+ "\nBe sure to save first or all previous gaming data will be lost.");
		while(noSelection){
			System.out.print("> ");
			String selection = input.nextLine();
			if(selection.equalsIgnoreCase("y") || selection.equalsIgnoreCase("yes")){
				noSelection = false;
				getFinished();
			} else if (selection.equalsIgnoreCase("n") || selection.equalsIgnoreCase("no")){
				noSelection = false;
				getNotFinished();
				System.out.println("What would you like to do?");
			} else {
				System.out.println("\nPlease say if you would like to quit: \"Yes (y)\" or \"No (n)\".");
			}
		}
	}
	
	public void getFinished(){
		finished = true;
	}
	
	public void getNotFinished(){
		finished = false;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public boolean gameNotComplete(){
		return forceQuit;
	}
}
