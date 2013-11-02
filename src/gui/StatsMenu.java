package gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StatsMenu extends BackgroundPanel {
	
	public StatsMenu(Controller controller) {
		
		super("http://jbaron6.cs2212.ca/img/default_background.png");
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10,150,10,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/stats.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Stats & Scores"), c);
		}
		
		JTabbedPane tabs = new JTabbedPane();
		JPanel tabFriends = new JPanel();
	//	tabFriends.add
		
		
	}
}
