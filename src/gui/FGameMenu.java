<<<<<<< HEAD
package gui;

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
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import json.JSONFailureException;
import service.FriendService;
import ttable.Friend;
import ttable.Progeny;

/**
 * The class FGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 1.1
 */
@SuppressWarnings("serial")
public class FGameMenu extends BackgroundPanel {

	/**
	 * Instantiates a FGame instance.
	 *
	 */
	public FGameMenu() {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		//Create a GridBagConstraints instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(50,150,5,150);
		c.gridx = 0;
		c.gridy = 0;

		//Load and add final game title graphic
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/fgame.png"));
			add(new JLabel(new ImageIcon(img)), c);

			//If there is an error loading the graphic display a text placeholder
		} catch (IOException e) {
			add(new JLabel("Drill Menu"), c);
		}

		//Create and set display attributes of play button
		JButton play = new JButton();
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/fgame/playgame.png"));
			play.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			play.setText("PLAY GAME!");
		}

		//Add action listener for play button
		play.addActionListener(new StartFinal());


		//Add the play button
		c.ipady = 50;
		c.gridwidth = 1;
		c.insets = new Insets(10,150,0,150);
		c.gridy = 1;
		add(play, c);

		//Load and add the high scores title graphic
		c.gridwidth = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,150,0,150);
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/highscores.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("High Scores"), c);
		}

		//Get child's high scores 
		int[] tmp = {100,88,681};
		Vector<Vector<String>> highScores = new Vector<Vector<String>>();
		for (int i = 0; i < tmp.length; i++) {
			Vector<String> v = new Vector<String>();
			v.add("" + tmp[i]);
			highScores.add(v);
		}

		LinkedHashMap<Progeny, Friend> butts;
		try {
			butts =  FriendService.getHighTopThreeProgeniesPerParentForFinal(FriendService.getFriends());
		} catch (JSONFailureException e) {
			new GeneralDialogue(e.getMessage(), "JSON Error", 1);
			butts = null;
		}
		
		
		String[] header = {"Friend", "Child", "Times"};
		Object[][] array = new Object[3][3];
		
		Iterator<Progeny> progeny = butts.keySet().iterator();

		Progeny prog1 = null, prog2 = null, prog3 = null;
		Friend friend1 = null, friend2 = null, friend3 = null;
		
		try {
			prog1 = progeny.next();
			friend1 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend1.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[0][0] = pic;
			array[0][1] = prog1.getFirstName();
			array[0][2] = "" + prog1.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[0][0] = "--";
			array[0][1] = "--";
			array[0][2] = "--";
			}
	
		try {
			prog2 = progeny.next();
			friend2 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend2.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[1][0] = pic;
			array[1][1] = prog2.getFirstName();
			array[1][2] = "" + prog2.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[1][0] = "--";
			array[1][1] = "--";
			array[1][2] = "--";		}
		
		try {
			prog3 = progeny.next();
			friend3 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend3.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[2][0] = pic;
			array[2][1] = prog3.getFirstName();
			array[2][2] = "" + prog3.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[2][0] = "--";
			array[2][1] = "--";
			array[2][2] = "--";
		}
		
		JTable table = new JTable(array, header);

		//Set the high scores table display attributes
		table.setOpaque(false);
		table.setRowHeight(60);
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setFont(Controller.getFont().deriveFont(Font.BOLD, 30));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		renderer.setOpaque(false);
		TableColumn col;
		
		// Set pictures renderer
		col = table.getColumnModel().getColumn(0);
		col.setCellRenderer(table.getDefaultRenderer(ImageIcon.class));
		((DefaultTableCellRenderer)table.getDefaultRenderer(ImageIcon.class)).setOpaque(false);
		// Set name renderer
		col = table.getColumnModel().getColumn(1);
		col.setCellRenderer(renderer);
		// Set score renderer
		col = table.getColumnModel().getColumn(2);
		col.setCellRenderer(renderer);

		//Add the table to the panel
		c.insets = new Insets(0,30,50,30);
		c.gridy = 3;
		c.ipady = 0;
		add(table,c);
	}
	
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

/**
 * The class StartFinal, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class StartFinal implements ActionListener {

	/**
	 * Instantiates a StartFinal instance.
	 * 
	 */
	public StartFinal() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		FGame screen = new FGame();
		Controller.setScreen(screen);
	}
}
=======
package gui;

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
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import json.JSONFailureException;
import service.FriendService;
import ttable.Friend;
import ttable.Progeny;

/**
 * The class FGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 1.1
 */
