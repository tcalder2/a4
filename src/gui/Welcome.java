package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Welcome extends BackgroundPanel {
	
	private Controller controller;
	
	public Welcome(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		this.controller = controller;
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/welcome.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"), c);
		}
		
		c.gridy = 1;
		c.insets = new Insets(25,100,0,100);
		if (controller.getUser().getProgies().size() > 0) {
			
		}
		else {
			
		}
	}
}
