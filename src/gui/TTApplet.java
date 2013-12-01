package gui;

import java.awt.Color;

import javax.swing.JApplet;

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
		} catch (Exception e) {
			String error = e.getMessage();
			if (error == null) {
				error = "Oops! It appears we are having some issues! Make sure you"
						+ " are logged into Facebook and try again.";
			}
			new GeneralDialogue(error, "Unknown Error!", 1);
		}
	}
}
