package editor;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import breaker.*;
public class EditorController extends MouseAdapter implements ActionListener, MouseMotionListener, KeyListener{
	private Editor e;
	private DrawingArea da;
	private Box box;
	private SaveMap sm;
	private LoadMap lm;
	private boolean moving;
	private boolean copying;
	public EditorController(Editor e){
		this.e = e;
		moving = false;
		copying = false;
	}
	public void setDrawingArea(DrawingArea da){
		this.da = da;
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("RESET")){
			e = new Editor();
			da.setBoxes(e.getBoxes());
			moving = false;
			copying = false;
			box = null;
			da.setActiveBox(box);
		}
		if(ae.getActionCommand().equals("SAVE")){
			sm = new SaveMap(this);
		}
		if(ae.getActionCommand().equals("LOAD")){
			ArrayList<File> files = e.getSavedLevels();
			if(!files.isEmpty()){
				lm = new LoadMap(files,this);
			}else{
				da.setReason("No .map files in Levels folder");
			}
		}
		if(ae.getActionCommand().equals("EXIT")){
			System.exit(0);
		}
		if(ae.getActionCommand().equals("SAVETHIS")){
			e.saveMap(sm.getSavedName());
			sm.dispose();
		}
		if(ae.getActionCommand().equals("LOADTHIS")){
			da.setBoxes(e.loadMap(lm.getSelectedFile()));
			lm.dispose();
		}
		if(ae.getActionCommand().equals("ABOUT")){
			new EditorAbout();
		}
		if(ae.getActionCommand().equals("HELP")){
			try                                      //try statement
	        {
				String command = "rundll32 url.dll,FileProtocolHandler " + "Editor.pdf";
				Runtime.getRuntime().exec(command);

	        } catch (Exception e)                    //catch any exceptions here
	          {
	              System.out.println("Error: " + e );  //print the error
	          }
		}
	}
	public void mouseReleased(MouseEvent me){
		if(box != null){
			if(!moving||copying){
				e.addBox(box, me.getPoint());
			}else{
				e.moveBox(me.getPoint());
			}
		}
		da.setBoxes(e.getBoxes());
		da.setReason(e.getReason());
		box = null;
		da.setActiveBox(box);
		moving = false;
	}
	public void mousePressed(MouseEvent me){
		this.getColorBox(me.getPoint());
		if(box!=null)da.setActiveBox(box);
	}
	public void mouseMoved(MouseEvent me){
		Point p = me.getPoint();
		da.setCoordinates(p.x, p.y);
	}
	public void mouseDragged(MouseEvent me){
		Point p = me.getPoint();
		da.setCoordinates(p.x, p.y);
		if(box!= null) box.setPosition(p);
	}
	private void getColorBox(Point p){
		boolean found = false;
		for(int i = 0; i<12 && !found; i++){
			Rectangle2D rect = new Rectangle2D.Double(i*20, 0, 20, 40);
			if(rect.contains((Point2D)p)){
				switch(i){
					case 0: 
						box = new GreenBox(p.x, p.y);
						found = true;
						break;
					case 1: 
						box = new YellowBox(p.x, p.y);
						found = true;
						break;
					case 2: 
						box = new RedBox(p.x, p.y);
						found = true;
						break;
					case 3: 
						box = new GreyBox(p.x, p.y);
						found = true;
						break;
					case 4: 
						box = new BlueBox(p.x, p.y);
						found = true;
						break;
					case 5: 
						box = new TealBox(p.x, p.y);
						found = true;
						break;
					case 6: 
						box = new GoldBox(p.x, p.y);
						found = true;
						break;
					case 7: 
						box = new OrangeBox(p.x, p.y);
						found = true;
						break;
					case 8: 
						box = new VioletBox(p.x, p.y);
						found = true;
						break;
					case 9: 
						box = new PinkBox(p.x, p.y);
						found = true;
						break;
					case 10: 
						box = new WhiteBox(p.x, p.y);
						found = true;
						break;
					case 11: 
						box = new BlackBox(p.x, p.y);
						found = true;
						break;
				}
			}
		}
		if(!found){
			box = e.getBox(p);
			if(box!=null)moving = true;
		}
	}
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_DELETE){
			if(box!= null){
				e.removeBox();
				box = null;
				da.setActiveBox(box);
				da.setBoxes(e.getBoxes());
				moving = false;
			}
		}
		if(ke.getKeyCode() == KeyEvent.VK_CONTROL){
			copying = true;
		}
	}
	public void keyReleased(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_CONTROL){
			copying = false;
		}
	}
	public void keyTyped(KeyEvent arg0) {}
}
