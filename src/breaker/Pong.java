package breaker;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Pong implements GameMode{
	private Ball ball1, ball2;
	private Bat b1, b2;
	private boolean intersecting = false;
	private BallBatThread ballthread;
	private boolean firstballout = false, secondballout = false;
	private DrawingArea da;
	private boolean hitY1 = false, hitY2 = false;
	private boolean ball1underbat1 = false, ball2underbat1 = false, ball1underbat2 = false, ball2underbat2 = false;
	private int balls = 2;
	private boolean twoballs = false;
	private Game g;
	
	public Pong(DrawingArea da, boolean AI, boolean maxspeed, boolean twoballs, Game g){
		this.g = g;
		this.twoballs = twoballs;
		this.da = da;
		this.b1 = new Bat(true, da);
		this.b2 = new Bat(false, da);
		ball1 = new Ball(da, this, false);
		da.setTwoballs(twoballs);
		if(twoballs){
			ball2 = new Ball(da, this, true);
			ballthread = new BallBatThread(ball1, ball2, b1, b2, AI, maxspeed);
		}else ballthread = new BallBatThread(ball1, b1, b2, AI, maxspeed);	
		da.resetScore();
		ballthread.play();
		ballthread.setAI(AI);
	}
	//function to stop game thread when game is over
	public void stopBallthread(){
		if(ballthread!=null)ballthread.stop();
		if(da!=null)da.gameOver(false);
	}
	//function for checking collision with borders and if ball is out of game area
	public void checkCollision(int x, int y, int r, Ball ball, boolean s){
		boolean hit = false;
		//is ball out of play area
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
				secondballout = false;
				firstballout = false;
				ball1underbat1 = false;
				ball2underbat1 = false;
				ball1underbat2 = false;
				ball2underbat2 = false;
				balls = 2;
				int[] t = {-5,5};
				this.ball1.setDirection(t);
				this.ball1.setX(600);
				this.ball1.setY(255);
				int[] t2 = {5,5};
				ball2.setDirection(t2);
				ball2.setX(200);
				ball2.setY(255);
				this.ball1.setSpeed(1);
				ball2.setSpeed(2);
				ballthread.setBatspeed(1);
				ballthread.setSpeed(3);
				ballthread.reset();
			}
			if(!twoballs){
				if(x <= 0){
					da.updateScore(0,1);
					int[] t = {-5,5};
					ball.setDirection(t);
					ball.setX(600);
					ball.setY(255);
				}
				if(x+r >= 800){
					da.updateScore(1,0);
					int[] t = {5,-5};
					ball.setDirection(t);
					ball.setX(200);
					ball.setY(255);
				}
				ball1underbat1 = false;
				ball1underbat2 = false;
				ball.setSpeed(1);
				ballthread.setBatspeed(1);
				ballthread.setSpeed(3);
				ballthread.reset();
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
		if(twoballs&&!hit)
			checkCollideWithAnotherBall();
	}
	//function for checking collisions with bats
	public boolean checkCollideWithBat(int x, int y, int r, Ball ball, boolean second){
		boolean hit = false;
		/*Rectangle2D.Double batter1 = new Rectangle2D.Double(50, b1.getPosition(), 20, b1.getHeight());
		Rectangle2D.Double batter2 = new Rectangle2D.Double(750, b2.getPosition(), 20, b2.getHeight());
		Rectangle2D.Double circle = new Rectangle2D.Double(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
		Area circlearea = new Area(circle);
		if(circlearea.intersects(batter1)){
			
		}*/
		//is ball colliding with bat 1
		if(((x>=50&&x<=70)&&(y<=b1.getPosition()+(b1.getHeight())&&y+r>=b1.getPosition()))
				&&!((hitY2&&second)||(hitY1&&!second))
				&&!((ball1underbat1&&!second)||(ball2underbat1&&second))){
			if(second)ball2underbat1 = true;
			else ball1underbat1 = true;
			g.playSound(1);
			hit = true;
			if(((y>=b1.getPosition()+b1.getHeight())&&(y<=b1.getPosition()+b1.getHeight())&&ball.getDirectionY()<=0)||((y+r<=b1.getPosition())&&(y+r>=b1.getPosition())&&ball.getDirectionY()>=0)||x<65){
				ball.setDirectionY(-ball.getDirectionY());  
				if(second)hitY2 = true;
				else hitY1 = true;
			}else{
				if(ball.getDirectionX()<0)ball.setDirectionX(-ball.getDirectionX());           
		        // bat1 moves into opposite direction from ball's direction
				if(!twoballs){
					if(ball.getDirectionY()==0){
						if(ballthread.getBat1Up())ball.setDirectionY(-1);
						if(ballthread.getBat1Down())ball.setDirectionY(1);
					}else if((ballthread.getBat1Up() && ball.getDirectionY() > 0) || (ballthread.getBat1Down() && ball.getDirectionY() < 0)){
			        	   int dir[] = {ball.getDirectionX(), ball.getDirectionY() };
			        	   if(dir[1]>=0)dir[1] += 1;
			               else if(dir[1]<0)dir[1] -= 1;
			               		else dir[1]=1;
			        	   if(dir[0]>=2)dir[0]-=1;
			        	   else if(dir[0]<-1)dir[0]+=1;
			               ball.setDirection(dir);
			               if(ballthread.getSpeed()<4)ballthread.setSpeed(ballthread.getSpeed()+1);
				           else{
				        	   ball.setSpeed(1);
				        	   ballthread.setBatspeed(1);
				           }
			           }else if((ballthread.getBat1Up() && ball.getDirectionY() <= 0) || (ballthread.getBat1Down() && ball.getDirectionY() >= 0)){
			        	   //bat1 moves into same direction as ball does
			        	   int dir[] = {ball.getDirectionX()+1, ball.getDirectionY() };
			        	   if(dir[1]>=2)dir[1]-=1;
			        	   else if(dir[1]<-1)dir[1]+=1;
			               ball.setDirection(dir);
			               if(ball.getSpeed() != 3){
			            	   ball.setSpeed(3);
			            	   ballthread.setBatspeed(2);
			               }else if(ballthread.getSpeed()>1)ballthread.setSpeed(ballthread.getSpeed()-1);
				        	
			           }
				}
			}
		}else if (!((x>=50&&x<=70)&&(y<=b1.getPosition()+(b1.getHeight())&&y+r>=b1.getPosition()))
			&&!((hitY2&&second)||(hitY1&&!second))){
			if(second)ball2underbat1 = false;
			else ball1underbat1 = false;
		}
		//if(((x+r<50||x>70)||(y>b1.getPosition()+(b1.getHeight())||y+r<b1.getPosition()))&&(underBat1))underBat1 = false;
        //is ball colliding with bat 2
		if(((x+r>=750 && x <=770)&&(y<=b2.getPosition()+(b2.getHeight())&&y+r>=b2.getPosition()))
				&&!((hitY2&&second)||(hitY1&&!second))
				&&!((ball1underbat2&&!second)||(ball2underbat2&&second))){
			if(second)ball2underbat2 = true;
			else ball1underbat2 = true;
			g.playSound(1);
			hit = true;
			if(((y>=b2.getPosition()+b2.getHeight())&&(y<=b2.getPosition()+b2.getHeight())&&ball.getDirectionY()<=0)||((y+r<=b2.getPosition()+2)&&(y+r>=b2.getPosition()))&&ball.getDirectionY()>=0||x>755){
				ball.setDirectionY(-ball.getDirectionY());
				if(second)hitY2 = true;
				else hitY1 = true;
			}else{
				if(ball.getDirectionX()>0)ball.setDirectionX(-ball.getDirectionX());   
				
				if(!twoballs){
					// bat2 moves into opposite direction from ball's direction
					if(ball.getDirectionY()==0){
						if(ballthread.getBat2Up())ball.setDirectionY(-1);
						if(ballthread.getBat2Down())ball.setDirectionY(1);
					}else if((ballthread.getBat2Up() && ball.getDirectionY() >= 0) || (ballthread.getBat2Down() && ball.getDirectionY() <= 0)){
			        	int dir[] = {ball.getDirectionX(), ball.getDirectionY() };
			        	if(dir[1]>=0)dir[1] += 1;
			            else if(dir[1]<0)dir[1] -= 1;
			            if(dir[0]>=2)dir[0]-=1;
			        	else if(dir[0]<-1)dir[0]+=1;
			            ball.setDirection(dir);
			            if(ballthread.getSpeed()<4)ballthread.setSpeed(ballthread.getSpeed()+1);
			            else{
			            	ball.setSpeed(1);
			            	ballthread.setBatspeed(1);
			            }
			        }else if((ballthread.getBat2Up() && ball.getDirectionY() <= 0) || (ballthread.getBat2Down() && ball.getDirectionY() >= 0)){
			        	//bat2 moves into same direction as ball does
			        	int dir[] = {ball.getDirectionX()-1, ball.getDirectionY() };
			        	if(dir[0]>=2)dir[1]-=1;
			        	else if(dir[0]<-1)dir[1]+=1;
			        	ball.setDirection(dir);			       
		        		if(ball.getSpeed() != 3){
		        			ball.setSpeed(3);
		        			ballthread.setBatspeed(2);
		        		}else if(ballthread.getSpeed()>1)ballthread.setSpeed(ballthread.getSpeed()-1);
			        	}
			        }
			}
			
		}else if (!((x+r>=750 && x <=770)&&(y<=b2.getPosition()+(b2.getHeight())&&y+r>=b2.getPosition()))
				&&!((hitY2&&second)||(hitY1&&!second))){
			if(second)ball2underbat2 = false;
			else ball1underbat2 = false;
		}
		return hit;
	}
	public void checkCollideWithAnotherBall(){
		Rectangle2D.Double circle1 = new Rectangle2D.Double(this.ball1.getX(),this.ball1.getY(),this.ball1.getRadius(),this.ball1.getRadius());
		Rectangle2D.Double circle2 = new Rectangle2D.Double(this.ball2.getX(),this.ball2.getY(),this.ball2.getRadius(),this.ball2.getRadius());
		Area area1 = new Area(circle1);
	    if(area1.intersects(circle2)&&!intersecting){
	    	intersecting = true;
	    	if((this.ball1.getDirectionX()>0&&!(this.ball2.getDirectionX()>0))||(this.ball1.getDirectionX()<0&&!(this.ball2.getDirectionX()<0))){
	    		if(this.ball1.getDirectionY()>0&&!(this.ball2.getDirectionY()>0)||(this.ball1.getDirectionY()<0&&!(this.ball2.getDirectionY()<0))){
	    			this.ball1.setDirectionY(-this.ball1.getDirectionY());
	    			this.ball2.setDirectionY(-this.ball2.getDirectionY());
	    		}
	    		this.ball1.setDirectionX(-this.ball1.getDirectionX());
	    		this.ball2.setDirectionX(-this.ball2.getDirectionX());
	    	}else{
	    		if(this.ball1.getDirectionY()>0&&!(this.ball2.getDirectionY()>0)||(this.ball1.getDirectionY()<0&&!(this.ball2.getDirectionY()<0))){
	    			this.ball1.setDirectionY(-this.ball1.getDirectionY());
	    			this.ball2.setDirectionY(-this.ball2.getDirectionY());
	    		}else{
	    			this.ball1.setDirectionX(-this.ball1.getDirectionX());
	    			this.ball2.setDirectionX(-this.ball2.getDirectionX());
	    		}
	    	}
	    }else if(!area1.intersects(circle2)&&intersecting) intersecting = false;
	}
	//function for moving bats
	public void moveBat(boolean[] move){
		if(move[2]){
			ballthread.setBat1Down(move[1]);
			ballthread.setBat1Up(move[0]);
		}else{
			ballthread.setBat2Down(move[1]);
			ballthread.setBat2Up(move[0]);
		}
	}
	//function for pausing game
	public void pause(){
		ballthread.setPause();
	}
	//sets AI on or off
	public void setAI(boolean AI){
		if(ballthread!=null)ballthread.setAI(AI);
	}
	//sets maximum game speed on or off
	public void setMaxSpeed(boolean s){
		ballthread.setMaxspeed(s);
	}

}
