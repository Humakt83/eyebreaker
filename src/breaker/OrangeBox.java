package breaker;
/*
 * Subclass of Box
 */
public class OrangeBox extends Box{
	private static final long serialVersionUID = -6274082600289521413L;
	public OrangeBox(int x, int y) {
		super(x, y);
		color = 7;
	}
	//Method decreases bat height
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		if(b1.getHeight()>40){
			b1.setHeight(b1.getHeight()-10);
			b2.setHeight(b2.getHeight()-10);
		}
	}
}