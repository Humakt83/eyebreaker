package breaker;
/*
 * Subclass of Box
 */
import java.util.Random;

public class BlackBox extends Box{
	private static final long serialVersionUID = 8601959165173992289L;
	private Random raneffect = new Random();
	public BlackBox(int x, int y) {
		super(x, y);
		color = 11;
	}
	//Method generates random negative effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		int picknegative = raneffect.nextInt(3);
		switch(picknegative){
			case 0:
				if(ball.getRadius()>5)ball.setRadius(ball.getRadius()-5);
				break;
			case 1:
				if(b1.getHeight()>40){
					b1.setHeight(b1.getHeight()-10);
					b2.setHeight(b2.getHeight()-10);
				}
				break;
			case 2:
				if(t.getSpeed()!=2)t.setSpeed(1);
				break;
		}
	}
}
