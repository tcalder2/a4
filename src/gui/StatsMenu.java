package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class StatsMenu extends BackgroundPanel {
	
	public StatsMenu(Controller controller) {
		
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
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

//-------------------------------------------------------------------------------------------------		
		c.insets = new Insets(0,75,0,75);
		c.gridy = 1;
		c.gridx = 0;
		c.ipady = 300;
		c.ipadx = 150;

		
		JTabbedPane tabs = new JTabbedPane();
		JPanel tabFriends = new JPanel();
		JPanel tabScores = new JPanel();
		
		tabs.addTab("Friends", tabFriends);
		tabs.addTab("High SCores", tabScores);
		add(tabs, c);
//-------------------------------------------------------------------------------------------------			

		tabFriends.setLayout(new GridBagLayout());
		c.insets = new Insets(0,20,0,20);
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridheight =4;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		for(int i=1; i<6;i++){
			c.gridy+=4;
			try	{
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/profilePictures/"+i+".jpg"));
				JLabel pic = new JLabel(new ImageIcon(getScaledImage(img, 55,55)));
				tabFriends.add(pic, c);
			} catch (IOException e) {
				tabFriends.add(new JLabel("Profile Picture"), c);
			}
		}

		c.gridy = 0;
		c.gridx= 1;
		tabFriends.add(new JLabel("James"), c);
		c.gridy = 0;
		c.gridx= 2;
		c.gridheight = 1;
		tabFriends.add(new JLabel("Jim"), c);
		c.gridy = 1;
		c.gridx= 2;
		tabFriends.add(new JLabel("Dwight"), c);
	}


	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
}
