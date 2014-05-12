package it.unisa.guessthecover;

public class Category {
	private String name;
	private int image;
	
	public Category (String n,int fantasy){
		name=n;
		image=fantasy;
	}
	public String getName() {return name;}
	public int getImage() {return image;}
}
