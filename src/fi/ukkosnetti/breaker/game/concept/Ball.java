package fi.ukkosnetti.breaker.game.concept;

import fi.ukkosnetti.breaker.game.mode.GameMode;
import fi.ukkosnetti.breaker.game.ui.DrawingArea;

/*
 * Class for Ball objects, stores ball's direction and other attributes
 */
public class Ball {
	//Class variables
	private DrawingArea da;
	private int speed = 1;
	private GameMode l;
	private int x = 200;
	private int y = 255;
	private int radius = 15;
	private int direction[] = {5, -5};
	private final int maxDirection = 10, minDirection = -10;
	
	private boolean second;
	public Ball(DrawingArea da, GameMode l, boolean s){
		second = s;
		//In case game has two balls, the other is placed on other side with opposite direction.
		if(second){
			x = 600;
			y = 255;
			direction[0] = -5;
			direction[1] = 5;
		}
		this.da = da;
		this.l = l;
		//gives ball's attributes to drawing area
		da.updateBall(x, y, radius, second);
	}
	//method which is called every time ball's position changes
	public void drawBall(){
		da.updateBall(x, y, radius, second);
		l.checkCollision(x, y, radius, this, second);
	}
	//Getters and setters:
	public void setRadius(int r){
		radius = r;
	}
	public int getRadius(){
		return radius;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		return x;
	}
	public int[] getDirection(){
		return direction;
	}
	public void setDirection(int[] d){
		direction = d;
	}
	public int getDirectionX(){
		return direction[0];
	}
	public int getDirectionY(){
		return direction[1];
	}
	public void setDirectionX(int i){
		if(i>maxDirection)direction[0] = maxDirection;
		else if(i<minDirection)direction[0] = minDirection;
		else direction[0] = i;
	}
	public void setDirectionY(int i){
		if(i>maxDirection)direction[1] = maxDirection;
		else if(i<minDirection)direction[1] = minDirection;
		else direction[1] = i;
	}
	public void setSpeed(int s){
		speed = s;
	}
	public int getSpeed(){
		return speed;
	}
}
