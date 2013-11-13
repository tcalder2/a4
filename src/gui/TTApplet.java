package gui;

import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

import json.JSONFailureException;

/**
 * The class TTApplet, a JApplet.
 * 
 * @author James Anderson
 *
 */
@SuppressWarnings("serial")
public class TTApplet extends JApplet {
	
	/** The controller. */
	Controller controller;
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		try {
			//Creates a controller responsible for managing the display and more
			controller = new Controller();
			
			//Create the title panel and add to the view
			TitlePanel titlePanel = new TitlePanel(controller);
			controller.setBanner(titlePanel);
			
			//Create the welcome screen and add to the view
			Welcome welcome = new Welcome(controller);
			controller.setScreen(welcome);
			
			//Set the main split pane frame colour
			controller.setFrameColour(Color.BLACK);
			
			//Add the split pane to the applet view
			this.add(controller.getPane());
		
		//If there is a communication error display a panel with an error message
		} catch (JSONFailureException e) {
			JPanel screen = new JPanel();
			screen.add(new JLabel("<html>Oops!<br>"
					+ "It appears we are having some issues!<br>"
					+ "Make sure you are logged into Facebook and try again.</html>"));
			this.add(screen);
		}
	}
}
