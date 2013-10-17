package breaker;
/*
 * Subclass of Box
 */
public class VioletBox extends Box{
	private static final long serialVersionUID = -6814129195141367795L;
	public VioletBox(int x, int y) {
		super(x, y);
		color = 8;
	}
	//Sets game's speed to fast
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		t.setSpeed(1);
	}
}
