package it.unisa.guessthecover;

public class User {
	private String name;
	private int point;
	public User(String n, int p){
		name=n;
		point=p;
	}
	public String getName(){return name;}
	public int getPoint(){return point;}
}
