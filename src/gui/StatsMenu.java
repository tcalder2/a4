package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import json.JSONFailureException;
import service.UserService;
import ttable.User;

/**
 * The class StatsMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Chuhan Qin
 * @author Taylor Calder
 * @version 1.2
 */
@SuppressWarnings("serial")
public class StatsMenu extends BackgroundPanel {

	/**
	 * Instantiates a StatsMenu instance.
	 *
	 */
	public StatsMenu() {

		//Calls superclass constructor to create the background panel
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
		
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(tab1);
		
		tab1.setLayout(new GridBagLayout());
		tab2.setLayout(new GridBagLayout());
		tab3.setLayout(new GridBagLayout());
		fillTabFriends(tab1, scroll, table);
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

	/**
	 * Fill tab friends.
	 *
	 * @param tab1 the tab1
	 * @param c the c
	 */
	private void fillTabFriends(JPanel tab1, JScrollPane scroll, JTable table){
		
		// Headers
		String[] header = {"", "Friends", "Children", "Age", "Level"};
		ArrayList<String> friends = new ArrayList<String>();
		String hold;
		
		try {
			friends = UserService.getFriendsTest();
		} catch (JSONFailureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int n = friends.size();
		
		// array of friend data
		//-------------------------------------------------------------------------------------------------		
		// Row 1 - picture
		// Row 2 - name
		// Row 3 - chidren's names
		// Row 4 - children's ages
		// Row 5 - children's levels
		//-------------------------------------------------------------------------------------------------		
		Object[][] array = new Object[n][5];
	
		// create table to display friend info
		table = new JTable(array, header);
		
		// set cell 1 to display pictures
		table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(ImageIcon.class));
		
		// set column widths
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		table.getColumnModel().getColumn(0).setMinWidth(75);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(115);
		table.getColumnModel().getColumn(1).setMaxWidth(115);
		table.getColumnModel().getColumn(1).setMinWidth(115);
		
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		
		table.getColumnModel().getColumn(3).setPreferredWidth(55);
		table.getColumnModel().getColumn(3).setMaxWidth(55);
		table.getColumnModel().getColumn(3).setMinWidth(55);
		
		table.getColumnModel().getColumn(4).setPreferredWidth(55);
		table.getColumnModel().getColumn(4).setMaxWidth(55);
		table.getColumnModel().getColumn(4).setMinWidth(55);
		
		table.setEnabled(false);
		
		// set row height
		table.setRowHeight(65);
		
		// set table to not be editable
		table.setDragEnabled(false);
		table.setShowVerticalLines(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		
		// Store friend information in the array
		
		/**
		 * NB - so far this only stores the names and profile pictures
		 * It's also a little slow, even with just 5 people
		 */
		for (int i = 0; i < n; i++) {

			hold = friends.get(i);
			array[i][1] = hold.substring(0, hold.indexOf(';'));
			try	{
				Image img = ImageIO.read(new URL(hold.substring(hold.indexOf(';')+1, hold.length())));
				ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
				array[i][0] = pic;
			} catch (IOException e) {
				array[i][0] = "no picture available";
			}
		}
		
		
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400,290));
		tab1.add(scroll);

	}

	/**
	 * Fill tab levels.
	 *
	 * @param tab the tab
	 * @param c the c
	 */
	private void fillTabLevels(JPanel tab, GridBagConstraints c){
		c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,10);
		c.gridwidth = 1;
		c.gridheight =1;
		c.gridx = 1;
		c.gridy = 0;
		tab.add(new JLabel("Level "),c);

		//-------------------------------------------------------------------------------------------------	
		//Level Combo Box
		Vector<String> m = new Vector<String>();

		for(int i=1;i<13;i++){
			m.add(Integer.toString(i));
		}
		JComboBox<String> levels = new JComboBox<String>(m);
		c.gridx = 2;
		tab.add(levels, c);

