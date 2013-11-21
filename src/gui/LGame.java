package gui;

import java.awt.GridBagLayout;

import ttable.LevelProgeny;

/**
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Yaqzan Ali
 * @version 0.1
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel {

	/** The level progeny holding the level details. */
	private LevelProgeny level;

	/**
	 * Instantiates an LGame instance.
	 *
	 * @param level			the level
	 */
	public LGame(int level) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		this.level = Controller.getCurrentProgeny().getLevels().get(level - 1);
		//TODO: complete class
	}
}
