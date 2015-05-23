package breaker;

import java.util.Random;
/*
 * Subclass of Box
 */
public class WhiteBox extends Box{
	private static final long serialVersionUID = 783130889911811910L;
	private Random raneffect = new Random();
	public WhiteBox(int x, int y) {
		super(x, y);
		color = 10;
	}
	//Random positive effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		int pickpositive = raneffect.nextInt(3);
		switch(pickpositive){
			case 0:
				if(ball.getRadius()<20)ball.setRadius(ball.getRadius()+5);
				break;
			case 1:
				if(b1.getHeight()<80){
					b1.setHeight(b1.getHeight()+10);
					b2.setHeight(b2.getHeight()+10);
				}
				break;
			case 2:
				if(t.getSpeed()!=6)t.setSpeed(5);
				break;
		}
	}
}
