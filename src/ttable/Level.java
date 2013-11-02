package ttable;

/**
 * This class represents a level in the game
 * 
 * @author CS2212 Group 4
 */

public class Level{
	
	// Instance Variables
	
	private int level; // The rank of the level, from 1 to 12
	
	private int mistakesAllowed; // The number of mistakes allowed for this level

	// Mutator Methods
	
	/**
	 * Set number of mistakes allowed
	 * @param mistakes number of mistakes allowed for this level
	 */
	public void setMistakesAllowed (int mistakes) {
		
		mistakesAllowed = mistakes;
		
	}

	/**
	 * Sets the current level
	 * @param lvl the new level
	 */
	public void setLevel(int lvl) {
		level = lvl;
	}

	// Accessor Methods
	
	/**
	 * Returns the number of mistakes allowed for this level
	 * @return mistakesAllowed the number of mistakes allowed
	 */
	public int getMistakesAllowed () {
		
		return mistakesAllowed;
		
	}

	/**
	 * Returns the current rank of the level
	 * @return level the current level
	 */
	public int getLevel () {
		
		return level;
		
	}
	
}