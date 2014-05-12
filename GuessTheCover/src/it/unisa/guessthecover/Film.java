package it.unisa.guessthecover;

public class Film {
	private String title,category,guess;
	private int id,point,image;
	
	public Film(int id1,String t,String c, int p, int i,String g){
		id=id1;
		title=t;
		category=c;
		point=p;
		image=i;
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
	public String getGuess(){
		return guess;
	}
}