		//------------------------------------------------------------------------
		//Rank		
		c.gridx = 0;
		c.gridy = 3;
		for(int i =1;i<4;i++){
			c.gridy+=2;
			tab.add(new JLabel(Integer.toString(i)), c);
		}
		//------------------------------------------------------------------------
		//Headers
		c.gridx=1;
		c.gridy=3;
		tab.add(new JLabel("Friends"), c);
		c.gridx=0;
		tab.add(new JLabel("Rank"), c);
		c.gridx =3;
		tab.add(new JLabel("Child"), c);
		c.gridx = 4;
		tab.add(new JLabel("Age"), c);
		c.gridx=5;
		tab.add(new JLabel("Fastest Time"), c);
		//------------------------------------------------------------------------
		//Profile Pictures
		c.insets = new Insets(0,5,0,5);
		c.gridx = 1;
		c.gridy=5;
		c.gridheight =1;	
		c.ipady = 30;
		for(int i=1; i<4;i++){

			try	{
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/profilePictures/"+i+".jpg"));
				JLabel pic = new JLabel(new ImageIcon(getScaledImage(img, 55,55)));
				tab.add(pic, c);
			} catch (IOException e) {
				tab.add(new JLabel("Profile Picture"), c);
			}
			c.gridy+=2;
		}
		//------------------------------------------------------------------------
		//Friends Names
		c.gridx = 2;
		c.gridy=5;
		c.ipady = 0;
		tab.add(new JLabel("James Baron"), c);
		c.gridy+=2;
		tab.add(new JLabel("James Anderson"), c);
		c.gridy+=2;
		tab.add(new JLabel("Chuhan Frank"), c);
		//------------------------------------------------------------------------
		//Child Names
		c.gridx = 3;
		c.gridy=5;
		tab.add(new JLabel("Jim"), c);
		c.gridy+=2;
		tab.add(new JLabel("Lisa"), c);
		c.gridy+=2;
		tab.add(new JLabel("Leslie"), c);
		//------------------------------------------------------------------------
		//Ages
		c.gridx = 4;
		c.gridy=5;
		tab.add(new JLabel("8"), c);
		c.gridy+=2;
		tab.add(new JLabel("7"), c);
		c.gridy+=2;
		tab.add(new JLabel("9"), c);
		//------------------------------------------------------------------------
		//Fastest Times
		c.gridx = 5;
		c.gridy=5;
		tab.add(new JLabel("0:27"), c);
		c.gridy+=2;
		tab.add(new JLabel("0:35"), c);
		c.gridy+=2;
		tab.add(new JLabel("1:20"), c);
		//-------------------------------------------------------------------------------------------------		
		// Separators
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 1; i<5;i++){
			JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
			sep.setPreferredSize(new Dimension(1,30));

			tab.add(sep,c);

			c.gridy+=2;
		}

	}

	/**
	 * Fill tab age.
	 *
	 * @param tab the tab
	 * @param c the c
	 */
	private void fillTabAge(JPanel tab, GridBagConstraints c){
		c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,10);
		c.gridwidth = 1;
		c.gridheight =1;
		c.gridx = 1;
		c.gridy = 0;
		tab.add(new JLabel("Age "),c);

		//-------------------------------------------------------------------------------------------------	
		//Level Combo Box
		Vector<String> m = new Vector<String>();
		for(int i=3;i<14;i++){
			m.add(Integer.toString(i));
		}
		JComboBox<String> levels = new JComboBox<String>(m);
		c.gridx = 2;
		tab.add(levels, c);
		//------------------------------------------------------------------------
		//Rank		
		c.gridx = 0;
		c.gridy = 3;
		for(int i =1;i<4;i++){
			c.gridy+=2;
			tab.add(new JLabel(Integer.toString(i)), c);
		}
		//------------------------------------------------------------------------
		//Headers
		c.gridx=1;
		c.gridy=3;
		tab.add(new JLabel("Friends"), c);
		c.gridx=0;
		tab.add(new JLabel("Rank"), c);
		c.gridx =3;
		tab.add(new JLabel("Child"), c);
		c.gridx = 4;
		tab.add(new JLabel("Age"), c);
		c.gridx=5;
		tab.add(new JLabel("Fastest Time"), c);
		//------------------------------------------------------------------------
		//Profile Pictures
		c.insets = new Insets(0,5,0,5);
		c.gridx = 1;
		c.gridy=5;
		c.gridheight =1;	
		c.ipady = 30;
		for(int i=1; i<4;i++){

			try	{
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/profilePictures/"+i+".jpg"));
				JLabel pic = new JLabel(new ImageIcon(getScaledImage(img, 55,55)));
				tab.add(pic, c);
			} catch (IOException e) {
				tab.add(new JLabel("Profile Picture"), c);
			}
			c.gridy+=2;
		}
		//------------------------------------------------------------------------
		//Friends Names
		c.gridx = 2;
		c.gridy=5;
		c.ipady = 0;
		tab.add(new JLabel("James Baron"), c);
		c.gridy+=2;
		tab.add(new JLabel("James Anderson"), c);
		c.gridy+=2;
		tab.add(new JLabel("Chuhan Frank"), c);
		//------------------------------------------------------------------------
		//Child Names
		c.gridx = 3;
		c.gridy=5;
		tab.add(new JLabel("Jim"), c);
		c.gridy+=2;
		tab.add(new JLabel("Lisa"), c);
		c.gridy+=2;
		tab.add(new JLabel("Leslie"), c);
		//------------------------------------------------------------------------
		//Ages
		c.gridx = 4;
		c.gridy=5;
		tab.add(new JLabel("8"), c);
		c.gridy+=2;
		tab.add(new JLabel("7"), c);
		c.gridy+=2;
		tab.add(new JLabel("9"), c);
		//------------------------------------------------------------------------
		//Fastest Times
		c.gridx = 5;
		c.gridy=5;
		tab.add(new JLabel("0:27"), c);
		c.gridy+=2;
		tab.add(new JLabel("0:35"), c);
		c.gridy+=2;
		tab.add(new JLabel("1:20"), c);
		//-------------------------------------------------------------------------------------------------		
		// Separators
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 1; i<5;i++){
			JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
			sep.setPreferredSize(new Dimension(1,30));

			tab.add(sep,c);

			c.gridy+=2;
		}

	}
	// Resize ImageIcon
	/**
	 * Gets the scaled image.
	 *
	 * @param srcImg	the source image
	 * @param w			the width
	 * @param h			the height
	 * @return 			the scaled image
	 */
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}
}
