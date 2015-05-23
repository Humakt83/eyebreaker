package breaker;
import java.util.*;
public class ScoreSphere {
	//Width 20 height 30
	private boolean directionLeft;
	private int positionX, positionY;
	private Random ran = new Random();
	private int effect;
	public ScoreSphere(Bat bat, int x, int y){
		directionLeft = bat.getLeft();
		positionX = x;
		positionY = y;
		effect = ran.nextInt(7);
	}
	public boolean isDirectionLeft() {
		return directionLeft;
	}
	public void setDirectionLeft(boolean directionLeft) {
		this.directionLeft = directionLeft;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	//Return effect to determine spheres color
	public int getEffect(){
		return effect;
	}
	public void effect(Ball b1, Ball b2, Bat bat1, Bat bat2, BoxModes gm, BallBatThread bt){
		switch(effect){
			case 0:
				break;
			case 1:
				bat1.setHeight(bat1.getHeight()+10);
				bat2.setHeight(bat2.getHeight()+10);
				break;
			case 2:
				if(bat1.getHeight()>10){
					bat1.setHeight(bat1.getHeight()-10);
					bat2.setHeight(bat2.getHeight()-10);
				}
				break;
			case 3:
				b1.setRadius(b1.getRadius()+5);
				if(b2!=null)b2.setRadius(b2.getRadius()+5);
				break;
			case 4:
				if(b1.getRadius()>5){
					b1.setRadius(b1.getRadius()-5);
					if(b2!=null)b2.setRadius(b2.getRadius()-5);
				}
				break;
			case 5:
				gm.setHotRed(true);
				break;
			case 6:
				bt.setReverse(true);
			default:
				break;
		}
	}
}
