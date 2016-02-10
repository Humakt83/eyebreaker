package fi.ukkosnetti.breaker.game.concept;

import fi.ukkosnetti.breaker.game.ui.DrawingArea;

/*
 * Class for game's bats, contains only necessary attributes with setters and getters.
 */
public class Bat {
	private DrawingArea da;
	private boolean left;
	private int position = 250;
	private int height = 60;
	public Bat(boolean left, DrawingArea d){
		da = d;
		this.left = left;
		da.updateBat(position, height, left);
	}
	public int getPosition(){
		return position;
	}
	public void setPosition(int p){
		if(p>10&&p<540-height){
			position = p;
			da.updateBat(position, height, left);
		}
	}
	public int getHeight(){
		return height;
	}
	public void setHeight(int h){
		height = h;
		if(position+height>=540){
			position = 540-height;
		}
		da.updateBat(position, height, left);
	}
	public boolean getLeft(){
		return left;
	}
}
