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

	/** The level between 1 and 12 inclusive. */
	private int level;
	
	/** The number of mistakes allowed to still complete the drill level. */
	private int mistakesAllowed = 3;


	/** The server object ID for the level. */
	private String id;
		
	/**
	 * Gets the server object ID for the level.
	 * 
	 * @return	the server object ID for the level.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the server object ID for the level.
	 * 
	 * @param id	the server object ID for the level.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the level number between 1 and 12 inclusive.
	 *
	 * @return the level number between 1 and 12 inclusive.
	 */
	public int getLevelNumber() {
		return level;
	}
	
	/**
	 * Sets the level number.  If the level number is not between 1 and 12 inclusive, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 */
	public void setLevelNumber(int level) {
		this.level = level;
	}
	
	/**
	 * Gets the number of mistakes a child can make in a drill and still complete the level.
	 *
	 * @return the number of mistakes a child can make in a drill and still complete the level.
	 */
	public int getMistakesAllowed() {
		return mistakesAllowed;
	}

	/**
	 * Sets the number of mistakes a child can make in a drill and still complete the level.
	 * If the Mistakes Allowed is not between 0 and 5 inclusive, an IllegalArgumentException
	 * is thrown.
	 *
	 * @param mistakesAllowed			the number of mistakes a child can make and still
	 * 									complete the level, a value between 0 and 5 inclusive.
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
		if (getLevelNumber() == level.getLevelNumber()) {
			if (getMistakesAllowed() == level.getMistakesAllowed()) {
				return true;
			}
		}
		return false;
	}
}
