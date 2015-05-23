package breaker;
import java.awt.event.*;
public class Controller implements ActionListener, KeyListener {
	private View v;
	private Game g;
	private About ab;
	private Help he;
	private DrawingArea da;
	private boolean antia = true;
	private boolean gamestarted = false;
	private boolean k = false, i = false, n = false, gkey = false;
	private String level = "";
	private boolean AI = false, speed = false, twoballs = false;
	public Controller(View v, Game g){
		this.g = g;
		this.v = v;
	}
	public void setDrawingArea(DrawingArea da){
		this.da = da;
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("EXIT")){
			System.exit(0);
		}
		if(ae.getActionCommand().equals("NEW")){
				g.newGame(da);
				gamestarted = true;
		}
		if(ae.getActionCommand().equals("VERSUS")){
			g.newVersusGame(da);
			gamestarted = true;
		}
		if(ae.getActionCommand().equals("NEW2")){
			g.newPong(da);
			gamestarted = true;
		}
		if(ae.getActionCommand().equals("AI")){
			AI = !AI;
			v.setAI(AI);
			g.setAI(AI);
		}
		if(ae.getActionCommand().equals("AA")){
			antia = !antia;
			v.setAA(antia);
			da.setAA(antia);
		}
		if(ae.getActionCommand().equals("PLAYERS")){
			v.viewPlayers();
		}
		if(ae.getActionCommand().equals("PLAYERCHANGES")){
			String r[] = v.getNames();
			g.setNames(r);
			da.setNames(r[0], r[1]);
		}
		if(ae.getActionCommand().equals("HIGHSCORES")){
			v.viewHighScores(g.getHighscores());
		}
		if(ae.getActionCommand().equals("ABOUT")){
			if(ab!=null)ab.dispose();
			ab=new About();
		}
		if(ae.getActionCommand().equals("HELP")){
			if(he!=null)he.dispose();
			he=new Help();
		}
		if(ae.getActionCommand().equals("SPEED")){
			speed = !speed;
			v.setSpeed(speed);
			g.setMaxSpeed(speed);
		}
		if(ae.getActionCommand().equals("TWO")){
			twoballs = !twoballs;
			v.setTwo(twoballs);
			g.setTwoballs(twoballs);
		}
		if(ae.getActionCommand().equals("SOUND")){
			g.setSound();
			v.setSound();
		}
		if(ae.getActionCommand().equals("SOUNDDOWN")){
			g.turnVolumeDown();
		}
		if(ae.getActionCommand().equals("SOUNDUP")){
			g.turnVolumeUp();
		}
	}
	public void keyTyped(KeyEvent ke){
		if(ke.getKeyChar()=='p' && gamestarted){
			g.pause();
		}
		if(ke.getKeyChar()=='k'|| k){
			if(!k) k = true;
			else if(ke.getKeyChar()=='i'|| i){
				if(!i) i = true;
				else if(ke.getKeyChar()=='n'|| n){
					if(!n) n = true;
					else if(ke.getKeyChar()=='g'|| gkey){
						if(!gkey) gkey = true;
						else{
								boolean through = false;
								switch(ke.getKeyChar()){
									case '0':
										level += 0;
										through = true;
										break;
									case '1':
										level += 1;	
										through = true;
										break;
									case '2':
										level += 2;		
										through = true;
										break;
									case '3':
										level += 3;		
										through = true;
										break;
									case '4':
										level += 4;		
										through = true;
										break;
									case '5':
										level += 5;		
										through = true;
										break;
									case '6':
										level += 6;		
										through = true;
										break;
									case '7':
										level += 7;		
										through = true;
										break;
									case '8':
										level += 8;		
										through = true;
										break;
									case '9':
										level += 9;		
										through = true;
										break;
								}
								if(level.length()==2){
									g.cheat(level);
									level = "";
									k = false;
									i = false;
									n = false;
									gkey = false;
								}else if(!through){
										k = false;
										i = false;
										n = false;
										gkey = false;
										level = "";
									}
						}
					}
				}
			}
		}else{
			k = false;
			i = false;
			n = false;
			gkey = false;
		}
	}
	public void keyReleased(KeyEvent ke){
		processKeyrelease(ke);
	}
	public void keyPressed(KeyEvent ke){
		processKeypress(ke);
	}
	public void processKeypress(KeyEvent ke){
		if(gamestarted){
			if(ke.getKeyChar()=='w' && !AI){
				boolean move[] = {true, false, true};
				g.moveBat(move);
			}
			else if(ke.getKeyChar()=='s' && !AI){
				boolean move[] = {false, true, true};
				g.moveBat(move);
			}
			if(ke.getKeyCode() == KeyEvent.VK_UP){
				boolean move[] = {true,false, false};
				g.moveBat(move);
			}
			if(ke.getKeyCode() == KeyEvent.VK_DOWN){
				boolean move[] = {false,true, false};
				g.moveBat(move);
			}
			
		}
	}
	public void processKeyrelease(KeyEvent ke){
		if(gamestarted){
			if(ke.getKeyChar()=='w' && !AI){
				boolean move[] = {false, false, true, false};
				g.moveBat(move);
			}
			if(ke.getKeyChar()=='s' && !AI){
				boolean move[] = {false, false, true, false};
				g.moveBat(move);
			}
			if(ke.getKeyCode() == KeyEvent.VK_UP){
				boolean move[] = {false,false, false, true};
				g.moveBat(move);
			}
			if(ke.getKeyCode() == KeyEvent.VK_DOWN){
				boolean move[] = {false,false, false, true};
				g.moveBat(move);
			}
		}
	}
}
