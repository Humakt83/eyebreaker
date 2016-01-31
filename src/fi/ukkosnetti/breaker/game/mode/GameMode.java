package fi.ukkosnetti.breaker.game.mode;

import fi.ukkosnetti.breaker.game.concept.Ball;

/*
 * This is interface class used by three different game modes: normal, versus and pong.
 */
public interface GameMode {
	//function to stop game thread when game is over
	void stopBallthread();
	//function for checking collision with borders and if ball is out of game area
	void checkCollision(int x, int y, int r, Ball ball, boolean s);
	//function for checking collisions with bats
	boolean checkCollideWithBat(int x, int y, int r, Ball ball, boolean second);
	void checkCollideWithAnotherBall();
	//function for moving bats
	void moveBat(boolean[] move);
	//function for pausing game
	void pause();
	//sets AI on or off
	void setAI(boolean AI);
	//sets maximum game speed on or off
	void setMaxSpeed(boolean s);
}
