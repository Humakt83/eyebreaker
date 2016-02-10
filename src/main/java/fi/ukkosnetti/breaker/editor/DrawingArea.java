package fi.ukkosnetti.breaker.editor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import fi.ukkosnetti.breaker.game.level.Box;
import fi.ukkosnetti.breaker.resource.ImageResource;

import java.util.*;
public class DrawingArea extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private String reason;
	private static final long serialVersionUID = 4320380334661237698L;
	private Box activeBox;
	private int x = 0, y = 0, amount = 0;
	private List<Box> boxes;
	public DrawingArea(){
		boxes = new ArrayList<Box>();
		reason = "";
		this.setPreferredSize(new Dimension(800, 570));
		this.setDoubleBuffered(true);
		new Thread(this).start();
	}
	public void setController(EditorController c){
		this.addMouseListener(c);
		this.addMouseMotionListener(c);
		this.setRequestFocusEnabled(true);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(c);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Dimension d = this.getSize();
		g2d.drawImage(ImageResource.BACKGROUND.getImage(), 0, 0, d.width, d.height, null);
		g2d.setPaint(new GradientPaint(0, 0, new Color(200,200,200),
				0, 40, new Color(150,150,150)));
		g2d.fillRect(0, 0, d.width, 40);
		for(int i = 0; i<12; i++){
			this.setBoxPaint(i, g2d, i*20, 0);
			g2d.fillRect(i*20, 0, 20, 40);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(i*20, 0, 20, 40);
		}
		Iterator<Box> ite = boxes.iterator();
		while(ite.hasNext()){
			Box b = ite.next();
			int xx = b.getPositionX(), yy = b.getPositionY();
			this.setBoxPaint(b.getColor(), g2d, xx, yy);
			g2d.fillRect(xx, yy, 20, 40);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(xx, yy, 20, 40);
		}
		if(activeBox != null){
			this.setBoxPaint(activeBox.getColor(), g2d, x, y);
			g2d.fillRect(x, y, 20, 40);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y, 20, 40);
		}
		g2d.setColor(Color.WHITE);
		g2d.drawString("x: " + x + ", y: " + y + " boxes: " + amount, 10, 560);
		g2d.drawString(reason, 200, 560);
	}
	public void run(){
		while(true){
			this.repaint();
			try{
				Thread.sleep(2);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void setReason(String s){
		reason = s;
	}
	public void setActiveBox(Box b){
		activeBox = b;
	}
	public void setBoxes(List<Box> b){
		boxes = b;
		amount = boxes.size();
	}
	public void setCoordinates(double x, double y){
		this.x = (int)x;
		this.y = (int)y;
	}
	public void addBox(Box b){
		boxes.add(b);
		amount++;
	}
	private Graphics2D setBoxPaint(int i, Graphics2D g2d, int xx, int yy){
		switch(i){
			case 0: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(0,255,0),
						xx, yy+40, new Color(0,155,0)));
				break;
			case 1: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(255,255,0),
						xx, yy+40, new Color(155,155,0)));
				break;
			case 2: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(255,0,0),
						xx, yy+40, new Color(55,0,0)));
				break;
			case 3: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(155,155,155),
						xx, yy+40, new Color(55,55,55)));
				break;
			case 4: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(0,0,255),
						xx, yy+40, new Color(0,0,55)));
				break;
			case 5: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(0,255,255),
						xx, yy+40, new Color(224,255,255)));
				break;
			case 6: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(139,69,19),
						xx, yy+40, new Color(205,133,63)));
				break;
			case 7: 
				g2d.setPaint(new GradientPaint(x, yy, new Color(255,165,0),
						x, yy+40, new Color(255,99,71)));
				break;
			case 8: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(148,0,211),
						xx, yy+40, new Color(147,112,219)));
				break;
			case 9: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(255,182,193),
						xx, yy+40, new Color(255,105,180)));
				break;
			case 10: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(255,255,255),
						xx, yy+40, new Color(200,200,200)));
				break;
			case 11: 
				g2d.setPaint(new GradientPaint(xx, yy, new Color(0,0,0),
						xx, yy+40, new Color(45,45,45)));
				break;
		}
		return g2d;
	}
}
