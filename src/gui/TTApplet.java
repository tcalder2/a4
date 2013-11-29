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
 * @version 1.6
 */
@SuppressWarnings("serial")
public class TTApplet extends JApplet {
		
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		try {
			//Sets up the GUI controller
			Controller.initialise();
			
			//Create the title panel and add to the view
			TitlePanel titlePanel = new TitlePanel();
			Controller.setBanner(titlePanel);
			
			//Create the welcome screen and add to the view
			Welcome welcome = new Welcome();
			Controller.setScreen(welcome);
			
			//Set the main split pane frame colour
			Controller.setFrameColour(Color.BLACK);
			
			//Add the split pane to the applet view
			this.add(Controller.getPane());
		
		//If there is a communication error display a panel with an error message
		} catch (JSONFailureException e) {
			JPanel screen = new JPanel();
			screen.add(new JLabel("<html>Oops!<br>"
					+ "It appears we are having some issues!<br>"
					+ "Make sure you are logged into Facebook and try again.</html>"));
			this.add(screen);
		} catch (Exception e) {
			String error = e.getMessage();
			if (error == null) {
				error = "An unknown error has occured";
			}
			new GeneralDialogue(error, "Error!", 1);
		}
	}
}
