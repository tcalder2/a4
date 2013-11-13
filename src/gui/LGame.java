package gui;

import java.awt.GridBagLayout;

import json.JSONFailureException;
import ttable.LevelProgeny;
import ttable.Progeny;

/**
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Yaqzan Ali
 *
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel {
	
	/** The level progeny holding the level details. */
	private LevelProgeny level;
	
	/**
	 * Instantiates an LGame instance.
	 *
	 * @param controller 	the controller
	 * @param level			the level
	 */
	public LGame(Controller controller, int level) {
		
		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		
		try {
			this.level = Progeny.getLevels(controller.getCurrentProgeny()).get(level - 1);
			//TODO: complete class
		} catch (JSONFailureException e) {
			// TODO: add exception handling, popup?
		}
	}
}
