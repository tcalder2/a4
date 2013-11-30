package ttable;

// TODO: Auto-generated Javadoc
/**
 * The class responsible for holding child specific level settings and data. In
 * other words the child's progress details and the level settings that are set
 * for that child alone.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class LevelProgeny {

	/** The level number between 1 and 12 inclusive. */
	private int levelNum;

	/** The number of mistakes made during the latest drill attempt. */
	private int mistakes;

	/** The number of times the drill has been attempted. */
	private int attempts;

	/** The time it took to complete the drill on the first time it was passed. */
	private int completionTime;

	/**
	 * Instantiates a new level progeny.
	 */
	public LevelProgeny() {
	}

	/**
	 * Gets the level number between 1 and 12 inclusive.
	 * 
	 * @return the level number between 1 and 12 inclusive.
	 */
	public int getLevelNumber() {
		return levelNum;
	}

	/**
	 * Sets the level number.
	 * 
	 * @param number
	 *            the level number between 1 and 12 inclusive.
	 */
	public void setLevelNumber(int number) {
		this.levelNum = number;
	}

	/**
	 * Gets the number of mistakes made during the latest drill attempt.
	 * 
	 * @return the number of mistakes made during the latest drill attempt.
	 */
	public int getMistakes() {
		return mistakes;
	}

	/**
	 * Sets the number of mistakes made during the latest drill attempt.
	 * 
	 * @param mistakes
	 *            the number of mistakes made during latest drill attempt.
	 */
	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	/**
	 * Gets the number of times the drill has been attempted.
	 * 
	 * @return the number of times the drill has been attempted.
	 */
	public int getAttempts() {
		return attempts;
	}

	/**
	 * Sets the number of times the drill has been attempted.
	 * 
	 * @param attempts
	 *            the number of times the drill has been attempted.
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	/**
	 * Gets the time it took to complete the drill on the first time it was
	 * passed.
	 * 
	 * @return the time it took to complete the drill on the first time it was
	 *         passed.
	 */
	public int getCompletionTime() {
		return completionTime;
	}

	/**
	 * Sets the time it took to complete the drill on the first time it was
	 * passed.
	 * 
	 * @param completionTime
	 *            the time it took to complete the drill on the first time it
	 *            was passed.
	 */
	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	/**
	 * Compares the class variables to the class variables of the level progeny
	 * passed as an argument.
	 * 
	 * @param level
	 *            the level progeny object to be compared with.
	 * @return true if the objects are equal, false if they are not.
	 */
	public boolean equals(LevelProgeny level) {
		if (getLevelNumber() == level.getLevelNumber()) {
			if (getMistakes() == level.getMistakes()) {
				if (getAttempts() == level.getAttempts()) {
						if (getCompletionTime() == level.getCompletionTime()) {
							return true;
						}
				}
			}
		}
		return false;
	}
}
