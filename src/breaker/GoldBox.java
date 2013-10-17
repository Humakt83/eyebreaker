package breaker;
/*
 * Subclass of Box
 */
public class GoldBox extends Box{
	private static final long serialVersionUID = -1694530438341681084L;
	public GoldBox(int x, int y) {
		super(x, y);
		color = 6;
	}
	//Method increases bat height
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		if(b1.getHeight()<80){
			b1.setHeight(b1.getHeight()+10);
			b2.setHeight(b2.getHeight()+10);
		}
	}
}
