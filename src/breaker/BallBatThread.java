package breaker;
/*
 * Class which handles movement of balls and bats and call's their respective functions to update drawing area
 */
public class BallBatThread implements Runnable{
	//Class variables:
	private Ball ball, ball2;
	private boolean twoballs = false;
	private Bat bat1;
	private Bat bat2;
	private boolean pause = false;
	private int speed = 3;
	private int batspeed = 1;
	private int[] direction;
	private boolean stop = true;
	private Thread t;
	boolean reset = false, reset2 = false;
	int xminusy1 = 0, xminusy2 = 0;
	private boolean movebat1up = false;
	private boolean movebat2up = false;
	private boolean movebat1down = false;
	private boolean movebat2down = false;
	private boolean AI = false;
	private boolean maxspeed = false;
	private boolean reverse = false;
	//Constructor:
	public BallBatThread(Ball ball, Bat b1, Bat b2, boolean AI, boolean s){
		maxspeed = s;
		if(maxspeed) speed = 1;
		bat1 = b1;
		bat2 = b2;
		this.ball = ball;
		this.AI = AI;
		twoballs = false;
	}
	//Overriding constructor for two-balls game:
	public BallBatThread(Ball ball, Ball ball2, Bat b1, Bat b2, boolean AI, boolean s){
		maxspeed = s;
		if(maxspeed) speed = 1;
		bat1 = b1;
		bat2 = b2;
		this.ball = ball;
		this.ball2 = ball2;
		twoballs = true;
		this.AI = AI;
	}
	//Resets attributes when ball's location resets.
	public void reset(){
		reset = false;
		if(!maxspeed)speed = 5;
		else speed = 1;
	}
	public void setReverse(boolean r){
		reverse = r;
	}
	//Run method
	public void run(){
    	/*
    	 * Ball movement is handled with x/y relationship, so variables for checking proper movement is required
    	 * for both balls.
    	 */
		int x = 0;
    	int y = 0;
    	int x2 = 0;
    	int y2 = 0;
    	int movebat = 0;
    	xminusy1 = 0;
    	xminusy2 = 0;
    	reset = false;
    	reset2 = false;
		while(!stop){
			while(!pause){
				if(!reset){
					//Sets x and y back to original values from ball's direction
					x = ball.getDirectionX();
					if(x<0) x = x - x - x;
					y = ball.getDirectionY();
					if(y<0) y = y - y - y;
					if(y>x)xminusy1 = x;
					if(x>y)xminusy1 = y;
					if(x==y)xminusy1 = 0;
					reset = true;
				}
				direction = ball.getDirection();
				if(x == y){
					if(direction[0]>0)ball.setX(ball.getX()+1);
		    		else if (direction[0]<0)ball.setX(ball.getX()-1);
		    		if(direction[1]>0)ball.setY(ball.getY()+1);
		    		else if (direction[1]<0)ball.setY(ball.getY()-1);
					if(xminusy1<=0)reset = false;
					else xminusy1--;
				}
	    		if(x>y){
	    			if(direction[0]>0)ball.setX(ball.getX()+1);
	        		else if (direction[0]<0)ball.setX(ball.getX()-1);
	    			y++;
	    		}
	    		if(y>x){
	    			if(direction[1]>0)ball.setY(ball.getY()+1);
	        		else if (direction[1]<0)ball.setY(ball.getY()-1);
	    			x++;
	    		}
	    		
	    		
	    		//Same done for second ball in two-ball game:
	    		if(twoballs){
		    		if(!reset2){
						x2 = ball2.getDirectionX();
						if(x2<0) x2 = x2 - x2 - x2;
						y2 = ball2.getDirectionY();
						if(y2<0) y2 = y2 - y2 - y2;
						if(y2>x2)xminusy2 = x2;
						if(x2>y2)xminusy2 = y2;
						if(x2==y2)xminusy2 = 0;
						reset2 = true;
					}
					direction = ball2.getDirection();
					if(x2 == y2){
						if(direction[0]>0)ball2.setX(ball2.getX()+1);
			    		else if (direction[0]<0)ball2.setX(ball2.getX()-1);
			    		if(direction[1]>0)ball2.setY(ball2.getY()+1);
			    		else if (direction[1]<0)ball2.setY(ball2.getY()-1);
			    		if(xminusy2<=0)reset2 = false;
						else xminusy2--;
					}
		    		if(x2>y2){
		    			if(direction[0]>0)ball2.setX(ball2.getX()+1);
		        		else if (direction[0]<0)ball2.setX(ball2.getX()-1);
		    			y2++;
		    		}
		    		if(y2>x2){
		    			if(direction[1]>0)ball2.setY(ball2.getY()+1);
		        		else if (direction[1]<0)ball2.setY(ball2.getY()-1);
		    			x2++;
		    		}
	    		}
	    		//This if-else branch is for AI and bat movement
	    		if(!twoballs){
	    			if(ball.getSpeed()<=movebat + batspeed){
		    		  	if(!AI){
			    			if((movebat1up&&!reverse)||(reverse&&movebat1down))bat1.setPosition(bat1.getPosition()-2);
			    			else if((movebat1down&&!reverse)||(movebat1up&&reverse))bat1.setPosition(bat1.getPosition()+2);
			    		}else{
			    			if(ball.getX()>500){ //if ball's x position is over 500 AI returns bat to center.
			    				if(bat1.getPosition()>250)bat1.setPosition(bat1.getPosition()-2);
			    				else if((bat1.getPosition()<250))bat1.setPosition(bat1.getPosition()+2);
			    			}else{
			    				if(ball.getY()+5>bat1.getPosition()+bat1.getHeight())bat1.setPosition(bat1.getPosition()+2);
				    			if(ball.getY()<bat1.getPosition()+5)bat1.setPosition(bat1.getPosition()-2);
			    			}
			    		}
		    		  	if((movebat2up&&!reverse)||(reverse&&movebat2down))bat2.setPosition(bat2.getPosition()-2);
		    			else if((movebat2down&&!reverse)||(movebat2up&&reverse))bat2.setPosition(bat2.getPosition()+2);
			    		if(movebat>=ball.getSpeed())movebat = 0;
			    		else movebat++;
	    			}else movebat++;
	    		}else{
	    			if(!AI){
	    				if((movebat1up&&!reverse)||(reverse&&movebat1down))bat1.setPosition(bat1.getPosition()-2);
		    			else if((movebat1down&&!reverse)||(movebat1up&&reverse))bat1.setPosition(bat1.getPosition()+2);
		    		}else{
		    			if((ball.getX()>500&&!(ball.getX()<=10)) && (ball2.getX()>500&&!(ball.getX()<=10))){
		    				if(bat1.getPosition()>250)bat1.setPosition(bat1.getPosition()-2);
		    				else if((bat1.getPosition()<250))bat1.setPosition(bat1.getPosition()+2);
		    			}else{
		    				if(!(ball.getX()>500)&&!(ball.getX()<=10)&&ball.getDirectionX()<=0&&(!(ball2.getX()<ball.getX())&&!(ball2.getX()<=10)&&ball2.getDirectionX()>0)){
		    					if(ball.getY()+5>bat1.getPosition()+bat1.getHeight())bat1.setPosition(bat1.getPosition()+2);
				    			if(ball.getY()<bat1.getPosition()+5)bat1.setPosition(bat1.getPosition()-2);
		    				}else{
		    					if(ball2.getY()+5>bat1.getPosition()+bat1.getHeight())bat1.setPosition(bat1.getPosition()+2);
				    			if(ball2.getY()<bat1.getPosition()+5)bat1.setPosition(bat1.getPosition()-2);
		    				}
		    				
		    			}
		    		}
	    			if((movebat2up&&!reverse)||(reverse&&movebat2down))bat2.setPosition(bat2.getPosition()-2);
	    			else if((movebat2down&&!reverse)||(movebat2up&&reverse))bat2.setPosition(bat2.getPosition()+2);
	    		}
	    		ball.drawBall();
	    		if(twoballs){
	    			ball2.drawBall();
	    		}
	    		try{
	    			Thread.sleep(speed);
	    		}
	    		catch(InterruptedException e){
	    	    }
			}
    	}
    }
	//Stop and play methods
	 public void stop(){
		 stop = true;
		 pause = true;
		 t = null;
	 }
	public void play(){
		stop();
		if (t==null){
			t = new Thread(this);
	        stop = false;
	        pause = false;
	        t.start();
	    }
	}
	//Getters and setters:
	public void setBat1Up(boolean up){
		movebat1up = up;
	}
	public void setBat1Down(boolean down){
		movebat1down = down;
	}
	public void setBat2Up(boolean up){
		movebat2up = up;
	}
	public void setBat2Down(boolean down){
		movebat2down = down;
	
	}public boolean getBat1Up(){
		return movebat1up;
	}
	public boolean getBat1Down(){
		return movebat1down;
	}
	public boolean getBat2Up(){
		return movebat2up;
	}
	public boolean getBat2Down(){
		return movebat2down;
	}
	public void setSpeed(int speed){
		if(!maxspeed)this.speed = speed;
		else speed = 1;
	}
	public int getSpeed(){
		return speed;
	}
	public void setPause(){
		pause = !pause;
	}
	public void setBatspeed(int i){
		batspeed = i;
	}
	public void setAI(boolean AI){
		this.AI = AI;
	}
	public void setMaxspeed(boolean s){
		maxspeed = s;
		if(maxspeed)speed = 1;
	}
}