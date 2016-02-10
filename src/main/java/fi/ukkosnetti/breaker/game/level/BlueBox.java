package fi.ukkosnetti.breaker.game.level;

import fi.ukkosnetti.breaker.game.concept.Ball;
import fi.ukkosnetti.breaker.game.concept.Bat;
import fi.ukkosnetti.breaker.game.logic.BallBatThread;

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
