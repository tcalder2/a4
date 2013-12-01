package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;

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

	/**
	 * Constructor requiring the image URL be passed as an argument.
	 *
	 * @param imgURL	the URL of the background graphic.
	 */
	public BackgroundPanel(int imageCode) {

		try	{
			//Load the graphic and set the dimensions of the panel
			background = Controller.loadBackground(imageCode);
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
			background = Controller.loadBackground(imageCode);
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
