package com.bayviewglen.zork;

import java.util.ArrayList;

public class CharacterRoster {
	private ArrayList<Character>roster;
	
	public CharacterRoster(ArrayList<Character> list) {
		super();
		roster = list;
	}
	public CharacterRoster(){
		super();
		roster = new ArrayList<Character>();
	}



	public ArrayList<Character> getRoster() {
		return roster;
	}

	public void setRoster(ArrayList<Character> roster) {
		this.roster = roster;
	}
	public String toString() {
		String characters = "";
		for (Character s : roster) {
			characters += " " + s.getName() +"\n";
		}
		return "Characters:\n" + characters;
	}
	public void addCharacter(Character x){
		roster.add(x);
	}
	public void removeCharacter(Character x){
		roster.remove(x);
	}
	public boolean hasCharacter(String x){
		for(Character s: roster){
			if(s.getName().equalsIgnoreCase(x)){
				return true;
			}
		}
		return false;
	}
	public boolean hasCharacter(Character x){
		if(roster.indexOf(x) != -1){
			return true;
		}else{
			return false;
		}
	}
	public Character getCharacterString(String x){
		for(Character s: roster){
			if(s.getName().equalsIgnoreCase(x)){
				return s;
			}
		}
		return null;
	}
	public Character getCharacter(Character x){
		int k = roster.indexOf(x);
		return roster.get(k);
	}
	
	public int getSize(){
		return roster.size();
	}
}
