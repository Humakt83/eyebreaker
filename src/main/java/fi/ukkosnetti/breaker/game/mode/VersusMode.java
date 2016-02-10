package fi.ukkosnetti.breaker.game.mode;
import java.io.File;
import java.util.List;

import fi.ukkosnetti.breaker.game.Game;
import fi.ukkosnetti.breaker.game.concept.Ball;
import fi.ukkosnetti.breaker.game.concept.Bat;
import fi.ukkosnetti.breaker.game.level.Box;
import fi.ukkosnetti.breaker.game.logic.BallBatThread;
import fi.ukkosnetti.breaker.game.ui.DrawingArea;

/*
 * This is class that handles VersusMode gametype.
 * It is a game where Boxbreaker and Pong are combined.
 */
public class VersusMode extends BoxModes{
	//Constructor starts the game
	public VersusMode(int i, DrawingArea da, boolean AI, boolean maxspeed, boolean twoballs, Game g){
		this.g = g;
		this.twoballs = twoballs;
		File[] listOfFiles = folder.listFiles();
		maxlevel = listOfFiles.length;
		if(i>maxlevel)i = maxlevel;
		level = i;
		this.da = da;
		this.b1 = new Bat(true, da);;
		this.b2 = new Bat(false, da);;
		ball1 = new Ball(da, this, false);
		da.setTwoballs(twoballs);
		if(twoballs){
			ball2 = new Ball(da, this, true);
			ballthread = new BallBatThread(ball1, ball2, b1, b2, AI, maxspeed);
		}else ballthread = new BallBatThread(ball1, b1, b2, AI, maxspeed);
		changeLevel();	
		da.resetScore();
		ballthread.play();
		ballthread.setAI(AI);
	}
	public void resetStuff(){
		if(twoballs){
			ball2.setRadius(15);
			ball2.setSpeed(1);
			int[] t = {-5,-5};
			ball1.setDirection(t);
			ball1.setX(600);
			ball1.setY(255);
			int[] t2 = {5,5};
			ball2.setDirection(t2);
			ball2.setX(200);
			ball2.setY(255);
			secondballout = false;
			firstballout = false;
			lasthitplayer1ball2 = false;
			balls = 2;
			ball2underbat1 = false;
			ball2underbat2 = false;
		}else{
			if(ballstartpositionleft){
				int[] t = {-5,-5};
				ball1.setDirection(t);
				ball1.setX(600);
				ball1.setY(255);
			}else{
				int[] t = {5,5};
				ball1.setDirection(t);
				ball1.setX(200);
				ball1.setY(255);
			}
		}
		ball1underbat1 = false;
		ball1underbat2 = false;
		lasthitplayer1ball1 = true;
		ball1.setSpeed(1);
		ballthread.setSpeed(4);
		ball1.setRadius(15);
		b1.setHeight(60);
		b2.setHeight(60);
		hotRed = false;
		ballthread.reset();
		da.setHotRed(hotRed);
	}
	//called when level is completed
	public void changeLevel(){		
		this.resetStuff();
		if(level>=maxlevel){
			level = 0;
			this.changeLevel();
		}else{
			File[] listOfFiles = folder.listFiles();
			String mapname = listOfFiles[level].getName();
			if(mapname.contains(".map")){
					mapname = mapname.replace(".map", "");
					currentlevel = currentlevel.readLevel(mapname);
					List<Box> boxy = currentlevel.getBoxes();
					boxes = new Box[boxy.size()];
					boxesleft = boxy.size();
					for(int i = 0; i<boxes.length; i++){
						boxes[i] = boxy.get(i);
						if(boxes[i].isIndestructible())boxesleft--;
					}
					da.setBoxes(boxes);
					setInsideBox();
					level++;
					ballstartpositionleft = !ballstartpositionleft;
			}else{
				level++;
				this.changeLevel();
			}
		}
	}
	//Method for checking collisions of ball with bats and game's borders
	public void checkCollision(int x, int y, int r, Ball ball, boolean s){
		//is ball out of play-area
		boolean hit = false;
		if(((int)(x+(r)) <= 0)||((int)(x+(r/2)) >= 800)){
			hitY1 = false;
			hitY2 = false;
			if(((s && !secondballout)||(!s && !firstballout))&&twoballs){
				balls--;
				if(s) secondballout = true;
				else firstballout = true;
				if(x <= 0)da.updateScore(0,1);
				if(x+r >= 800)da.updateScore(1,0);
			}
			if(twoballs&&balls<=0){
				this.resetStuff();				
			}
			if(!twoballs){
				if(x <= 0){
					da.updateScore(0,1);
				}
				if(x+r >= 800){
					da.updateScore(1,0);
				}
				this.resetStuff();
			}
		}else if(y <= 10||y+r >= 540){ //is ball colliding with border
			g.playSound(1);
			if(y <= 10){
				int dirY = ball.getDirectionY();
				if(dirY<=0)ball.setDirectionY(-dirY);
			}
			if(y+r >= 540){
				int dirY = ball.getDirectionY();
				if(dirY>=0)ball.setDirectionY(-dirY);
			}
		}else{
			hit = checkCollideWithBat(x, y, r, ball, s);
		}
		if(!hit)
			checkCollideWithBox(x, y, r, ball, s);
		if(twoballs&&!hit)
			checkCollideWithAnotherBall();
		if(!spheres.isEmpty()&&!(twoballs&&secondtime)){
			moveSpheres();
			secondtime = true;
		}else secondtime = false;
	}
	//function to check whether ball is colliding with box
	public void scoreFromSphere(boolean player1){}
}
