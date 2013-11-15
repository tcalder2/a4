package gui;

import java.awt.GridBagLayout;

import ttable.LevelProgeny;

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

		this.level = controller.getCurrentProgeny().getLevels().get(level - 1);
		//TODO: complete class
	}
}
