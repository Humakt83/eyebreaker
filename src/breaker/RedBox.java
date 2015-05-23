package breaker;
/*
 * Subclass of Box
 */
public class RedBox extends Box{
	private static final long serialVersionUID = -450415321471165325L;
	public RedBox(int x, int y) {
		super(x, y);
		hits = 3;
		color = 2;
	}
	//No effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){}
}
