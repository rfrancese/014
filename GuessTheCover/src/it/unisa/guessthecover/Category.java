package it.unisa.guessthecover;

import java.io.Serializable;

public class Category implements Serializable  {
	private String name;
	private int image;
	private int multiplier;
	
	public Category (String n,int im){
		name=n;
		image=im;
	}
	public String getName() {return name;}
	public int getImage() {return image;}
	public void addImage(int i){
		image=i;
	}
	public int getMultiplier(){return multiplier;}
	public void setMultiplier(int m){multiplier=m;}
}
