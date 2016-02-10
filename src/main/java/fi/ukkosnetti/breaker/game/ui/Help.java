package fi.ukkosnetti.breaker.game.ui;
import java.awt.*;
import javax.swing.*;

import fi.ukkosnetti.breaker.resource.ImageResource;
public class Help extends JFrame{
	private static final long serialVersionUID = 8027531720754221869L;
	private ImageIcon backgroundimage = ImageResource.HELP;
	private JPanel topPanel = new JPanel();
	private JPanel drawingpanel = new JPanel(){
		static final long serialVersionUID = 1;
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(backgroundimage.getImage(), 0, 0, null);
			Dimension d = new Dimension(600, 900);
			g.drawImage(backgroundimage.getImage(), 0, 0, d.width, d.height, null);
		}
	};
	private JScrollPane scroll = new JScrollPane();
	public Help(){
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
		drawingpanel.setPreferredSize(new Dimension(600,900));
		scroll.setWheelScrollingEnabled(true);
		scroll.setViewportView(drawingpanel);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		topPanel.add( scroll, BorderLayout.CENTER );
		this.add(topPanel);
		this.setSize(625, 500);
		this.setTitle("Help");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(ImageResource.LOGO.getImage());
		this.setVisible(true);
	}
}
