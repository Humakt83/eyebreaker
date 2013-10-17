package breaker;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;
public class DrawingArea extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2264850270912438048L;
	private Font scorefont = new Font("Impact", Font.BOLD, 20);
	private Font gameover = new Font("Impact", Font.BOLD, 50);
	private int ballY;
	private int ballX;
	private int ballR = 2;
	private int ballY2;
	private int ballX2;
	private int ballR2 = 2;
	private boolean twoballs = false;;
	private int bat1Y;
	private int bat2Y;
	private int batsleft = 3;
	private Box[] boxes;
	private int bat1height;
	private int bat2height;
	private int scoreplayer2 = 0;
	private int scoreplayer1 = 0;
	private String nameplayer1 = "Player 1";
	private String nameplayer2 = "Player 2";
	private String scoreString = "0 - 0";
	private boolean pong = false, vs = false;
	private ImageIcon backgroundimage = new ImageIcon("bg.png");
	private Color batBorderColor = new Color(255,255,255);
	private GradientPaint batPaint = new GradientPaint(
		    0, 0, new Color(150,0,255),
		    0, 50, new Color(0,0,0), true );
	private GradientPaint batScorePaint = new GradientPaint(
		    150, 570, new Color(150,0,255),
		    175, 570, new Color(0,0,0), true );
	private GradientPaint borderPaint = new GradientPaint(
		    0, 0, new Color(150,100,0),
		    300, 0, new Color(100,150,0), true );
	private GradientPaint scorePanelPaint = new GradientPaint(
		    0, 560, new Color(100,0,100),
		    1500, 0, new Color(250,0,250));
	private boolean over = false;
	private int ballLine = 0;
	private Vector<ScoreSphere> spheres = new Vector<ScoreSphere>();
	private boolean hotRed = false;
	private boolean antia = true;
	
	
	public DrawingArea() {
		this.setPreferredSize(new Dimension(800,590));
		this.setDoubleBuffered(true);
	  }
	public void setTwoballs(boolean two){
		twoballs = two;
	}
	public void updateBall(int x, int y, int r, boolean second){
		if(!second){
			ballY = y;
			ballX = x;
			ballR = r;
		}else{
			ballY2 = y;
			ballX2 = x;
			ballR2 = r;
		}
		repaint();
	}
	public void updateBat(int y, int h, boolean left){
		if(left){
			bat1Y = y;
			bat1height = h;
		}
		else{
			bat2Y = y;
			bat2height = h;
		}
	}
	public void gameOver(boolean over){
		this.over = over;
		repaint();
	}
	public void updateScore(int score1, int score2){
		scoreplayer1 +=score1;
		scoreplayer2 +=score2;
		scoreString = nameplayer1 + "   " + scoreplayer1 +" - " + scoreplayer2 + "   " +nameplayer2;
	}
	public void updateScoreBox(int score1, int score2){
		scoreplayer1 = score1;
		scoreplayer2 = score2;
	}
	public void resetScore(){
		scoreplayer1 = 0;
		scoreplayer2 = 0;
		scoreString = nameplayer1 + "   " + scoreplayer1 +" - " + scoreplayer2 + "   " +nameplayer2;
	}
	public void setNames(String p1, String p2){
		nameplayer1 = p1;
		nameplayer2 = p2;
	}
	public void setPong(boolean pong, boolean vs){
		this.pong = pong;
		this.vs = vs;
	}
	public void setBoxes(Box[] boxes){
		this.boxes = boxes;
	}
	public void setBatsLeft(int b){
		batsleft = b;
	}
	public void setAA(boolean a){
		antia = a;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Ellipse2D.Double circle1 = new Ellipse2D.Double(ballX, ballY, ballR, ballR);
		Graphics2D g2d = (Graphics2D)g;
		if(antia)g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		else g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		Dimension d = this.getSize();
		g2d.drawImage(backgroundimage.getImage(), 0, 0, d.width, d.height, null);
		//drawing ball
		if(!hotRed)g2d.setColor(Color.WHITE);
		else g2d.setColor(Color.RED);
		g2d.fill(circle1);
		if(ballLine<180)g2d.setPaint(new GradientPaint(
			    ballX, ballY, Color.GREEN,
			    ballX, ballY+ballR, Color.BLUE, true ));
		else g2d.setPaint(new GradientPaint(
			    ballX, ballY, Color.BLUE,
			    ballX, ballY+ballR, Color.GREEN, true ));
		g2d.fillArc(ballX, ballY, ballR, ballR, ballLine, 125);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(ballX, ballY, ballR, ballR, ballLine+35, 30);
		
		//drawing second ball if it exists
		if(twoballs){
			Ellipse2D.Double circle2 = new Ellipse2D.Double(ballX2, ballY2, ballR2, ballR2);
			if(!hotRed)g2d.setColor(Color.WHITE);
			else g2d.setColor(Color.RED);
			g2d.fill(circle2);
			if(ballLine<180)g2d.setPaint(new GradientPaint(
				    ballX2, ballY2, Color.RED,
				    ballX2, ballY2+ballR2, Color.YELLOW, true ));
			else g2d.setPaint(new GradientPaint(
				    ballX2, ballY2, Color.YELLOW,
				    ballX2, ballY2+ballR2, Color.RED, true ));
			g2d.fillArc(ballX2, ballY2, ballR2, ballR2, ballLine, 125);
			g2d.setColor(Color.BLACK);
			g2d.fillArc(ballX2, ballY2, ballR2, ballR2, ballLine+35, 30);
		}
		ballLine+=2;
		if(ballLine>=360){
			ballLine=0;
		}
		//drawing borders
		
		g2d.setPaint( borderPaint );
		g2d.fillRect( 0, 0, 1500,10 );
		g2d.fillRect(0, 540, 1500, 20);
		//drawing bats
		if(bat1height>0){
			g2d.setPaint( batPaint );
			g2d.fillRect(50, bat1Y, 20, bat1height);
			g2d.fillRect(750, bat2Y, 20, bat2height);
			g2d.setColor(batBorderColor);
			g2d.drawRect(50, bat1Y, 20, bat1height);
			g2d.drawRect(750, bat2Y, 20, bat2height);
		}
		//drawing batpanel/scorepanel
		g2d.setPaint(scorePanelPaint);
		g2d.fillRect(0, 560, 1500, 40);
		if(pong){
			g.setColor(new Color(255,255,255));
			g.setFont(scorefont);
			g.drawString(scoreString, 320, 585);
		}
		if(!pong || vs){
			if(!vs){
				g.setColor(new Color(255,255,255));
				g.setFont(scorefont);
				g.drawString(nameplayer1 + "  " + scoreplayer1, 20, 585);
				g.drawString(scoreplayer2 + "  "+ nameplayer2  , 680, 585);
				for(int n = 0; n<batsleft; n++){
					g2d.setPaint(batScorePaint);
					g2d.fillRect(150+(n*50), 570, 40, 15);
					g2d.setColor(batBorderColor);
					g2d.drawRect(150+(n*50), 570, 40, 15);
				}
			}
			if(boxes!=null){
				for(int i = 0; i<boxes.length; i++){
					if(!(boxes[i].getDestroyed())){
						switch(boxes[i].getColor()){
							case 0: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(0,255,0),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(0,155,0)));
								break;
							case 1: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(255,255,0),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(155,155,0)));
								break;
							case 2: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(255,0,0),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(55,0,0)));
								break;
							case 3: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(155,155,155),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(55,55,55)));
								break;
							case 4: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(0,0,255),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(0,0,55)));
								break;
							case 5: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(0,255,255),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(224,255,255)));
								break;
							case 6: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(139,69,19),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(205,133,63)));
								break;
							case 7: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(255,165,0),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(255,99,71)));
								break;
							case 8: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(148,0,211),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(147,112,219)));
								break;
							case 9: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(255,182,193),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(255,105,180)));
								break;
							case 10: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(255,255,255),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(200,200,200)));
								break;
							case 11: 
								g2d.setPaint(new GradientPaint(boxes[i].getPositionX(), boxes[i].getPositionY(), new Color(0,0,0),
										boxes[i].getPositionX(), boxes[i].getPositionY() + 40, new Color(45,45,45)));
								break;
						}
						g2d.fillRect(boxes[i].getPositionX(), boxes[i].getPositionY(), 20, 40);
						g2d.setColor(Color.BLACK);
						g2d.drawRect(boxes[i].getPositionX(), boxes[i].getPositionY(), 20, 40);
					}
				}
			}
		}
		if(!spheres.isEmpty()){
			for(int i=0; i<spheres.size(); i++){
				int x = spheres.elementAt(i).getPositionX(), y = spheres.elementAt(i).getPositionY();
				switch(spheres.elementAt(i).getEffect()){
					case 0:
						g2d.setPaint(new GradientPaint(0, y,Color.BLUE,
								20, y, Color.YELLOW, true));
						break;
					case 1:
						g2d.setPaint(new GradientPaint(0, y,Color.YELLOW,
								20, y, Color.GREEN, true));
						break;
					case 2:
						g2d.setPaint(new GradientPaint(0, y,Color.BLACK,
								20, y, Color.GREEN, true));
						break;
					case 3:
						g2d.setPaint(new GradientPaint(0, y,Color.BLUE,
								20, y, Color.RED, true));
						break;
					case 4:
						g2d.setPaint(new GradientPaint(0, y,Color.BLACK,
								20, y, Color.RED, true));
						break;
					case 5:
						g2d.setPaint(new GradientPaint(0, y,Color.RED,
								20, y, Color.ORANGE, true));
						break;
					case 6:
						g2d.setPaint(new GradientPaint(0, y,Color.DARK_GRAY,
								20, y, Color.BLACK, true));
						break;
					default:
						break;
				}
						
				g2d.fillOval(x, y, 20, 30);
			}
		}
		if(over && !vs){
			g.setColor(Color.RED);
			g.setFont(gameover);
			g.drawString("GAME OVER!", 300, 200);
		}
	}
	public void setSpheres(Vector<ScoreSphere> spheres) {
		this.spheres = spheres;
	}
	public void setHotRed(boolean hotRed) {
		this.hotRed = hotRed;
	}
}
