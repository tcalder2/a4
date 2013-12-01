package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The class BackgroundPanel, a JPanel with a graphic used as a backdrop for most GUI screens.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {

	/** The background to display. */
	private Image background;
	
	/** The default background graphic. */
	private static Image defaultBackground = null;
	
	/** The title pane graphic. */
	private static Image titleBackground = null;

	/** The default level menu background graphic. */
	private static Image defaultLevelMenu = null;

	/** The planes level menu background graphic. */
	private static Image planesLevelMenu = null;

	/** The planes level menu background graphic. */
	private static Image castlesLevelMenu = null;

	/**
	 * Constructor requiring the image URL be passed as an argument.
	 *
	 * @param imgURL	the URL of the background graphic.
	 */
	public BackgroundPanel(int imageCode) {

		try	{
			//Load the graphic and set the dimensions of the panel
			background = loadImage(imageCode);
			Dimension d = new Dimension(background.getWidth(null), background.getHeight(null));
			setPreferredSize(d);
			setMinimumSize(d);
			setMaximumSize(d);
			setSize(d);
		} catch (IOException e) {
			new GeneralDialogue("Oops! It seems we are having trouble communicating!",
					"Communication Error", 1);
		}
	}

	/**
	 * Constructor requiring the image URL, the the dimensions and the layout be passed as
	 * arguments.
	 *
	 * @param imgURL	the URL of the background image.
	 * @param width		the desired width of the new background panel.
	 * @param height	the desired height of the new background panel.
	 * @param layout 	the layout for the new background panel.
	 */
	public BackgroundPanel(int imageCode, int width, int height, LayoutManager layout) {

		//Set the panel dimensions based on the arguments passed
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(d);

		try	{
			background = loadImage(imageCode);
		} catch (IOException e) {
			new GeneralDialogue("Oops! It seems we are having trouble communicating!", 
					"Communication Error", 1);
		}

		//Set the layout manger to the specified type
		setLayout(layout);
	}

	/**
	 * Instantiates a new background panel with graphic and specified layout.
	 *
	 * @param imgURL	the URL of the background image.
	 * @param layout	the layout for the new background panel.
	 */
	public BackgroundPanel(int imageCode, LayoutManager layout) {

		//Call the basic constructor
		this(imageCode);

		//Set the layout manager to the specified type
		setLayout(layout);
	}

	public static Image loadImage(int imageCode) throws IOException {
		switch(imageCode) {
		case 0:
			if (defaultBackground == null) {
				Image img = ImageIO.read(new URL(
						"http://jbaron6.cs2212.ca/img/default_background.png"));
				defaultBackground = new ImageIcon(img).getImage();
			}
			return defaultBackground;
		case 1:
			if (titleBackground == null) {
				Image img = ImageIO.read(new URL(
						"http://jbaron6.cs2212.ca/img/topbanner.png"));
				titleBackground = new ImageIcon(img).getImage();
			}
			return titleBackground;
		case 20:
			if (defaultLevelMenu == null) {
				Image img = ImageIO.read(new URL(
						"http://jbaron6.cs2212.ca/img/level_background.png"));
				defaultLevelMenu = new ImageIcon(img).getImage();
			}
			return defaultBackground;
		case 21:
			if (planesLevelMenu == null) {
				Image img = ImageIO.read(new URL(
						"http://jbaron6.cs2212.ca/img/themes/theme_1/level_background.png"));
				planesLevelMenu = new ImageIcon(img).getImage();
			}
			return planesLevelMenu;
		case 22:
			if (castlesLevelMenu == null) {
				Image img = ImageIO.read(new URL(
						"http://jbaron6.cs2212.ca/img/themes/theme_2/level_background.png"));
				castlesLevelMenu = new ImageIcon(img).getImage();
			}
			return castlesLevelMenu;
		}
		throw new IOException();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

	/**
	 * 
	 */
	public void close() {
		//Null method to be overwritten by screens requiring extra actions on close.
	}
}
