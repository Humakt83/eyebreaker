package breaker;
/*
 * This is interface class used by three different game modes: normal, versus and pong.
 */
public abstract interface GameMode {
	//function to stop game thread when game is over
	public abstract void stopBallthread();
	//function for checking collision with borders and if ball is out of game area
	public abstract void checkCollision(int x, int y, int r, Ball ball, boolean s);
	//function for checking collisions with bats
	public abstract boolean checkCollideWithBat(int x, int y, int r, Ball ball, boolean second);
	public abstract void checkCollideWithAnotherBall();
	//function for moving bats
	public abstract void moveBat(boolean[] move);
	//function for pausing game
	public abstract void pause();
	//sets AI on or off
	public abstract void setAI(boolean AI);
	//sets maximum game speed on or off
	public abstract void setMaxSpeed(boolean s);
}
