package breaker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.*;
import javax.swing.*;
/*
 * Simple class for About view.
 */
public class About extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9201271579248031460L;
	private JTextArea abouttxt = new JTextArea(9,24);
	private JPanel container = new JPanel(){
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(new GradientPaint(0,0, Color.BLACK, this.getWidth(), 0, Color.GREEN));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	};
	private ImageIcon eye = new ImageIcon("Eye.png");
	public About(){
		// Setting component properties.
		abouttxt.setOpaque(false);
		container.setOpaque(true);
		// Customizing JTextArea.
		abouttxt.setFont(new Font("Impact", Font.PLAIN, 14));
		abouttxt.setForeground(Color.WHITE);
		abouttxt.setWrapStyleWord(true);
		abouttxt.setText("EyeBreaker. \nGame for breaking whole lot of boxes. \nProgram version 1.0. \nProgrammed & designed by Risto Salama. \n\n Thanks to Juha Railio for helping to test this program.");
		abouttxt.setEditable(false);
		abouttxt.setLineWrap(true);
		// Adding components to panels.
		container.add(abouttxt);
		add(container);
		// Customizing frame.
		setTitle("About");
		container.setLayout(new FlowLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //center frame to screen
        setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
		pack();
		setResizable(false);
		this.setIconImage(eye.getImage());
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
