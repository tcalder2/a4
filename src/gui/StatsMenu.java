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
	
// Add the logo		
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
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight =1;
		c.gridy = 1;
		add(new JLabel("Friends"), c);
		c.gridx = 2;
		c.gridwidth = 1;
		add(new JLabel("Children"), c);
		c.gridx = 3;
		add(new JLabel("Age"), c);
		c.gridx = 4;
		add(new JLabel("Level"), c);
		
		c.gridx = 0;
		c.gridy=2;
		c.gridheight =4;	
		for(int i=1; i<6;i++){
			
			try	{
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/profilePictures/"+i+".jpg"));
				JLabel pic = new JLabel(new ImageIcon(getScaledImage(img, 55,55)));
				add(pic, c);
			} catch (IOException e) {
				add(new JLabel("Profile Picture"), c);
			}
			c.gridy=(i*4)+2;
		}
//-------------------------------------------------------------------------------------------------			
		
		c.gridy = 2;
		c.gridx= 1;
		add(new JLabel("James A."), c);
		c.gridy+=4;
		add(new JLabel("Yaqzan"), c);
		c.gridy+=4;
		add(new JLabel("Chuhan"), c);
		c.gridy+=4;
		add(new JLabel("Taylor"), c);
		c.gridy+=4;
		add(new JLabel("James B."), c);

//-------------------------------------------------------------------------------------------------		
		c.insets = new Insets(0,0,0,0);
		c.gridy = 2;
		c.gridx = 2;
		c.gridheight = 1;
		add(new JLabel("Jim"), c);
		c.gridy++;
		add(new JLabel("Pam"), c);
		c.gridy++;
		add(new JLabel("Michael"), c);
		c.gridy++;
		add(new JLabel("Dwight"), c);
		c.gridy++;
		add(new JLabel("Troy"), c);
		c.gridy++;
		add(new JLabel("Abed"), c);
		c.gridy++;
		add(new JLabel("Britta"), c);
		c.gridy++;
		add(new JLabel("Annie"), c);
		c.gridy++;
		add(new JLabel("Sheldon"), c);
		c.gridy+=4;
		add(new JLabel("Leslie"), c);
		c.gridy++;
		add(new JLabel("Ron"), c);
		c.gridy+=3;
		add(new JLabel("Lisa"), c);
		c.gridy++;
		add(new JLabel("Bart"), c);
		c.gridy++;

		
//-------------------------------------------------------------------------------------------------	
		c.gridx = 3;
		for(int i=2; i< 22;i++){
			c.gridy= i;
			int x = 3 + (int)(Math.random() * ((20 - 3) + 1));
			add(new JLabel(Integer.toString(x)), c);
		}
		
//-------------------------------------------------------------------------------------------------		
		c.gridx = 4;
		for(int i=2; i< 22;i++){
			c.gridy= i;
			int x = 1 + (int)(Math.random() * 12);
			add(new JLabel(Integer.toString(x)), c);
			
		}
//-------------------------------------------------------------------------------------------------			



//-------------------------------------------------------------------------------------------------		

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
