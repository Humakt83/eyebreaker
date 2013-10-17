package breaker;
/*
 * Subclass of Box
 */
public class PinkBox extends Box{
	private static final long serialVersionUID = -7109253877456557620L;
	public PinkBox(int x, int y) {
		super(x, y);
		color = 9;
	}
	//Method sets game's speed to slow
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		t.setSpeed(5);
	}
}
