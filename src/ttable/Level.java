package ttable;

// TODO: Auto-generated Javadoc
/**
 * The class responsible for holding user specific level settings that are shared for
 * all children playing on that users account.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class Level {

	/** The level. */
	private int level;
	
	/** The mistakes allowed. */
	private int mistakesAllowed;

	private String id;
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * Gets the mistakes allowed.
	 *
	 * @return the mistakes allowed
	 */
	public int getMistakesAllowed() {
		return mistakesAllowed;
	}

	/**
	 * Sets the mistakes allowed.
	 *
	 * @param mistakesAllowed the new mistakes allowed
	 */
	public void setMistakesAllowed(int mistakesAllowed) {
		this.mistakesAllowed = mistakesAllowed;
	}
	
	/**
	 * Compares the class variables to the class variables of the level passed as an argument.
	 * 
	 * @param level		the level object to be compared with.
	 * @return			true if the objects are equal, false if they are not.
	 */
	public boolean equals(Level level) {
		if (getLevel() == level.getLevel()) {
			if (getMistakesAllowed() == level.getMistakesAllowed()) {
				return true;
			}
		}
		return false;
	}
}
