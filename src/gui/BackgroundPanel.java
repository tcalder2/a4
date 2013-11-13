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
 * The BackgroundPanel class.
 * 
 * @author James Anderson
 * 
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
	
	/** The background graphic. */
	private Image background;
	
	/**
	 * Instantiates a new background panel with graphic.
	 *
	 * @param imgURL	the URL of the image
	 */
	public BackgroundPanel(String imgURL) {
		try	{
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
			Dimension d = new Dimension(background.getWidth(null), background.getHeight(null));
			setPreferredSize(d);
		    setMinimumSize(d);
		    setMaximumSize(d);
		    setSize(d);
		} catch (IOException e) {
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
		}
	}
	
	/**
	 * Instantiates a new background panel with graphic of specified size and with the specified layout.
	 *
	 * @param imgURL	the URL of the image
	 * @param width		the width
	 * @param height	the height
	 * @param layout 	the layout
	 */
	public BackgroundPanel(String imgURL, int width, int height, LayoutManager layout) {
		
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	    setMinimumSize(d);
	    setMaximumSize(d);
	    setSize(d);
	    try	{
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
		} catch (IOException e) {
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
		}
	    setLayout(layout);
	}
	
	/**
	 * Instantiates a new background panel with graphic and specified layout.
	 *
	 * @param imgURL	the URL of the image
	 * @param layout	the layout
	 */
	public BackgroundPanel(String imgURL, LayoutManager layout) {
		this(imgURL);
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
}
