package it.unisa.guessthecover;

import java.io.Serializable;

public class Film implements Serializable {
	private String title,category,guess,nameImage;
	private int id,point,image;
	private int multiplier;
	
	public Film(int id1,String t,String c, int p, int i, String ni ,String g){
		id=id1;
		title=t;
		category=c;
		point=p;
		image=i;
		nameImage=ni;
		guess=g;
	}
	public int getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	public String getCategory(){
		return category;
	}
	public int getPoint(){
		return point;
	}
	public int getImage(){
		return image;
	}
	public String getNameImage(){
		return nameImage;
	}
	public String getGuess(){
		return guess;
	}
	@Override
	public String toString(){
		return"[id:"+id+" title:"+title+" category:"+category+" point:"+point+" image:"+image+" name image:"+nameImage+" guess:"+guess+" ]";
	}
	public int getMultiplier(){return multiplier;}
	public void setMultiplier(int m){multiplier=m;}
}
