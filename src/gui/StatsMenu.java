package gui;

import java.awt.Dimension;
import java.awt.Font;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.util.Iterator;

import json.JSONFailureException;
import service.FriendService;
import service.ProgenyService;
import service.UserService;
import ttable.Friend;
import ttable.Progeny;
import ttable.User;

/**
 * The class StatsMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Yaqzan Ali
 * @Author Taylor Calder
 * @version 1.2
 */
@SuppressWarnings("serial")
public class StatsMenu extends BackgroundPanel {

	private String path;
	private String progNames, progAges, progLevels;
	// private ArrayList<Progeny> age3, age4, age5, age6, age7, age8, age9,
	// age10, ageOther;
	JComboBox<String> levels, ages;
	JLabel nameFirst, nameSecond, nameThird, nameFirst2, nameSecond2,
			nameThird2;
	JLabel ageFirst, ageSecond, ageThird, ageFirst2, ageSecond2, ageThird2;
	JLabel timeFirst, timeSecond, timeThird, timeFirst2, timeSecond2,
			timeThird2;
	JLabel parentFirst1, parentFirst2, parentSecond1, parentSecond2,
			parentThird1, parentThird2;
	JLabel picFirst1, picFirst2, picSecond1, picSecond2, picThird1, picThird2;
	BoxListener box;

	/**
	 * Instantiates a StatsMenu instance.
	 * 
	 */
	public StatsMenu() {

		// Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png",
				new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// -------------------------------------------------------------------------------------------------
		// Add the logo
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 150, 10, 150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		try {
			Image img = ImageIO.read(new URL(
					"http://jbaron6.cs2212.ca/img/titles/stats.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Stats & Scores"), c);
		}

		// -------------------------------------------------------------------------------------------------
		// Tabs
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

		picFirst1 = new JLabel();
		picSecond1 = new JLabel();
		picThird1 = new JLabel();
		picFirst2 = new JLabel();
		picSecond2 = new JLabel();
		picThird2 = new JLabel();

		parentFirst1 = new JLabel();
		parentSecond1 = new JLabel();
		parentThird1 = new JLabel();
		parentFirst2 = new JLabel();
		parentSecond2 = new JLabel();
		parentThird2 = new JLabel();

		box = new BoxListener(this);
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(tab1);

		tab1.setLayout(new GridBagLayout());
		tab2.setLayout(new GridBagLayout());
		tab3.setLayout(new GridBagLayout());
		fillTabFriends(tab1, scroll, table);
		fillTabLevels(tab2, c);

		// Add the tabs
		tabs.addTab("Friends", tab1);
		tabs.addTab("High Scores", tab2);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 150, 10, 150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(tabs, c);

	}

