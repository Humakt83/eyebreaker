package breaker;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Random;
import java.util.Vector;

public abstract class BoxModes implements GameMode{
	protected Game g;
	protected boolean intersecting = false;
	protected File folder = new File("Levels");
	protected Box boxes[];
	protected Ball ball1, ball2;
	protected Bat b1, b2;
	protected Map currentlevel = new Map();
	protected BallBatThread ballthread;
	protected boolean firstballout = false, secondballout = false;
	protected DrawingArea da;
	protected boolean ballstartpositionleft = true;
	protected int boxesleft;
	protected boolean insidebox[];
	protected int level;
	protected int maxlevel;
	protected boolean hitY1 = false, hitY2 = false;
	protected int balls = 2;
	protected boolean twoballs = false;
	protected boolean ball1underbat1 = false, ball2underbat1 = false, ball1underbat2 = false, ball2underbat2 = false;
	protected Vector<ScoreSphere> spheres = new Vector<ScoreSphere>();
	protected Random random = new Random();
	protected boolean hotRed = false;
	protected boolean secondtime = true;
	protected boolean lasthitplayer1ball1 = true;
	protected boolean lasthitplayer1ball2 = false;
	//resets boolean array which is used to determine if ball is inside box
	public void setInsideBox(){
		insidebox = new boolean[boxes.length];
		for(int i = 0; i<insidebox.length; i++)insidebox[i] = false;
	}
	public Vector<ScoreSphere> getSpheres() {
		return spheres;
	}
	public abstract void resetStuff();
	public void stopBallthread(){
		if(ballthread!=null)ballthread.stop();
		if(da!=null)da.gameOver(false);
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
	public void moveBat(boolean[] move){
		if(move[2]){
			ballthread.setBat1Down(move[1]);
			ballthread.setBat1Up(move[0]);
		}else{
			ballthread.setBat2Down(move[1]);
			ballthread.setBat2Up(move[0]);
		}
	}
	public void pause(){
		ballthread.setPause();
		//System.out.println("unberbat1: " + underBat1 + " unberbat2: " + underBat2);
	}
	public void setAI(boolean AI){
		if(ballthread!=null)ballthread.setAI(AI);
	}
	public void setMaxSpeed(boolean s){
		ballthread.setMaxspeed(s);
	}
	public void setHotRed(boolean hotRed) {
		this.hotRed = hotRed;
		da.setHotRed(hotRed);
	}
	public abstract void scoreFromSphere(boolean player1);
	public void moveSpheres(){
		for(int i = spheres.size()-1; i>=0; i--){
			if(spheres.elementAt(i).isDirectionLeft()){
				spheres.elementAt(i).setPositionX(spheres.elementAt(i).getPositionX()-1);
				if(spheres.elementAt(i).getPositionX()<0)spheres.removeElementAt(i);
				else{
					int x = spheres.elementAt(i).getPositionX(), y = spheres.elementAt(i).getPositionY();
					if((x>=50&&x<=70)&&(y<=b1.getPosition()+(b1.getHeight())&&y+30>=b1.getPosition())){
						this.scoreFromSphere(true);
						
						spheres.elementAt(i).effect(ball1, ball2, b1, b2, this, this.ballthread);
						spheres.removeElementAt(i);
					}	
				}
			}else{
				spheres.elementAt(i).setPositionX(spheres.elementAt(i).getPositionX()+1);
				if(spheres.elementAt(i).getPositionX()>800)spheres.removeElementAt(i);
				else{
					int x = spheres.elementAt(i).getPositionX(), y = spheres.elementAt(i).getPositionY();
					if((x>=750&&x<=770)&&(y<=b2.getPosition()+(b2.getHeight())&&y+30>=b2.getPosition())){
						this.scoreFromSphere(false);
						spheres.elementAt(i).effect(ball1, ball2, b1, b2, this, this.ballthread);
						spheres.removeElementAt(i);
					}
				}
			}
		}
		da.setSpheres(spheres);
	}
	public boolean checkCollideWithBat(int x, int y, int r, Ball ball, boolean second){
		boolean hit = false;
		//is ball colliding with bat 1
		if(((x>=50&&x<=70)&&(y<=b1.getPosition()+(b1.getHeight())&&y+r>=b1.getPosition()))
				&&!((hitY2&&second)||(hitY1&&!second))
				&&!((ball1underbat1&&!second)||(ball2underbat1&&second))){
			g.playSound(1);
			hit = true;
			if(second){
				this.lasthitplayer1ball2 = true;
				ball2underbat1 = true;
			}
			else{
				ball1underbat1 = true;
				this.lasthitplayer1ball1 = true;
			}
			if(((y>=b1.getPosition()+b1.getHeight())&&(y<=b1.getPosition()+b1.getHeight())&&ball.getDirectionY()<=0)||((y+r<=b1.getPosition())&&(y+r>=b1.getPosition())&&ball.getDirectionY()>=0)||x<65){
				ball.setDirectionY(-ball.getDirectionY());  
				if(second)hitY2 = true;
				else hitY1 = true;
			}
			else{
			if(ball.getDirectionX()<0)ball.setDirectionX(-ball.getDirectionX());           
            // bat1 moves into opposite direction from ball's direction
			//if(!twoballs){
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
			//}
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
			if(second){
				this.lasthitplayer1ball2 = false;
				ball2underbat2 = true;
			}
			else{
				this.lasthitplayer1ball1 = false;
				ball1underbat2 = true;
			}
			g.playSound(1);
			hit = true;
			if(((y>=b2.getPosition()+b2.getHeight())&&(y<=b2.getPosition()+b2.getHeight())&&ball.getDirectionY()<=0)||((y+r<=b2.getPosition())&&(y+r>=b2.getPosition())&&ball.getDirectionY()>=0)||x>755){
				ball.setDirectionY(-ball.getDirectionY());
				if(second)hitY2 = true;
				else hitY1 = true;
			}
			else{ 
				if(ball.getDirectionX()>0)ball.setDirectionX(-ball.getDirectionX());   
				//if(!twoballs){
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
				//}
			}
		}else if (!((x+r>=750 && x <=770)&&(y<=b2.getPosition()+(b2.getHeight())&&y+r>=b2.getPosition()))
				&&!((hitY2&&second)||(hitY1&&!second))){
			if(second)ball2underbat2 = false;
			else ball1underbat2 = false;
		}
		return hit;
		//if(((x+r<750||x>770)||(y>b2.getPosition()+(b2.getHeight())||y+r<b2.getPosition()))&&(underBat2))underBat2 = false;
	}
	public abstract void checkCollision(int x, int y, int r, Ball ball, boolean s);
	public boolean checkCollideWithBox(int x, int y, int r, Ball ball, boolean second){
		boolean hit = false;
		boolean collidedwithbox = false;
		for(int j = 0; j<insidebox.length;j++){
			if(insidebox[j]){
				if((((x+r>=boxes[j].getPositionX()&&(x<=boxes[j].getPositionX()+20)&&ball.getDirectionX()>0))||((x<=boxes[j].getPositionX()+20&&(x+r>=boxes[j].getPositionX())&&ball.getDirectionX()<=0)))&&(y+r>=boxes[j].getPositionY()&&y<=boxes[j].getPositionY()+40)&&!boxes[j].getDestroyed())collidedwithbox = true;
				else insidebox[j] = false;
			}
		}
		boolean yhit = false, xhit = false;
		Vector<Integer> destroy = new Vector<Integer>();
		for(int i = 0; i<boxes.length && !collidedwithbox&&!(yhit||xhit); i++){
			if(!boxes[i].getDestroyed()){ //if box is not destroyed already
				if((((x+r>=boxes[i].getPositionX()&&(x<=boxes[i].getPositionX()+20)&&ball.getDirectionX()>0))||((x<=boxes[i].getPositionX()+20&&(x+r>=boxes[i].getPositionX())&&ball.getDirectionX()<=0)))&&(y+r>=boxes[i].getPositionY()&&y<=boxes[i].getPositionY()+40)){ //if ball is touching or inside the box
					if((y==boxes[i].getPositionY()+40&&!(ball.getDirectionY()>=0))||(y+r==boxes[i].getPositionY()&&!(ball.getDirectionY()<=0))){ //if ball is on upper or lower side of the box
						
						//trying to solve corner cases:
						if(!(x+r==boxes[i].getPositionX()||x==boxes[i].getPositionX()+20)){
							yhit =  true;
						}else{
							yhit = true;
							xhit = true;
							for(int u = 0; u<boxes.length;u++){
								if(!(u==i||boxes[u].getDestroyed())){
									if(((boxes[i].getPositionX()==boxes[u].getPositionX())&&(x+r==boxes[u].getPositionX()||x==boxes[u].getPositionX()+20))&&((boxes[i].getPositionY()==boxes[u].getPositionY())&&(y==boxes[u].getPositionY()+40||y+r==boxes[u].getPositionY()))){
										yhit = true;
										xhit = true;
									}else if((boxes[i].getPositionX()==boxes[u].getPositionX())&&(x+r==boxes[u].getPositionX()||x==boxes[u].getPositionX()+20))yhit=false;
										else if((boxes[i].getPositionY()==boxes[u].getPositionY())&&(y==boxes[u].getPositionY()+40||y+r==boxes[u].getPositionY()))xhit=false;		
									if(!yhit && !xhit){
										yhit = true;
										xhit = true;
									}
								}
							}
							
						}
					} else {
						xhit = true;
					}
					if(boxes[i].getHits()<=1||this.hotRed){
						boxes[i].getEffect(ball, b1, b2, ballthread);
						if(!boxes[i].isIndestructible()){
							if(hotRed){
								yhit = false;
								xhit = false;
							}
							destroy.add(i);
							g.playSound(2);
							boxesleft--;
							if(!second){
								if(lasthitplayer1ball1)scoreFromSphere(true);
								else scoreFromSphere(false);
							}else{
								if(lasthitplayer1ball2)scoreFromSphere(true);
								else scoreFromSphere(false);
							}
						}else g.playSound(3); //indestructible sound
						if(boxesleft <= 0){
							this.changeLevel();
							collidedwithbox = true;
						}
					}else{
						g.playSound(2);
						boxes[i].setHits(boxes[i].getHits()-1);
						boxes[i].setColor(boxes[i].getColor()-1);
						if(!twoballs){
							if(lasthitplayer1ball1)scoreFromSphere(true);
							else scoreFromSphere(false);
						}else{
							if(!second){
								if(lasthitplayer1ball1)scoreFromSphere(true);
								else scoreFromSphere(false);
							}else{
								if(lasthitplayer1ball2)scoreFromSphere(true);
								else scoreFromSphere(false);
							}
							
						}
					}
				if(!collidedwithbox)insidebox[i] = true;
				}
			}
		}
		if(!collidedwithbox){
			if(xhit){
				ball.setDirectionX(-ball.getDirectionX());
				hit = true;
			}
			if(yhit){
				ball.setDirectionY(-ball.getDirectionY());
				hit = true;
			}
			for(int i = 0; i<destroy.size(); i++){
				if(random.nextInt(3) == 0){
					if((!second&&this.lasthitplayer1ball1)
							||second&&this.lasthitplayer1ball2)spheres.add(new ScoreSphere(b1, x, y));
					else{
						spheres.add(new ScoreSphere(b2, x, y));
						da.setSpheres(spheres);
					}
				}
				boxes[destroy.elementAt(i).intValue()].setDestroyed();
			}
		}
		return hit;
	}
	public abstract void changeLevel();
}
