package com.bayviewglen.zork;

import java.util.Scanner;

public class Quit {
	private boolean finished;
	private boolean forceQuit;
	
	public Quit(boolean finished, boolean forceQuit){
		this.finished = finished;
		this.forceQuit = forceQuit;
	}
	
	public void ask(){
		Scanner input = new Scanner(System.in);
		boolean noSelection = true;
		while(noSelection){
			System.out.println("Are you sure you would like to quit? All previous gaming data will be lost.");
			String selection = input.nextLine();
			if(selection.equalsIgnoreCase("y") || selection.equalsIgnoreCase("yes")){
				noSelection = false;
				getFinished();
			} else if (selection.equalsIgnoreCase("n") || selection.equalsIgnoreCase("no")){
				noSelection = false;
				getNotFinished();
			} else {
				System.out.println("Please enter \"Yes\" or \"No\".");
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