	/**
	 * Fill tab friends.
	 * 
	 * @param tab1
	 *            the tab1
	 * @param c
	 *            the c
	 */
	private void fillTabFriends(JPanel tab1, JScrollPane scroll, JTable table) {

		// Headers
		String[] header = { "", "Friends", "Children", "Age", "Level" };
		ArrayList<Friend> friends = new ArrayList<Friend>();
		Friend hold;

		try {
			friends = FriendService.getFriends();
		} catch (JSONFailureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int n = friends.size();

		// array of friend data
		// -------------------------------------------------------------------------------------------------
		// Column 1 - picture
		// Column 2 - name
		// Column 3 - chidren's names
		// Column 4 - children's ages
		// Column 5 - children's levels
		// -------------------------------------------------------------------------------------------------
		Object[][] array = new Object[n][5];

		// create table to display friend info
		table = new JTable(array, header);

		// set column 1 to display pictures
		table.getColumnModel().getColumn(0)
				.setCellRenderer(table.getDefaultRenderer(ImageIcon.class));

		// columns 3, 4, and 5 are multi-line displays
		table.getColumnModel().getColumn(2)
				.setCellRenderer(new JTextRenderer());
		table.getColumnModel().getColumn(3)
				.setCellRenderer(new JTextRenderer());
		table.getColumnModel().getColumn(4)
				.setCellRenderer(new JTextRenderer());

		// set column widths
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		// table.getColumnModel().getColumn(0).setMaxWidth(75);
		// table.getColumnModel().getColumn(0).setMinWidth(75);

		table.getColumnModel().getColumn(1).setPreferredWidth(115);
		// table.getColumnModel().getColumn(1).setMaxWidth(115);
		// table.getColumnModel().getColumn(1).setMinWidth(115);

		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		// table.getColumnModel().getColumn(2).setMaxWidth(80);
		// table.getColumnModel().getColumn(2).setMinWidth(80);

		table.getColumnModel().getColumn(3).setPreferredWidth(55);
		// table.getColumnModel().getColumn(3).setMaxWidth(55);
		// table.getColumnModel().getColumn(3).setMinWidth(55);

		table.getColumnModel().getColumn(4).setPreferredWidth(55);
		// table.getColumnModel().getColumn(4).setMaxWidth(55);
		// table.getColumnModel().getColumn(4).setMinWidth(55);

		table.setEnabled(false);

		// set row height
		table.setRowHeight(65);

		// set table to not be editable
		// table.setDragEnabled(false);
		table.setShowVerticalLines(false);
		// table.setRowSelectionAllowed(false);
		// table.setColumnSelectionAllowed(false);
		// table.setCellSelectionEnabled(false);
		// table.getTableHeader().setReorderingAllowed(false);

		// ***********************************************************
		// STORE FRIEND INFORMATION IN THE ARRAY
		// ***********************************************************

		// int user for size of progeny list
		int m;

		for (int i = 0; i < n; i++) {

			hold = friends.get(i);

			// Picture
			path = ("http://graph.facebook.com/" + hold.getFbId() + "/picture?type=large");
			try {
				Image img = ImageIO.read(new URL(path));
				ImageIcon pic = (new ImageIcon(getScaledImage(img, 55, 55)));
				array[i][0] = pic;
			} catch (IOException e) {
				array[i][0] = "";
			}

			// Name
			array[i][1] = (hold.getFirstName() + " " + hold.getLastName());

			ArrayList<Progeny> progenies = hold.getProgenies();

			m = progenies.size();

			progNames = "";

			// Children Names
			for (int j = 0; j < m; j++) {

				progNames += (progenies.get(j).getFirstName() + "\n");
			}
			array[i][2] = progNames;

			// Children Ages

			progAges = "";

			for (int j = 0; j < m; j++) {
				progAges += (ProgenyService.getAge(progenies.get(j)
						.getBirthDate()) + "\n");
			}
			array[i][3] = progAges;

			progLevels = "";
			// Children Levels
			for (int j = 0; j < m; j++) {
				progLevels += (progenies.get(j).getLevel() + "\n");
			}
			array[i][4] = progLevels;
		}

		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400, 290));
		tab1.add(scroll);

	}

	/**
	 * Fill tab levels.
	 * 
	 * @param tab
	 *            the tab
	 * @param c
	 *            the c
	 */
	private void fillTabLevels(JPanel tab, GridBagConstraints c) {
		c = new GridBagConstraints();
		c.insets = new Insets(0, 10, 0, 10);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		tab.add(new JLabel("Level "), c);

		// -------------------------------------------------------------------------------------------------
		// Level Combo Box
		Vector<String> m = new Vector<String>();

		for (int i = 1; i < 13; i++) {
			m.add(Integer.toString(i));
		}
		levels = new JComboBox<String>(m);
		levels.addActionListener(box);
		c.gridx = 2;
		tab.add(levels, c);

		// ------------------------------------------------------------------------
		// Rank
		c.gridx = 0;
		c.gridy = 3;
		for (int i = 1; i < 4; i++) {
			c.gridy += 2;
			tab.add(new JLabel(Integer.toString(i)), c);
		}
		// ------------------------------------------------------------------------
		// Headers
		c.gridx = 1;
		c.gridy = 3;
		tab.add(new JLabel("Friends"), c);
		c.gridx = 0;
		tab.add(new JLabel("Rank"), c);
		c.gridx = 3;
		tab.add(new JLabel("Child"), c);
		c.gridx = 4;
		tab.add(new JLabel("Age"), c);
		c.gridx = 5;
		tab.add(new JLabel("Fastest Time"), c);
		// ------------------------------------------------------------------------
		// Profile Pictures
		c.insets = new Insets(0, 5, 0, 5);
		c.gridx = 1;
		c.gridy = 5;
		c.gridheight = 1;
		c.ipady = 30;
		{

			try {
				Image img = ImageIO.read(new URL(""));
				picFirst2 = new JLabel(new ImageIcon(
						getScaledImage(img, 55, 55)));
				tab.add(picFirst2, c);
			} catch (IOException e) {
				picFirst2.setText("");
				tab.add(picFirst2, c);
				c.gridy += 2;
			}

			try {
				Image img = ImageIO.read(new URL(""));
				picSecond2 = new JLabel(new ImageIcon(getScaledImage(img, 55,
						55)));
				tab.add(picSecond2, c);
			} catch (IOException e) {
				picSecond2.setText("");
				tab.add(picSecond2, c);
				c.gridy += 2;
			}

			try {
				Image img = ImageIO.read(new URL(""));
				picThird2 = new JLabel(new ImageIcon(
						getScaledImage(img, 55, 55)));
				tab.add(picThird2, c);
			} catch (IOException e) {
				picThird2.setText("");
				tab.add(picThird2, c);
				c.gridy += 2;
			}

			// ------------------------------------------------------------------------
			// Friends Names
			c.gridx = 2;
			c.gridy = 5;
			c.ipady = 0;
			tab.add(parentFirst1, c);
			c.gridy += 2;
			tab.add(parentSecond1, c);
			c.gridy += 2;
			tab.add(parentThird1, c);
			// ------------------------------------------------------------------------
			// Child Names
			c.gridx = 3;
			c.gridy = 5;
			tab.add(nameFirst, c);
			c.gridy += 2;
			tab.add(nameSecond, c);
			c.gridy += 2;
			tab.add(nameThird, c);
			// ------------------------------------------------------------------------
			// Ages
			c.gridx = 4;
			c.gridy = 5;
			tab.add(ageFirst, c);
			c.gridy += 2;
			tab.add(ageSecond, c);
			c.gridy += 2;
			tab.add(ageThird, c);
			// ------------------------------------------------------------------------
			// Fastest Times
			c.gridx = 5;
			c.gridy = 5;
			tab.add(timeFirst, c);
			c.gridy += 2;
			tab.add(timeSecond, c);
			c.gridy += 2;
			tab.add(timeThird, c);
			// -------------------------------------------------------------------------------------------------
			// Separators
			c.insets = new Insets(0, 0, 0, 0);
			c.gridwidth = 5;
			c.gridx = 0;
			c.gridy = 2;
			c.fill = GridBagConstraints.HORIZONTAL;
			for (int i = 1; i < 5; i++) {
				JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
				sep.setPreferredSize(new Dimension(1, 30));

				tab.add(sep, c);

				c.gridy += 2;
			}
		}

	}
	/**
	 * Gets the scaled image.
	 * 
	 * @param srcImg
	 *            the source image
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @return the scaled image
	 */
	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}



	/**
	 * Updates the list of children based on current level/age selected
	 */
	private void updateList() {

		ArrayList<Progeny> progList;

		int level;
		level = Integer.parseInt((String) levels.getSelectedItem());

		ArrayList<String> topThree = getTopThree(level);

		nameFirst.setText(topThree.get(0));
		ageFirst.setText(topThree.get(1));
		timeFirst.setText(topThree.get(2));
		parentFirst1.setText(topThree.get(3));
		try {
			picFirst1.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(4))), 55, 55)));
		} catch (IOException e) {
			picFirst1.setText("");
		}
		
		nameSecond.setText(topThree.get(5));
		ageSecond.setText(topThree.get(6));
		timeSecond.setText(topThree.get(7));
		parentSecond1.setText(topThree.get(8));
		try {
			picSecond1.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(9))), 55, 55)));
		} catch (IOException e) {
			picSecond1.setText("");
		}
		
		//
		nameThird.setText(topThree.get(10));
		ageThird.setText(topThree.get(11));
		timeThird.setText(topThree.get(12));
		parentThird1.setText(topThree.get(13));
		try {
			picThird1.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(14))), 55, 55)));
		} catch (IOException e) {
			picThird1.setText("");
		}

		nameFirst2.setText(topThree.get(0));
		ageFirst2.setText(topThree.get(1));
		timeFirst2.setText(topThree.get(2));
		parentFirst2.setText(topThree.get(3));
		try {
			picFirst2.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(4))), 55, 55)));
		} catch (IOException e) {
			picFirst2.setText("");
		}
		//
		nameSecond2.setText(topThree.get(5));
		ageSecond2.setText(topThree.get(6));
		timeSecond2.setText(topThree.get(7));
		parentSecond2.setText(topThree.get(8));
		try {
			picSecond2.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(9))), 55, 55)));
		} catch (IOException e) {
			picSecond2.setText("");
		}
		//
		nameThird2.setText(topThree.get(10));
		ageThird2.setText(topThree.get(11));
		timeThird2.setText(topThree.get(12));
		parentThird2.setText(topThree.get(13));
		try {
			picThird2.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new URL(topThree.get(14))), 55, 55)));
		} catch (IOException e) {
			picThird2.setText("");
		}

	}

	/**
	 * Returns the top three children for a given age and level
	 * 
	 * @param age
	 */
	private ArrayList<String> getTopThree(int level) {

		String firstName = null, firstAge = null, firstTime = null, secondName = null, secondAge = null, secondTime = null, thirdName = null, thirdAge = null, thirdTime = null, firstPic = null, secondPic = null, thirdPic = null, firstParent = null, secondParent = null, thirdParent = null;

		Progeny first = null, second = null, third = null;
		Friend firstFriend = null, secondFriend = null, thirdFriend = null;


		ArrayList<Friend> friends = new ArrayList<Friend>();
		try {
			friends = FriendService.getFriends();
		} catch (JSONFailureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		LinkedHashMap<Progeny, Friend> topThreeHash = null;

		try {
			topThreeHash = FriendService
					.getHighTopThreeProgeniesPerParentByLevel(friends, level);
		} catch (NullPointerException e) {
			new GeneralDialogue(e.getMessage(), "JSON Error", 1);
		}

		Iterator<Progeny> progeny = topThreeHash.keySet().iterator();

		first = progeny.next();
		try {
			second = progeny.next();
		} catch (Exception e) {

		}
		try {
			third = progeny.next();
		} catch (Exception e) {

		}

		ArrayList<String> topThree = new ArrayList<String>();

		firstFriend = topThreeHash.get(first);
		secondFriend = topThreeHash.get(second);
		thirdFriend = topThreeHash.get(third);
		try {
		firstName = first.getFirstName();
		firstAge = ("" + ProgenyService.getAge(first.getBirthDate()));
		firstTime = ("" + first.getLevelProgenys().get(level - 1)
				.getCompletionTime());
		}
		catch (Exception e) {
			firstName = "-";
			firstAge = "-";
			firstTime = "-";
		}
		
		try {
		secondName = (second.getFirstName());
		secondAge = ("" + ProgenyService.getAge(second.getBirthDate()));
		secondTime = ("" + second.getLevelProgenys().get(level - 1)
				.getCompletionTime());
		}
		catch (Exception e) {
			secondName = "-";
			secondAge = "-";
			secondTime = "-";
		}
		try {
		thirdName = (third.getFirstName());
		thirdAge = ("" + ProgenyService.getAge(third.getBirthDate()));
		thirdTime = ("" + third.getLevelProgenys().get(level - 1)
				.getCompletionTime());
		}
		catch (Exception e) {
			thirdName = "-";
			thirdAge = "-";
			thirdTime = "-";
		}
		try {
		firstParent = firstFriend.getFirstName();
		firstPic = "http://graph.facebook.com/" + firstFriend.getFbId()
				+ "/picture?type=large";
		}
		catch (Exception e) {
			firstParent = "-";
			firstPic = "-";
		}
		try {
		secondParent = secondFriend.getFirstName();
		secondPic = "http://graph.facebook.com/" + secondFriend.getFbId()
				+ "/picture?type=large";
		}
		catch (Exception e) {
			secondParent = "-";
			secondPic = "-";
		}
		try {
		thirdParent = thirdFriend.getFirstName();
		thirdPic = "http://graph.facebook.com/" + thirdFriend.getFbId()
				+ "/picture?type=large";
		}
		catch (Exception e) {
			secondParent = "-";
			secondPic = "-";
		}
		
		
		

		topThree.add(firstName);
		topThree.add(firstAge);
		topThree.add(firstTime);
		topThree.add(firstParent);
		topThree.add(firstPic);
		topThree.add(secondName);
		topThree.add(secondAge);
		topThree.add(secondTime);
		topThree.add(secondParent);
		topThree.add(secondPic);
		topThree.add(thirdName);
		topThree.add(thirdAge);
		topThree.add(thirdTime);
		topThree.add(thirdParent);
		topThree.add(thirdPic);
		return topThree;
	}

	/**
	 * ActionListener for the JComboBox
	 * 
	 * @author Taylor Calder
	 * @version 0.2
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
