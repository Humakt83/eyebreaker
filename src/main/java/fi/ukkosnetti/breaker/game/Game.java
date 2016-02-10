package fi.ukkosnetti.breaker.game;
import java.io.*;
import java.util.*;

import fi.ukkosnetti.breaker.game.mode.GameMode;
import fi.ukkosnetti.breaker.game.mode.NormalMode;
import fi.ukkosnetti.breaker.game.mode.Pong;
import fi.ukkosnetti.breaker.game.mode.VersusMode;
import fi.ukkosnetti.breaker.game.ui.DrawingArea;
public class Game{
	private GameMode l; 
	private int level = 0;
	private boolean AI = false, maxspeed = false, twoballs = false;
	private String player1 = "Player 1";
	private String player2 = "Player 2";
	private boolean sound = true;
	private int volume = 1;
	public Game(){
		Sound.init();
		Sound.volume = Sound.Volume.MEDIUM;
		File f = new File("Highscores.dat");
		if(!(f.exists()))this.resetHighscores();
	}
	public void newGame(DrawingArea da){
		if(l!=null)l.stopBallthread();
		l = new NormalMode(level, da, AI, maxspeed, twoballs, this);
		da.setPong(false, false);
	}
	public void newVersusGame(DrawingArea da){
		if(l!=null)l.stopBallthread();
		l = new VersusMode(level, da, AI, maxspeed, twoballs, this);
		da.setPong(true, true);
	}
	public void newPong(DrawingArea da){
		if(l!=null)l.stopBallthread();
		l = new Pong(da, AI, maxspeed, twoballs, this);
		da.setPong(true, false);
	}
	public void moveBat(boolean move[]){
		l.moveBat(move);
	}
	public void pause(){
		l.pause();
	}
	public void cheat(String i){
	    level = Integer.valueOf( i ).intValue();
	}
	public void setMaxSpeed(boolean s){
		maxspeed = s;
		if(l!=null)l.setMaxSpeed(maxspeed);
	}
	public void setAI(boolean AI){
		this.AI = AI;
		if(l!=null)l.setAI(AI);
	}
	public void setNames(String[] r){
		player1 = r[0];
		player2 = r[1];
	}
	public Vector<Vector<Object>> getHighscores(){
		Vector<Vector<Object>> scoredata = new Vector<Vector<Object>>();
		File file = new File("Highscores.dat");
		try{
			FileInputStream filein = new FileInputStream (file);
			ObjectInputStream ois = new ObjectInputStream(filein);
			scoredata= readObject(ois);
		}catch(IOException e){}
	    catch(ClassNotFoundException ce){}
	    return scoredata;
	}
	 private Vector<Vector<Object>> readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		 Vector<Vector<Object>> o = new Vector<Vector<Object>>();
		 o.add(new Vector<Object>());
		 o.add(new Vector<Object>());
		 o.add(new Vector<Object>());
	     for(int i = 0; i<10; i++){
	    	 o.elementAt(0).add(stream.readInt());
	    	 o.elementAt(1).add(stream.readObject());
	    	 o.elementAt(2).add(stream.readInt());
	     }
	  	return o;	
	 }
	 public void setHighscores(int player1score, int player2score){
		Vector<Vector<Object>> scoredata = new Vector<Vector<Object>>();
		File file = new File("Highscores.dat");
		try{
			FileInputStream filein = new FileInputStream (file);
			ObjectInputStream ois = new ObjectInputStream(filein);
			scoredata= readObject(ois);
		}catch(IOException e){}
		catch(ClassNotFoundException ce){}
		if(!AI){
			for(int i = 0; i<10; i++){
				if(((Integer)scoredata.elementAt(2).elementAt(i)).intValue()<player1score){
					for(int j = 9; j>i+1; j--){
						scoredata.elementAt(1).setElementAt(scoredata.elementAt(1).elementAt(j-1), j);
						scoredata.elementAt(2).setElementAt(scoredata.elementAt(2).elementAt(j-1), j);
					}
					scoredata.elementAt(1).setElementAt(player1, i);
					scoredata.elementAt(2).setElementAt(player1score, i);
					i = 10;
				}
			}
		}
		for(int i = 0; i<10; i++){
			if(((Integer)scoredata.elementAt(2).elementAt(i)).intValue()<player2score){
				for(int j = 9; j>=i+1; j--){
					scoredata.elementAt(1).setElementAt(scoredata.elementAt(1).elementAt(j-1), j);
					scoredata.elementAt(2).setElementAt(scoredata.elementAt(2).elementAt(j-1), j);
				}
				scoredata.elementAt(1).setElementAt(player2, i);
				scoredata.elementAt(2).setElementAt(player2score, i);
				i = 10;
			}
		}
		try{
			FileOutputStream filein = new FileOutputStream (file);
			ObjectOutputStream oos = new ObjectOutputStream(filein);
			writeObject(oos, scoredata);
		}catch(IOException e){}
	 }
	 private void writeObject(java.io.ObjectOutputStream stream, Vector<Vector<Object>>data) throws IOException{
		 for(int i = 0; i<10; i++ ){
			 stream.writeInt(((Integer)data.elementAt(0).elementAt(i)).intValue());
			 stream.writeObject(data.elementAt(1).elementAt(i));
			 stream.writeInt(((Integer)data.elementAt(2).elementAt(i)).intValue());
		 }
		 stream.close();
	 }
	 private void resetHighscores(){
		 Object[][] o = {{1, "Antti the Beholder", 200},
				 {2, "Juha the tester", 100},
				 {3, "Jorma", 80},
				 {4, "Irma", 70},
				 {5, "Jussi", 60},
				 {6, "Leena", 50},
				 {7, "Uuno", 40},
				 {8, "Auvo", 30},
				 {9, "Iines", 20},
				 {10, "Petteri", 10}};
		 Vector<Object> rank = new Vector<Object>();
		 Vector<Object> name = new Vector<Object>();
		 Vector<Object> score = new Vector<Object>();
		 for(int i = 0; i<10; i++){
			 rank.add(o[i][0]);
			 name.add(o[i][1]);
			 score.add(o[i][2]);
		 }
		 Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		 data.add(rank);
		 data.add(name);
		 data.add(score);
		 File file = new File("Highscores.dat");
		 try{
			 	file.createNewFile();
				FileOutputStream filein = new FileOutputStream (file);
				ObjectOutputStream oos = new ObjectOutputStream(filein);
				writeObject(oos, data);
			}catch(IOException e){}
	 }
	 public void setTwoballs(boolean b){
		 twoballs = b;
	 }
	 public void setSound(){
		 sound = !sound;
	 }
	 public void playSound(int w){
		 if(sound){
			 if(w==1)Sound.COLLISION.play();
			 if(w==2)Sound.BREAK.play();
			 if(w==3)Sound.INDESTRUCTIBLE.play();
		 }
	 }
	 public void turnVolumeUp(){
		 if(volume == 1){
			 Sound.volume = Sound.Volume.MEDIUM;
			 volume++;
		 }
		 else{
			 if(volume == 2)volume++;
			 Sound.volume = Sound.Volume.HIGH;
		 }
	 }
	 public void turnVolumeDown(){
		 if(volume == 3){
			 Sound.volume = Sound.Volume.MEDIUM;
			 volume--;
		 }
		 else{
			 if(volume == 2)volume--;
			 Sound.volume = Sound.Volume.LOW;
		 }
	 }
}