package breaker;
/*
 * Subclass of Box
 */
public class BlueBox extends Box{
	private static final long serialVersionUID = 8951718167942744926L;
	public BlueBox(int x, int y) {
		super(x, y);
		color = 4;
	}
	//Method increases ball radius
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		if(ball.getRadius()<20)ball.setRadius(ball.getRadius()+5);
	}
}
