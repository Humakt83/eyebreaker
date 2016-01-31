package fi.ukkosnetti.breaker.game.level;

import fi.ukkosnetti.breaker.game.concept.Ball;
import fi.ukkosnetti.breaker.game.concept.Bat;
import fi.ukkosnetti.breaker.game.logic.BallBatThread;

/*
 * Subclass of Box
 */
public class TealBox extends Box{
	private static final long serialVersionUID = 5198068591331432120L;
	public TealBox(int x, int y) {
		super(x, y);
		color = 5;
	}
	//Decreases ball's radius
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){
		if(ball.getRadius()>5)ball.setRadius(ball.getRadius()-5);
	}
}
