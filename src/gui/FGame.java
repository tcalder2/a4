package gui;

import java.awt.GridBagLayout;

/**
 * The class FGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 0.1
 */
@SuppressWarnings("serial")
public class FGame extends BackgroundPanel {

	/**
	 * Instantiates a FGame instance.
	 *
	 * @param controller	the controller
	 */
	public FGame(Controller controller) {
		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
	}
}
