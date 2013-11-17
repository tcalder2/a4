package ttable;

/**
 * The class Level.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class Level {

	/** 
	 * The level between 1 and 12 inclusive.
	 */
	private int level;
	
	/** 
	 * The number of mistakes allowed to still complete the drill level.
	 */
	private int mistakesAllowed;
	
	/**
	 * Constructor requiring the level number and number of mistakes allowed to be passed
	 * as arguments.  If either the level number or number of mistakes allowed are not
	 * within their respective ranges, an IllegalArgumentException is thrown
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @param mistakesAllowed			the number of mistakes allowed per drill between
	 * 									0 and 5 inclusive.
	 * @throws IllegalArgumentException	the exception thrown if the either the level number
	 * 									or the number of mistakes allowed are not within their
	 * 									respective ranges.
	 */
	public Level(int level, int mistakesAllowed) throws IllegalArgumentException {
		setLevel(level);
		setMistakesAllowed(mistakesAllowed);
	}
	
	/**
	 * Constructor that creates a default new level object for the specified level, with a
	 * default mistakes allowed value of 3.  If the level number is not between 1 and 12
	 * inclusive, an IllegalArgumentException is thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @throws IllegalArgumentException the exception thrown if the level number is not between
	 * 									1 and 12 inclusive.
	 */
	public Level(int level) throws IllegalArgumentException {
		setLevel(level);
		this.mistakesAllowed = 3;
	}
	
	/**
	 * Verifies that all class variables are valid.  If any variables are not valid, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @throws IllegalArgumentException	the exception thrwon if any of the variables are out of
	 * 									their respective ranges.
	 */
	@SuppressWarnings("unused")
	private void validateState() throws IllegalArgumentException {
		validateLevel(level);
		validateMistakesAllowed(mistakesAllowed);
	}
	
	/**
	 * Gets the level number between 1 and 12 inclusive.
	 *
	 * @return the level number between 1 and 12 inclusive.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Sets the level number.  If the level number is not between 1 and 12 inclusive, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @throws IllegalArgumentException the exception thrown if the level number is not between
	 * 									1 and 12 inclusive.
	 */
	private void setLevel(int level) throws IllegalArgumentException {
		validateLevel(level);
		this.level = level;
	}
	
	/**
	 * Validates the level number.  If the level number is not between 1 and 12 inclusive,
	 * an IllegalArgumentException is thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @throws IllegalArgumentException the exception thrown if the level number is not between
	 * 									1 and 12 inclusive.
	 */
	private void validateLevel(int level) throws IllegalArgumentException {
		if (level < 1 || level > 12) {
			throw new IllegalArgumentException("Level must be between 1 and 12 inclusive");
		}
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
	 * @throws IllegalArgumentException	the exception thrown if the number of mistakes allowed
	 * 									is not between 0 and 5 inclusive.
	 */
	public void setMistakesAllowed(int mistakesAllowed) throws IllegalArgumentException{
		validateMistakesAllowed(mistakesAllowed);
		this.mistakesAllowed = mistakesAllowed;
	}

	/**
	 * Validates the new number of mistakes a child is allowed to make while still completing
	 * the level.  If the Mistakes Allowed is not between 0 and 5 inclusive, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param mistakesAllowed			the number of mistakes a child can make and still
	 * 									complete the level, a value between 0 and 5 inclusive.
	 * @throws IllegalArgumentException	the exception thrown if the number of mistakes allowed
	 * 									is not between 0 and 5 inclusive.
	 */
	private void validateMistakesAllowed(int mistakesAllowed) throws IllegalArgumentException {
		if (mistakesAllowed < 0 || mistakesAllowed > 5) {
			throw new IllegalArgumentException("Mistakes Allowed must be between 0 and 5 inclusive.");
		}
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