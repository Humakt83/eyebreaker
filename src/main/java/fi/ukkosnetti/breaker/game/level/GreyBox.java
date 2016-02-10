package fi.ukkosnetti.breaker.game.level;

import fi.ukkosnetti.breaker.game.concept.Ball;
import fi.ukkosnetti.breaker.game.concept.Bat;
import fi.ukkosnetti.breaker.game.logic.BallBatThread;

/*
 * Subclass of Box
 */
public class GreyBox extends Box{
	private static final long serialVersionUID = 8160397201333929175L;
	public GreyBox(int x, int y) {
		super(x, y);
		color = 3;
		indestructible = true;
	}
	//No effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){}
}