@SuppressWarnings("serial")
public class FGameMenu extends BackgroundPanel {

	/**
	 * Instantiates a FGame instance.
	 *
	 */
	public FGameMenu() {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		//Create a GridBagConstraints instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(50,150,5,150);
		c.gridx = 0;
		c.gridy = 0;

		//Load and add final game title graphic
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/fgame.png"));
			add(new JLabel(new ImageIcon(img)), c);

			//If there is an error loading the graphic display a text placeholder
		} catch (IOException e) {
			add(new JLabel("Drill Menu"), c);
		}

		//Create and set display attributes of play button
		JButton play = new JButton();
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/fgame/playgame.png"));
			play.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			play.setText("PLAY GAME!");
		}

		//Add action listener for play button
		play.addActionListener(new StartFinal());


		//Add the play button
		c.ipady = 50;
		c.gridwidth = 1;
		c.insets = new Insets(10,150,0,150);
		c.gridy = 1;
		add(play, c);

		//Load and add the high scores title graphic
		c.gridwidth = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,150,0,150);
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/highscores.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("High Scores"), c);
		}

		//Get child's high scores 
		int[] tmp = {100,88,681};
		Vector<Vector<String>> highScores = new Vector<Vector<String>>();
		for (int i = 0; i < tmp.length; i++) {
			Vector<String> v = new Vector<String>();
			v.add("" + tmp[i]);
			highScores.add(v);
		}

		LinkedHashMap<Progeny, Friend> butts;
		try {
			butts =  FriendService.getHighTopThreeProgeniesPerParentForFinal(FriendService.getFriends());
		} catch (JSONFailureException e) {
			new GeneralDialogue(e.getMessage(), "JSON Error", 1);
			butts = null;
		}
		
		
		String[] header = {"Friend", "Child", "Times"};
		Object[][] array = new Object[3][3];
		
		Iterator<Progeny> progeny = butts.keySet().iterator();

		Progeny prog1 = null, prog2 = null, prog3 = null;
		Friend friend1 = null, friend2 = null, friend3 = null;
		
		try {
			prog1 = progeny.next();
			friend1 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend1.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[0][0] = pic;
			array[0][1] = prog1.getFirstName();
			array[0][2] = "" + prog1.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[0][0] = "--";
			array[0][1] = "--";
			array[0][2] = "--";
			}
	
		try {
			prog2 = progeny.next();
			friend2 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend2.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[1][0] = pic;
			array[1][1] = prog2.getFirstName();
			array[1][2] = "" + prog2.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[1][0] = "--";
			array[1][1] = "--";
			array[1][2] = "--";		}
		
		try {
			prog3 = progeny.next();
			friend3 = butts.get(prog1);
			Image img = ImageIO.read(new URL("http://graph.facebook.com/" + friend3.getFbId()
					+ "/picture?type=large"));
			ImageIcon pic = (new ImageIcon(getScaledImage(img, 55,55)));
			array[2][0] = pic;
			array[2][1] = prog3.getFirstName();
			array[2][2] = "" + prog3.getFinalGameHighScore();
		}
		catch (Exception e) {
			array[2][0] = "--";
			array[2][1] = "--";
			array[2][2] = "--";
		}
		
		JTable table = new JTable(array, header);

		//Set the high scores table display attributes
		table.setOpaque(false);
		table.setRowHeight(60);
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setFont(Controller.getFont().deriveFont(Font.BOLD, 30));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		renderer.setOpaque(false);
		TableColumn col;
		
		// Set pictures renderer
		col = table.getColumnModel().getColumn(0);
		col.setCellRenderer(table.getDefaultRenderer(ImageIcon.class));
		((DefaultTableCellRenderer)table.getDefaultRenderer(ImageIcon.class)).setOpaque(false);
		// Set name renderer
		col = table.getColumnModel().getColumn(1);
		col.setCellRenderer(renderer);
		// Set score renderer
		col = table.getColumnModel().getColumn(2);
		col.setCellRenderer(renderer);

		//Add the table to the panel
		c.insets = new Insets(0,30,50,30);
		c.gridy = 3;
		c.ipady = 0;
		add(table,c);
	}
	
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

/**
 * The class StartFinal, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class StartFinal implements ActionListener {

	/**
	 * Instantiates a StartFinal instance.
	 * 
	 */
	public StartFinal() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		FGame screen = new FGame();
		Controller.setScreen(screen);
	}
}
>>>>>>> branch 'master' of ssh://taylor@jbaron6.cs2212.ca/git/ttable
