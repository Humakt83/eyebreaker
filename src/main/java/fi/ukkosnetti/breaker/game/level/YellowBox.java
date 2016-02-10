package fi.ukkosnetti.breaker.game.level;

import fi.ukkosnetti.breaker.game.concept.Ball;
import fi.ukkosnetti.breaker.game.concept.Bat;
import fi.ukkosnetti.breaker.game.logic.BallBatThread;

/*
 * Subclass of Box
 */
public class YellowBox extends Box{
	private static final long serialVersionUID = -1506599317429235652L;
	public YellowBox(int x, int y) {
		super(x, y);
		hits = 2;
		color = 1;
	}
	//No effect
	public void getEffect(Ball ball, Bat b1, Bat b2, BallBatThread t){}
}
