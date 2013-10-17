package breaker;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
public class View extends JFrame{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6839961070670630073L;
	//GUI components and class variables
	private DrawingArea gamearea = new DrawingArea();
	private JMenuBar menubar = new JMenuBar(){
		private static final long serialVersionUID = 5751704525693665233L;

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			this.setBackground(Color.BLUE);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(new GradientPaint(0,0,new Color(105,25,255),this.getWidth(),0, Color.BLUE));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	};
	private JMenu filemenu = new JMenu("Game");
	private JMenuItem newgame = new PurpleMenuItem("New Game");
	private JMenuItem newvsgame = new PurpleMenuItem("New Versus Game");
	private JMenuItem newgame2 = new PurpleMenuItem("New Game of Pong");
	private JMenuItem highscores = new PurpleMenuItem("Highscores");
	private JMenuItem exitgame = new PurpleMenuItem("Exit");
	private JMenu setupmenu = new JMenu("Settings");
	private JMenuItem players = new PurpleMenuItem("Player Names");
	private JMenuItem ai = new PurpleMenuItem("Turn AI On");
	private JMenuItem maxspeed = new PurpleMenuItem("Turn Maximum Speed On");
	private JMenuItem twoballs = new PurpleMenuItem("Two-ball Game");
	private JMenu helpmenu = new JMenu("Help");
	private JMenuItem help = new PurpleMenuItem("Help");
	private JMenuItem about = new PurpleMenuItem("About");
	private JMenu sound = new JMenu("Sound");
	private JMenuItem soundon = new PurpleMenuItem("Turn Sound Off");
	private JMenuItem soundlow = new PurpleMenuItem("Turn Volume Up");
	private JMenuItem soundhigh = new PurpleMenuItem("Turn Volume Down");
	private JMenuItem antia = new PurpleMenuItem("Anti-aliasing Off");
	//JFrame components for player name change view:
	private JFrame setplayers = new JFrame("Player Names");
	private JPanel playerpanel = new JPanel(){
		private static final long serialVersionUID = 3061393611274911826L;

		public void paintComponent(Graphics g){
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(new GradientPaint(0,0, Color.BLACK, this.getWidth(), 0, Color.GREEN));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	};
	private JLabel player1 = new JLabel("Player 1: ");
	private JLabel player2 = new JLabel("Player 2: ");
	private JTextField player1name = new JTextField("Player 1", 15);
	private JTextField player2name = new JTextField("Player 2", 15); 
	private JButton confirmplayerchanges = new JButton("OK");
	private ImageIcon eye = new ImageIcon("Eye.png");
	//JFrame components for Highscore view:
	private JFrame highscoreview = new JFrame("Highscores");
	private JPanel scorepanel = new JPanel(){
		private static final long serialVersionUID = 6348911899640556132L;

		public void paintComponent(Graphics g){
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(new GradientPaint(0,0, Color.BLACK, this.getWidth(), 0, Color.GREEN));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	};
	private JTable highscorestable;
	private Font scorefont = new Font("Impact", Font.PLAIN, 16);
	
	public View(){
		//forming highscore view components
		scorepanel.setBackground(Color.BLACK);
		highscoreview.add(scorepanel);
		highscoreview.setDefaultCloseOperation(HIDE_ON_CLOSE);
		highscoreview.setSize(270,220);
		highscoreview.setResizable(false);
		// forming player name view components
		player1.setOpaque(false);
		player2.setOpaque(false);
		player1.setForeground(Color.WHITE);
		player2.setForeground(Color.WHITE);
		playerpanel.setLayout(new FlowLayout());
		playerpanel.add(player1);
		playerpanel.add(player1name);
		playerpanel.add(player2);
		playerpanel.add(player2name);
		playerpanel.add(confirmplayerchanges);
		playerpanel.setBackground(Color.BLACK);
		setplayers.add(playerpanel);
		setplayers.setDefaultCloseOperation(HIDE_ON_CLOSE);
		setplayers.setSize(250, 110);
		setplayers.setResizable(false);
		//forming main view
		this.add(gamearea);
		filemenu.setBackground(new Color(105,25,255));
		filemenu.setForeground(Color.WHITE);
		filemenu.add(newgame);
		filemenu.add(newvsgame);
		filemenu.add(newgame2);
		filemenu.addSeparator();
		filemenu.add(highscores);
		filemenu.addSeparator();
		filemenu.add(exitgame);
		sound.setOpaque(true);
		sound.setBackground(new Color(105,25,255));
		sound.setForeground(Color.WHITE);
		sound.add(soundon);
		sound.addSeparator();
		sound.add(soundlow);
		sound.add(soundhigh);
		setupmenu.setBackground(new Color(105,25,255));
		setupmenu.setForeground(Color.WHITE);
		setupmenu.add(players);
		setupmenu.addSeparator();
		setupmenu.add(sound);
		setupmenu.addSeparator();
		setupmenu.add(ai);
		setupmenu.add(maxspeed);
		setupmenu.add(twoballs);
		setupmenu.addSeparator();
		setupmenu.add(antia);
		helpmenu.setBackground(new Color(105,25,255));
		helpmenu.setForeground(Color.WHITE);
		helpmenu.add(help);
		help.setMnemonic(Event.F11);
		helpmenu.addSeparator();
		helpmenu.add(about);
		menubar.add(filemenu);
		menubar.add(setupmenu);
		menubar.add(helpmenu);		
		this.setJMenuBar(menubar);
		this.setSize(820,645);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		this.setResizable(false);
		this.setTitle( "EyeBreaker" );
		this.setLayout(new GridLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(eye.getImage());
		this.setVisible(true);
		//setting area for player name view
		frameSize = setplayers.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
		setplayers.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setplayers.setIconImage(eye.getImage());
		//setting area for highscore view
		frameSize = highscoreview.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
		highscoreview.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		highscoreview.setIconImage(eye.getImage());
	}
	//Function for setting actioncommands and actionlisteners
	public void setController(Controller c){
		ai.setActionCommand("AI");
		soundon.setActionCommand("SOUND");
		soundlow.setActionCommand("SOUNDDOWN");
		soundhigh.setActionCommand("SOUNDUP");
		twoballs.setActionCommand("TWO");
		maxspeed.setActionCommand("SPEED");
		exitgame.setActionCommand("EXIT");
		newgame.setActionCommand("NEW");
		newvsgame.setActionCommand("VERSUS");
		newgame2.setActionCommand("NEW2");
		confirmplayerchanges.setActionCommand("PLAYERCHANGES");
		players.setActionCommand("PLAYERS");
		about.setActionCommand("ABOUT");
		help.setActionCommand("HELP");
		antia.setActionCommand("AA");
		highscores.setActionCommand("HIGHSCORES");
		about.addActionListener(c);
		help.addActionListener(c);
		confirmplayerchanges.addActionListener(c);
		maxspeed.addActionListener(c);
		players.addActionListener(c);
		newgame.addActionListener(c);
		newvsgame.addActionListener(c);
		newgame2.addActionListener(c);
		exitgame.addActionListener(c);
		ai.addActionListener(c);
		highscores.addActionListener(c);
		twoballs.addActionListener(c);
		soundon.addActionListener(c);
		soundlow.addActionListener(c);
		soundhigh.addActionListener(c);
		antia.addActionListener(c);
		//Passes drawing area to controller
		c.setDrawingArea(gamearea);
		this.addKeyListener(c);
	}
	//Next four methods change certain menu-items' text
	public void setAI(boolean a){
		if(a)ai.setText("Turn AI Off");
		else ai.setText("Turn AI On");
	}
	public void setSound(){
		if(soundon.getText().equals("Turn Sound Off"))soundon.setText("Turn Sound On");
		else soundon.setText("Turn Sound Off");
	}
	public void setSpeed(boolean s){
		if(s)maxspeed.setText("Turn Maximum Speed Off");
		else maxspeed.setText("Turn Maximum Speed On");
	}
	public void setTwo(boolean s){
		if(s)twoballs.setText("One-ball Game");
		else twoballs.setText("Two-ball Game");
	}
	public void setAA(boolean s){
		if(s)antia.setText("Anti-aliasing On");
		else antia.setText("Anti-aliasing Off");
	}
	//Shows frame for setting player names
	public void viewPlayers(){
		setplayers.setVisible(true);
	}
	//Shows highscores view
	public void viewHighScores(Vector<Vector<Object>> vec){
		scorepanel.removeAll();
		String[] columnnames = {"Rank", "Name", "Score"};
		Object data[][] = new Object[10][3];
		for(int i = 0; i<vec.elementAt(0).size(); i++){
			data[i][0] = vec.elementAt(0).elementAt(i);
			data[i][1] = vec.elementAt(1).elementAt(i);
			data[i][2] = vec.elementAt(2).elementAt(i);
		}
		//Creating table and customizing it:
		highscorestable = new JTable(data, columnnames);
		highscorestable.setFont(scorefont);
		highscorestable.setShowGrid(true);
		highscorestable.setRowSelectionAllowed(false);
		highscorestable.setColumnSelectionAllowed(false);
		highscorestable.setEnabled(false);
		highscorestable.setBackground(Color.RED);
		highscorestable.setForeground(Color.WHITE);
		highscorestable.getColumnModel().getColumn(0).setPreferredWidth(35);
		highscorestable.getColumnModel().getColumn(1).setPreferredWidth(150);
		highscorestable.getColumnModel().getColumn(2).setPreferredWidth(60);
		JTableHeader hd = highscorestable.getTableHeader();
		hd.setEnabled(false);
		hd.setBackground(Color.YELLOW);
		hd.setForeground(Color.BLACK);
		hd.setReorderingAllowed(false);
		hd.setResizingAllowed(false);
		scorepanel.add(hd);
		scorepanel.add(highscorestable);
		highscoreview.setVisible(true);
	}
	//Returns names from player-name settings
	public String[] getNames(){
		String r[] = new String[2];
		r[0] = player1name.getText();
		r[1] = player2name.getText();
		setplayers.setVisible(false);
		return r;
	}
}