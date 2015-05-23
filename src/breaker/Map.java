package breaker;


import java.util.*;
import java.io.*;
public class Map implements Serializable{
	private static final long serialVersionUID = 3443642997627411431L;
	private List<Box> boxes;
	public Map(){
		boxes = new ArrayList<Box>();
	}
	public Map(int i){
		
	}
	public Map(List<Box> boxes){
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
	public Map readLevel(String n){
		Map ma = null;
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
	private Map readObject(String n)throws IOException, ClassNotFoundException{
    	ObjectInputStream ois = null;
    	Map ma = null; 
        //Construct the ObjectInputStream object
    	try{
    		ois = new ObjectInputStream(new FileInputStream("Levels/"+n+".map"));
    		Object obj = null;
    		if((obj = ois.readObject())!= null){
    			if (obj instanceof Map) {
    	            ma = (Map)obj;
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
