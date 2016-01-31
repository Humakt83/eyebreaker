package fi.ukkosnetti.breaker.editor;
import fi.ukkosnetti.breaker.game.level.Box;
import fi.ukkosnetti.breaker.game.level.Level;

import java.io.File;
import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
public class Editor {
	private List<Box> boxes;
	private int amount;
	private String reason;
	private Box movingBox;
	public Editor(){
		reason = "";
		boxes = new ArrayList<Box>();
		amount = 0;
	}
	public boolean addBox(Box b, Point p){
		if(p.x>100 && p.x<720){
			if(p.y>40 && p.y<=530){
				Iterator<Box> ite = boxes.iterator();
				while(ite.hasNext()){
					Box help = ite.next();
					Rectangle2D area = new Rectangle2D.Double(help.getPositionX(), help.getPositionY(), 20, 40);
					Rectangle2D area2 = new Rectangle2D.Double(b.getPositionX(), b.getPositionY(), 20, 40);
					if(area.intersects(area2)){
						reason = "Box is on top of another.";
						return false;
					}
				}
				boxes.add(b);
				reason = "";
				if(!b.isIndestructible())amount++;
				return true;
			}else reason = "Box is outside level.";
		}else reason = "Box is either outside level or on the area where bats move.";
		return false;
	}
	public boolean moveBox(Point p){
		if(p.x>100 && p.x<720){
			if(p.y>40 && p.y<=530){
				Iterator<Box> ite = boxes.iterator();
				while(ite.hasNext()){
					Box help = ite.next();
					if(!movingBox.equals(help)){
						Rectangle2D area = new Rectangle2D.Double(help.getPositionX(), help.getPositionY(), 20, 40);
						Rectangle2D area2 = new Rectangle2D.Double(p.x, p.y, 20, 40);
						if(area.intersects(area2)){
							reason = "Box is on top of another.";
							return false;
						}
					}
				}
				movingBox.setPosition(p);
				reason = "";
				return true;
				
			}else reason = "Box is outside level.";
		}else reason = "Box is either outside level or on the area where bats move.";
		return false;
	}
	public Box getBox(Point p){
		Iterator<Box> ite = boxes.iterator();
		while(ite.hasNext()){
			movingBox = ite.next();
			Rectangle2D area = new Rectangle2D.Double(movingBox.getPositionX(), movingBox.getPositionY(), 20, 40);
			if(area.contains(p)){
				Box boxret = (Box)movingBox.clone();
				boxes.remove(boxret);
				return boxret;
			}
		}
		return null;
	}
	public List<Box> getBoxes(){
		return boxes;
	}
	public String getReason(){
		String s = reason;
		reason = "";
		return s;
	}
	public int getAmount(){
		return amount;
	}
	public ArrayList<File> getSavedLevels(){
		ArrayList<File> files = new ArrayList<File>();
		File folder = new File("Levels");
		File[] listOfFiles = folder.listFiles();
		for (int i=0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName();
				if(name.contains(".map") && listOfFiles[i].canWrite()){
					files.add(listOfFiles[i]);
				}
			}
		}
		return files;
	}
	public void saveMap(String s){
		Level m = new Level(boxes);
		m.writeLevel(s);
	}
	public List<Box> loadMap(String s){
		Level m = new Level();
		Level nm = m.readLevel(s);
		boxes = nm.getBoxes();
		System.out.println(boxes.size());
		return boxes;
	}
	public void removeBox(){
		boxes.remove(movingBox);
		movingBox = null;
	}
}
