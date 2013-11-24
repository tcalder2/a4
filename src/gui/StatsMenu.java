package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	JComboBox<String> levels, ages;
	JLabel nameFirst, nameSecond, nameThird, nameFirst2, nameSecond2, nameThird2;
	JLabel ageFirst, ageSecond, ageThird, ageFirst2, ageSecond2, ageThird2;
	JLabel timeFirst, timeSecond, timeThird, timeFirst2, timeSecond2, timeThird2;
	BoxListener box;
	
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
		
		nameFirst = new JLabel();
		nameSecond = new JLabel();
		nameThird = new JLabel();
		nameFirst2 = new JLabel();
		nameSecond2 = new JLabel();
		nameThird2 = new JLabel();
		
		ageFirst = new JLabel();
		ageSecond = new JLabel();
		ageThird = new JLabel();
		ageFirst2 = new JLabel();
		ageSecond2 = new JLabel();
		ageThird2 = new JLabel();
		
		timeFirst = new JLabel();
		timeSecond = new JLabel();
		timeThird = new JLabel();
		timeFirst2 = new JLabel();
		timeSecond2 = new JLabel();
		timeThird2 = new JLabel();
		
		box = new BoxListener(this);
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
		// Column 1 - picture
		// Column 2 - name
		// Column 3 - chidren's names
		// Column 4 - children's ages
		// Column 5 - children's levels
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
		levels = new JComboBox<String>(m);
		levels.addActionListener(box);
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
		tab.add(nameFirst, c);
		c.gridy+=2;
		tab.add(nameSecond, c);
		c.gridy+=2;
		tab.add(nameThird, c);
		//------------------------------------------------------------------------
		//Ages
		c.gridx = 4;
		c.gridy=5;
		tab.add(ageFirst, c);
		c.gridy+=2;
		tab.add(ageSecond, c);
		c.gridy+=2;
		tab.add(ageThird, c);
		//------------------------------------------------------------------------
		//Fastest Times
		c.gridx = 5;
		c.gridy=5;
		tab.add(timeFirst, c);
		c.gridy+=2;
		tab.add(timeSecond, c);
		c.gridy+=2;
		tab.add(timeThird, c);
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
		//Age Combo Box
		Vector<String> m = new Vector<String>();
		for(int i=3;i<14;i++){
			m.add(Integer.toString(i));
		}
		ages = new JComboBox<String>(m);
		ages.addActionListener(box);
		c.gridx = 2;
		tab.add(ages, c);
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
		tab.add(nameFirst2, c);
		c.gridy+=2;
		tab.add(nameSecond2, c);
		c.gridy+=2;
		tab.add(nameThird2, c);
		//------------------------------------------------------------------------
		//Ages
		c.gridx = 4;
		c.gridy=5;
		tab.add(ageFirst2, c);
		c.gridy+=2;
		tab.add(ageSecond2, c);
		c.gridy+=2;
		tab.add(ageThird2, c);
		//------------------------------------------------------------------------
		//Fastest Times
		c.gridx = 5;
		c.gridy=5;
		tab.add(timeFirst2, c);
		c.gridy+=2;
		tab.add(timeSecond2, c);
		c.gridy+=2;
		tab.add(timeThird2, c);
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
	
	/**
	 * Updates the list of children based on current level/age selected
	 */
	private void updateList() {
		
		int age, level;
		age = Integer.parseInt((String) ages.getSelectedItem());
		level = Integer.parseInt((String) levels.getSelectedItem());
		
		ArrayList<String> topThree = getTopThree(age, level);
		
		nameFirst.setText(topThree.get(0));
		ageFirst.setText(topThree.get(1));
		timeFirst.setText(topThree.get(2));
		nameSecond.setText(topThree.get(3));
		ageSecond.setText(topThree.get(4));
		timeSecond.setText(topThree.get(5));
		nameThird.setText(topThree.get(6));
		ageThird.setText(topThree.get(7));
		timeThird.setText(topThree.get(8));

		nameFirst2.setText(topThree.get(0));
		ageFirst2.setText(topThree.get(1));
		timeFirst2.setText(topThree.get(2));
		nameSecond2.setText(topThree.get(3));
		ageSecond2.setText(topThree.get(4));
		timeSecond2.setText(topThree.get(5));
		nameThird2.setText(topThree.get(6));
		ageThird2.setText(topThree.get(7));
		timeThird2.setText(topThree.get(8));
			
	}
	
	/**
	 * Returns the top three children for a given age and level
	 * @param age
	 * @param level
	 */
	private ArrayList<String> getTopThree(int age, int level) {
	
		String firstName, firstAge, firstTime, secondName, secondAge, secondTime, thirdName, thirdAge, thirdTime;
		
		ArrayList<String> topThree = new ArrayList<String>();
		
		// Time the child took to complete the level on seconds
		int time;
		
		//TODO Make JSON call to get top 3 children
	
		firstName = ("Molly");
		firstAge = ("" + age);
		firstTime = ("0:19");
		secondName = ("Case");
		secondAge = ("" + age);
		secondTime = ("0:22");
		thirdName = ("Armitage");
		thirdAge = ("" + age);
		thirdTime = ("0:23");
		
		topThree.add(firstName);
		topThree.add(firstAge);
		topThree.add(firstTime);
		topThree.add(secondName);
		topThree.add(secondAge);
		topThree.add(secondTime);
		topThree.add(thirdName);
		topThree.add(thirdAge);
		topThree.add(thirdTime);
		
		return topThree;
		
	}
	
	/**
	 * ActionListener for the JComboBox
	 * 
	 * @author Taylor Calder
	 * @version 0.1
	 */
	class BoxListener implements ActionListener {

		StatsMenu menu;
		
		public BoxListener(StatsMenu m) {
			this.menu = m;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			menu.updateList();
		}
		
		
	}
	
}
