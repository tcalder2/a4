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
		//-------------------------------------------------------------------------------------------------		
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
		//Tabs
		JTabbedPane tabs = new JTabbedPane();
		JPanel tab1 = new JPanel();
		JPanel tab2 = new JPanel();
		JPanel tab3 = new JPanel();
		tab1.setLayout(new GridBagLayout());
		fillTabFriends(tab1, c);
		fillTabAge(tab2, c);
		fillTabLevels(tab3, c);
		
		// Add the tabs
		tabs.addTab("Friends", tab1);
		tabs.addTab("By Age", tab2);
		tabs.addTab("By Level", tab3);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10,150,10,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(tabs, c);


		
	}
	private void fillTabFriends(JPanel tab1, GridBagConstraints c){
		// Headers	
		
		c.insets = new Insets(5,5,5,10);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight =1;
		c.gridy = 0;
		tab1.add(new JLabel("Friends"), c);
		c.gridx = 2;
		c.gridwidth = 1;
		tab1.add(new JLabel("Children"), c);
		c.gridx = 3;
		tab1.add(new JLabel("Age"), c);
		c.gridx = 4;
		tab1.add(new JLabel("Level"), c);
		//-------------------------------------------------------------------------------------------------		
		// Profile Pictures
		c.gridx = 0;
		c.gridy=2;
		c.gridheight =4;	
		for(int i=1; i<6;i++){
			
			try	{
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/profilePictures/"+i+".jpg"));
				JLabel pic = new JLabel(new ImageIcon(getScaledImage(img, 55,55)));
				tab1.add(pic, c);
			} catch (IOException e) {
				tab1.add(new JLabel("Profile Picture"), c);
			}
			c.gridy=(i*5)+2;
		}

		//-------------------------------------------------------------------------------------------------	
		// Friends names
		c.gridy = 2;
		c.gridx= 1;
		c.gridwidth =1;
		tab1.add(new JLabel("James Anderson"), c);
		c.gridy+=5;
		tab1.add(new JLabel("Yaqzan Ali"), c);
		c.gridy+=5;
		tab1.add(new JLabel("Chuhan Frank"), c);
		c.gridy+=5;
		tab1.add(new JLabel("Taylor Joseph"), c);
		c.gridy+=5;
		tab1.add(new JLabel("James Baron"), c);

		//-------------------------------------------------------------------------------------------------		
		// Children Names
		c.insets = new Insets(0,10,0,0);
		c.gridy = 2;
		c.gridx = 2;
		c.gridheight = 1;
		tab1.add(new JLabel("Lisa"), c);
		c.gridy++;
		tab1.add(new JLabel("Bart"), c);
		c.gridy+=4;
		
		tab1.add(new JLabel("Troy"), c);
		c.gridy++;
		tab1.add(new JLabel("Abed"), c);
		c.gridy++;
		tab1.add(new JLabel("Britta"), c);
		c.gridy+=3;

		tab1.add(new JLabel("Sheldon"), c);
		c.gridy+=5;
		tab1.add(new JLabel("Leslie"), c);
		c.gridy++;
		tab1.add(new JLabel("Ron"), c);
		c.gridy+=4;
		tab1.add(new JLabel("Dwight"), c);
		c.gridy++;
		tab1.add(new JLabel("Jim"), c);
		c.gridy++;
		tab1.add(new JLabel("Michael"), c);
		c.gridy++;
		tab1.add(new JLabel("Pam"), c);
		
		
		//-------------------------------------------------------------------------------------------------	
		// Age
		c.insets = new Insets(0,10,0,10);
		c.gridx = 3;
		c.gridy = 2;
		
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy+=4;
		
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy+=3;

		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy+=5;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy+=4;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		c.gridy++;
		tab1.add(new JLabel((Integer.toString(3 + (int)(Math.random() * ((20 - 3) + 1))))+" years old"), c);
		
		//-------------------------------------------------------------------------------------------------		
		// Level
		c.gridx = 4;
		c.gridy = 2;
		
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy+=4;
		
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy+=3;

		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy+=5;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy+=4;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		c.gridy++;
		tab1.add(new JLabel(("Level "+Integer.toString(1 + (int)(Math.random() * 12)))), c);
		//-------------------------------------------------------------------------------------------------		
		// Separators
		c.insets = new Insets(0,0,0,0);
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 1;
	    c.gridwidth = 5;
		c.gridheight =1;
		c.gridx = 0;
		c.gridy = 1;
		for(int i = 1; i< 6;i++){
			JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
			sep.setPreferredSize(new Dimension(1,30));

			tab1.add(sep,c);
					
			c.gridy+=5;
		}
			
	}

	private void fillTabLevels(JPanel tab, GridBagConstraints c){
		c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight =1;
		c.gridx = 1;
		c.gridy = 0;
		tab.add(new JLabel("Level "),c);
		
        //-------------------------------------------------------------------------------------------------	
		//Level Combo Box
		Vector<String> m = new Vector<String>();
		m.add("1");
		m.add("2");
		m.add("3");
		m.add("4");
		m.add("5");
		m.add("6");
		m.add("7");
		m.add("8");
		m.add("9");
		m.add("10");
		m.add("11");
		m.add("12");
		JComboBox<String> levels = new JComboBox<String>(m);
		c.gridx = 2;
		tab.add(levels, c);
		
		c.gridx = 0;
		c.gridy = 3;
		for(int i =1;i<4;i++){
			c.gridy+=2;
			tab.add(new JLabel(Integer.toString(i)), c);
		}
		
		
	}
	
	private void fillTabAge(JPanel tab, GridBagConstraints c){

		
	}
// Resize ImageIcon
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
}
