package ttable;

/**
 * The class Level.
 * 
 * @author James Baron
 * @author James Anderson
 *
 */
public class Level{
		
	/** The level from 1 to 12. */
	private int level;
	
	/** The mistakes allowed. */
	private int mistakesAllowed;
	
	/**
	 * Instantiates a level instance.
	 * 
	 * @param level				the level
	 * @param mistakesAllowed	the number of mistakes allowed
	 */
	public Level(int level, int mistakesAllowed) {
		this.level = level;
		this.mistakesAllowed = mistakesAllowed;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Gets the number of mistakes allowed.
	 *
	 * @return the mistakes allowed
	 */
	public int getMistakesAllowed() {
		return mistakesAllowed;
	}

	/**
	 * Sets the number of mistakes allowed.
	 *
	 * @param mistakesAllowed the new mistakes allowed
	 */
	public void setMistakesAllowed(int mistakesAllowed) {
		this.mistakesAllowed = mistakesAllowed;
	}
}