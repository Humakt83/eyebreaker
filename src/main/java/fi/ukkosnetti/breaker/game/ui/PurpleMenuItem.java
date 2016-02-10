package fi.ukkosnetti.breaker.game.ui;
import java.awt.Color;

import javax.swing.*;
public class PurpleMenuItem extends JMenuItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1349802548504628558L;

	public PurpleMenuItem(String s){
		this.setText(s);
		this.setBackground(new Color(105,25,255));
		this.setForeground(Color.WHITE);
	}
}
