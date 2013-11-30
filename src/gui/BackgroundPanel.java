package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The class BackgroundPanel, a JPanel with a graphic used as a backdrop for most GUI screens.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
	
	/** The background graphic. */
	private Image background;
	
	/**
	 * Constructor requiring the image URL be passed as an argument.
	 *
	 * @param imgURL	the URL of the background graphic.
	 */
	public BackgroundPanel(String imgURL) {

		try	{
			//Load the graphic and set the dimensions of the panel
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
			Dimension d = new Dimension(background.getWidth(null), background.getHeight(null));
			setPreferredSize(d);
		    setMinimumSize(d);
		    setMaximumSize(d);
		    setSize(d);
		} catch (IOException e) {
			//If there is an error loading the graphic, set the background white and display an error message
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
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
	public BackgroundPanel(String imgURL, int width, int height, LayoutManager layout) {
		
		//Set the panel dimensions based on the arguments passed
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	    setMinimumSize(d);
	    setMaximumSize(d);
	    setSize(d);
	    
	    try	{
	    	//Download the graphic
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
		} catch (IOException e) {
			//If there is an error loading the graphic, set the background white and display an error message
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
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
	public BackgroundPanel(String imgURL, LayoutManager layout) {
		
		//Call the basic constructor
		this(imgURL);
		
		//Set the layout manager to the specified type
		setLayout(layout);
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
