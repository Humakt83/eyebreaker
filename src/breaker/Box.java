package breaker;
/*
 * Abstract class for game's boxes
 */
import java.awt.*;
import java.io.*;
public abstract class Box implements Cloneable, Serializable{
	private static final long serialVersionUID = -7016612136231451721L;
	//Class variables, visible to subclasses
	protected int hits = 1;
	protected int color;
	protected boolean indestructible = false;
	protected int positionX;
	protected int positionY;
	protected boolean destroyed = false;
	
	/* Memo:
	 * Width: 20 always -- doesn't require variable
	 * Height: 40 always -- doesn't require variable
	 * Effects:
	 * 0: nothing
	 * 1: increase ball radius
	 * 2: decrease ball radius
	 * 3: increase height of bats
	 * 4: decrease height of bats
	 * 5: increase ball speed
	 * 6: decrease ball speed
	 * 7: random positive effect
	 * 8: random negative effect
	 */
	/*
	 * Colors:
	 * 0: Green, 1 hit
	 * 1: Yellow, 2 hits
	 * 2: Red, 3 hits
	 * 3: Grey, indestructible
	 * 4: Blue, effect 1
	 * 5: Teal, effect 2;
	 * 6: Gold, effect 3,
	 * 7: Orange, effect 4
	 * 8: Violet, effect 5
	 * 9: Pink, effect 6
	 * 10: White, effect 7
	 * 11, Black, effect 8
	 */
	
	public Box(int x, int y){
		positionY = y;
		positionX = x;
		//Other variables are defined in subclasses.
	}
	public abstract void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t);
	//Getters and setters:
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public boolean isIndestructible() {
		return indestructible;
	}
	public void setIndestructible(boolean indestructible) {
		this.indestructible = indestructible;
	}
	
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setDestroyed(){
		if(!indestructible)destroyed = true;
	}
	public boolean getDestroyed(){
		return destroyed;
	}
	public void setPosition(Point p){
		positionX = (int)p.getX();
		positionY = (int)p.getY();
	}
	public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
}
