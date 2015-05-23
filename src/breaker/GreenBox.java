package breaker;
/*
 * Subclass of Box
 */
public class GreenBox extends Box{
	private static final long serialVersionUID = 7090694662633515904L;
	public GreenBox(int x, int y) {
		super(x, y);
		color = 0;
	}
	//No Effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){}
}