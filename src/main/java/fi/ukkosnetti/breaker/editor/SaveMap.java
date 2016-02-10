package fi.ukkosnetti.breaker.editor;
import javax.swing.*;

import fi.ukkosnetti.breaker.resource.ImageResource;

import java.awt.*;
public class SaveMap extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1672782695939979071L;
	private JTextField savename = new JTextField();
	private JButton save = new JButton("SAVE");
	private JPanel container = new JPanel();
	public SaveMap(EditorController c){
		savename.setColumns(13);
		save.setActionCommand("SAVETHIS");
		save.addActionListener(c);
		save.setBackground(new Color(255,0,50));
		save.setForeground(Color.WHITE);
		container.add(savename);
		container.add(save);
		container.setBackground(new Color(50,0,255));
		this.add(container);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(180, 100);
		this.setIconImage(ImageResource.LOGO.getImage());
		this.setTitle("Save Game");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //center frame to screen
        this.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height)/4);
	}
	public String getSavedName(){
		return savename.getText();
	}
}
