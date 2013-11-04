package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The Class TTApplet.
 */
public class TTApplet extends JApplet {
	
	/** The controller. */
	Controller controller;
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		controller = new Controller();
		
		TitlePanel titlePanel = new TitlePanel(controller);
		controller.setHeader(titlePanel);
		MainMenu mainMenu = new MainMenu(controller);
		controller.setScreen(mainMenu);
		controller.setFrameColour(Color.black);
		this.add(controller.getPane());
	}
}
