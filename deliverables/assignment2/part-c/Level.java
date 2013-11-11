package ttable;

import java.util.ArrayList;

import json.JSONFailureException;

/**
 * The Class Level.
 */
public class Level {
	
	// Instance Variables
	
	/** The level from 1 to 12. */
	private int level;
	
	/** The mistakes allowed. */
	private int mistakesAllowed;

	/**
	 * Gets the LevelProgenys.
	 *
	 * @param levelProgeny the level progeny
	 * @return the LevelProgenys
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Level> getLevelProgenys(LevelProgeny levelProgeny) throws JSONFailureException { }

	/**
	 * Change mistakes allowed.
	 *
	 * @param level the level
	 * @return true, if successful
	 */
	public boolean changeMistakesAllowed(Level level) throws JSONFailureException { }

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() { }

	/**
	 * Gets the mistakes allowed.
	 *
	 * @return the mistakes allowed
	 */
	public int getMistakesAllowed() { }

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(int level) { }
	
	/**
	 * Sets the mistakes allowed.
	 *
	 * @param mistakesAllowed the new mistakes allowed
	 */
	public void setMistakesAllowed(int mistakesAllowed) { }

}