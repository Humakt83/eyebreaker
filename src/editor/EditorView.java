package editor;

import java.awt.*;

import javax.swing.*;
public class EditorView extends JFrame{
	private static final long serialVersionUID = 2099886291871181934L;
	private DrawingArea da = new DrawingArea();;
	private JMenuBar menubar;
	private JMenu filemenu;
	private JMenu helpmenu;
	private JMenuItem help;
	private JMenuItem about;
	private JMenuItem newmap;
	private JMenuItem exitprog;
	private JMenuItem save;
	private JMenuItem load;
	private ImageIcon eye = new ImageIcon("Eye.png");
	public EditorView(){
		newmap = new JMenuItem("New Level");
		exitprog = new JMenuItem("Exit");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		about = new JMenuItem("About");
		help = new JMenuItem("Help");
		filemenu = new JMenu("File");
		helpmenu = new JMenu("Help");
		menubar = new JMenuBar();
		helpmenu.add(help);
		helpmenu.addSeparator();
		helpmenu.add(about);
		filemenu.add(newmap);
		filemenu.addSeparator();
		filemenu.add(save);
		filemenu.add(load);
		filemenu.addSeparator();
		filemenu.add(exitprog);
		menubar.add(filemenu);
		menubar.add(helpmenu);
		this.setJMenuBar(menubar);
		Container content = this.getContentPane();
		content.add(da);
		this.pack();
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
		this.setIconImage(eye.getImage());
		this.setTitle( "EyeBreaker Editor" );
		this.setLayout(new GridLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void setController(EditorController c){
		c.setDrawingArea(da);
		da.setController(c);
		newmap.setActionCommand("RESET");
		save.setActionCommand("SAVE");
		load.setActionCommand("LOAD");
		about.setActionCommand("ABOUT");
		help.setActionCommand("HELP");
		exitprog.setActionCommand("EXIT");
		newmap.addActionListener(c);
		save.addActionListener(c);
		load.addActionListener(c);
		exitprog.addActionListener(c);
		help.addActionListener(c);
		about.addActionListener(c);
	}
}
