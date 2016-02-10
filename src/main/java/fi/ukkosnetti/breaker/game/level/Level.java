package fi.ukkosnetti.breaker.game.level;


import java.util.*;
import java.io.*;
public class Level implements Serializable{
	private static final long serialVersionUID = 3443642997627411431L;
	private List<Box> boxes;
	public Level(){
		boxes = new ArrayList<Box>();
	}
	public Level(int i){
		
	}
	public Level(List<Box> boxes){
		this.boxes = boxes;
	}
	public void addBox(Box b){
		boxes.add(b);
	}
	public List<Box> getBoxes(){
		return boxes;
	}
	/*
	 * removeBox is used by editor only
	 */
	public void removeBox(Box b){
		boxes.remove(b);
	}
	public void writeLevel(String n){
		try{
    		this.writeObject(n);
    	}catch(IOException ioe){
    		ioe.printStackTrace();
    	}
	}
	public Level readLevel(String n){
		Level ma = null;
    	try {
    		ma = this.readObject(n);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	return ma;
	}
	private void writeObject(String file)throws IOException{
    	FileOutputStream fos = new FileOutputStream("Levels/"+file+".map");
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(this);
    	oos.close();
    	fos.close();
    }
	private Level readObject(String n)throws IOException, ClassNotFoundException{
    	ObjectInputStream ois = null;
    	Level ma = null; 
        //Construct the ObjectInputStream object
    	try{
    		ois = new ObjectInputStream(new FileInputStream("Levels/"+n+".map"));
    		Object obj = null;
    		if((obj = ois.readObject())!= null){
    			if (obj instanceof Level) {
    	            ma = (Level)obj;
    	        }
            }
    	}catch (EOFException ex) { ex.printStackTrace();}
    	//This exception will be caught when EOF is reached
        if (ois != null) {
            ois.close();
        }
    	return ma;
    }
}
