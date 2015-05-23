package breaker;
import java.util.*;
import java.io.*;
public class NormalMode extends BoxModes{
	private boolean levelhaschanged = false;
	private int batsleft = 3;
	private int scoreplayer1;
	private int scoreplayer2;
	private boolean passedgame = false;
	
	//Constuctor starts the game
	public NormalMode(int i, DrawingArea da, boolean AI, boolean maxspeed, boolean twoballs, Game g){
		this.g = g;
		this.twoballs = twoballs;
		File[] listOfFiles = folder.listFiles();
		maxlevel = listOfFiles.length;
		if(i>maxlevel)i = maxlevel;
		level = i;
		this.da = da;
		this.b1 = new Bat(true, da);
		this.b2 = new Bat(false, da);
		ball1 = new Ball(da, this, false);
		da.setTwoballs(twoballs);
		if(twoballs){
			ball2 = new Ball(da, this, true);
			ballthread = new BallBatThread(ball1, ball2, b1, b2, AI, maxspeed);
		}else ballthread = new BallBatThread(ball1, b1, b2, AI, maxspeed);
		scoreplayer1 = 0;
		scoreplayer2 = 0;
		da.setBatsLeft(batsleft);
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
			lasthitplayer1ball2 = false;
			secondballout = false;
			firstballout = false;
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
		lasthitplayer1ball1 = true;
		ball1underbat1 = false;
		ball1underbat2 = false;
		ball1.setSpeed(1);
		ballthread.setSpeed(4);
		ball1.setRadius(15);
		b1.setHeight(60);
		b2.setHeight(60);
		spheres.removeAllElements();
		da.setSpheres(spheres);
		this.ballthread.setReverse(false);
		hotRed = false;
		ballthread.reset();
		da.setHotRed(hotRed);
	}
	//called when level is completed
	public void changeLevel(){
		this.resetStuff();
		if(level >= maxlevel){
			level = 0;
			passedgame = true;
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
					if(levelhaschanged && !passedgame)batsleft++;
					else levelhaschanged = true;
					da.setBoxes(boxes);
					da.setBatsLeft(batsleft);
					setInsideBox();
					level++;
					ballstartpositionleft = !ballstartpositionleft;
			}else{
				level++;
				this.changeLevel();
			}
		}
	}
	
	public void checkCollision(int x, int y, int r, Ball ball, boolean s){
		//is ball out of playarea
		boolean hit = false;
		if(((int)(x+(r)) <= 0)||((int)(x+(r/2)) >= 800)){
			hitY1 = false;
			hitY2 = false;
			if((s && !secondballout)||(!s && !firstballout)){
				balls--;
				if(s) secondballout = true;
				else firstballout = true;
			}
			if(twoballs&&balls<=0){
				this.resetStuff();
				if(batsleft<=0){
					g.setHighscores(scoreplayer1, scoreplayer2);
					da.gameOver(true);
					ballthread.stop();
				}else{
					batsleft--;
					da.setBatsLeft(batsleft);
				}	
			}
			if(!twoballs){			
				if(batsleft<=0){
					g.setHighscores(scoreplayer1, scoreplayer2);
					da.gameOver(true);
					ballthread.stop();
				}else{
					batsleft--;
					resetStuff();
					da.setBatsLeft(batsleft);
				}
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
			hit = checkCollideWithBox(x, y, r, ball, s);
		if(twoballs&&!hit)
			checkCollideWithAnotherBall();
		if(!spheres.isEmpty()&&!(twoballs&&secondtime)){
			moveSpheres();
			secondtime = true;
		}else secondtime = false;
	}
	public void scoreFromSphere(boolean player1){
		if(player1)scoreplayer1 += 10;
		else scoreplayer2 += 10;
		da.updateScoreBox(scoreplayer1, scoreplayer2);
	}
	//function to check whether ball is colliding with box
}
