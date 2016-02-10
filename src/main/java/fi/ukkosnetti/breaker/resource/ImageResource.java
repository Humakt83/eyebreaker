package fi.ukkosnetti.breaker.resource;

import javax.swing.ImageIcon;

public class ImageResource {
	
	public final static ImageIcon LOGO = initIcon("Eye.png");
	public final static ImageIcon BACKGROUND = initIcon("bg.png");
	public final static ImageIcon HELP = initIcon("Help.png");
	
	private static ImageIcon initIcon(String name) {
		return new ImageIcon(ClassLoader.getSystemResource(name).getFile());
	}
}
