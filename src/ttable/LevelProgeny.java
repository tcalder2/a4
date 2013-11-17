package ttable;

/**
 * The class LevelProgeny.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class LevelProgeny {

	

	/**
	 * The level number between 1 and 12 inclusive.
	 */
	private int level;
	
	/**
	 * The number of mistakes made during the latest drill attempt.
	 */
	private int mistakes;
	
	/**
	 * The number of times the drill has been attempted.
	 */
	private int attempts;
	
	/**
	 * The current high score for the level game.
	 */
	private int levelHighScore;
	
	/** 
	 * The time it took to complete the drill on the first time it was passed.
	 */
	private int completionTime;
	
	/**
	 * Constructor requiring all of the variables be passed as arguments.  If any of the
	 * variables are not within their respective ranges, an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @param mistakes					the number of mistakes made during the latest drill
	 * 									attempt.
	 * @param attempts					the number of times the drill has been attempted.
	 * @param levelHighScore		the current high score  for the level game.
	 * @param completionTime		the time it took to complete the drill the first it 
	 * 									was passed.
	 * @throws IllegalArgumentException	the exception thrown if any of the arguments passed
	 * 									are out of their respective ranges.
	 */
	public LevelProgeny(int level, int mistakes, int attempts, int levelHighScore, int completionTime) 
	throws IllegalArgumentException {
		setLevel(level);
		setMistakes(mistakes);
		setAttempts(attempts);
		setLevelHighScore(levelHighScore);
		setCompletionTime(completionTime);
	}
	
	/**
	 * Constructor that creates the default new object of the specified level, with all
	 * other values set to zero.  If the level is not between 1 and 12 inclusive, an
	 * IllealArgumentException is thrown.
	 * 
	 * @param level						the level number between 1 and 12 inclusive.
	 * @throws IllegalArgumentException	the exception thrown if any of the arguments passed
	 * 									are out of their respective ranges. 
	 */
	public LevelProgeny(int level) throws IllegalArgumentException {
		setLevel(level);
		mistakes = 0;
		attempts = 0;
		levelHighScore = 0;
		completionTime = 0;
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
		validateAttempts(attempts);
		validateLevelHighScore(levelHighScore);
		validateCompletionTime(completionTime);
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
	 * Gets the number of mistakes made during the latest drill attempt.
	 *
	 * @return the number of mistakes made during the latest drill attempt.
	 */
	public int getMistakes() {
		return mistakes;
	}

	/**
	 * Sets the number of mistakes made during the latest drill attempt. If the number of
	 * mistakes made is negative, an IllegalArgumentException is thrown.
	 *
	 * @param mistakes					the number of mistakes made during latest drill attempt.
	 * @throws IllegalArgumentException	the exception thrown if the number of mistakes made
	 * 									during the latest drill attempt is negative.
	 */
	public void setMistakes(int mistakes) throws IllegalArgumentException {
		validateMistakes(mistakes);
		this.mistakes = mistakes;
	}
	
	/**
	 * Validates the a new number of mistakes made during the latest drill attempt. If the number of
	 * mistakes made is negative, an IllegalArgumentException is thrown.
	 * 
	 * @param mistakes					the number of mistakes made during latest drill attempt.
	 * @throws IllegalArgumentException	the exception thrown if the number of mistakes made
	 * 									during the latest drill attempt is negative.
	 */
	private void validateMistakes(int mistakes) throws IllegalArgumentException {
		if (mistakes < 0) {
			throw new IllegalArgumentException("Mistakes Made must be a positive value.");
		}
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
	 * Sets the number of times the drill has been attempted.  If the number of attempts is
	 * negative, an IllegalArgumentException is thrown.
	 *
	 * @param attempts					the number of times the drill has been attempted.
	 * @throws IllegalArgumentException	the exception thrown if the number of times the drill has
	 * 									been attempted is negative.
	 */
	public void setAttempts(int attempts)  throws IllegalArgumentException {
		validateAttempts(attempts);
		this.attempts = attempts;
	}
	
	/**
	 * Validates the number of drill attempts made.  If the number of attempts is negative, an 
	 * IllegalArgumentException is thrown.
	 * 
	 * @param attempts					the number of times the drill has been attempted.
	 * @throws IllegalArgumentException	the exception thrown if the number of times the drill has
	 * 									been attempted is negative.
	 */
	private void validateAttempts(int attempts) throws IllegalArgumentException {
		if (attempts < 0) {
			throw new IllegalArgumentException("Attempts Made must be a positive value.");
		}
	}

	/**
	 * Gets The current high score for the level game.
	 *
	 * @return The current high score for the level game.
	 */
	public int getLevelHighScore() { 		
		return levelHighScore;
	}

	/**
	 * Sets the level game high score. If the new level game high score is negative, an
	 * IllegalArgumentException is thrown.
	 *
	 * @param levelHighScore			the new level game high score.
	 * @throws IllegalArgumentException	the exception thrown if the new level game high score is
	 * 									is negative.
	 */
	public void setLevelHighScore(int levelHighScore) throws IllegalArgumentException {
		validateLevelHighScore(levelHighScore);
		this.levelHighScore = levelHighScore;
	}
	
	/**
	 * Validates the new level game high score. If the new level game high score is negative, an
	 * IllegalArgumentException is thrown.
	 *
	 * @param levelHighScore			the new level game high score.
	 * @throws IllegalArgumentException	the exception thrown if the new level game high score is
	 * 									is negative.
	 */
	private void validateLevelHighScore(int levelHighScore) throws IllegalArgumentException {
		if (level < 0) {
			throw new IllegalArgumentException("Level Game High Score must be a positive value");
		}
	}

	/**
	 * Gets the time it took to complete the drill on the first time it was passed.
	 *
	 * @return the time it took to complete the drill on the first time it was passed.
	 */
	public int getCompletionTime() {
		return completionTime;
	}

	/**
	 * Sets the time it took to complete the drill on the first time it was passed. If the
	 * completion time is not between 0 and 120 seconds, an IllegalArgumentException is thrown.
	 *
	 * @param completionTime 			the time it took to complete the drill on the first time
	 * 									it was passed.
	 * @throws IllegalArgumentException	the exception thrown if the completion time passed is not
	 * 									between 0 and 120 inclusive.
	 */
	public void setCompletionTime(int completionTime) throws IllegalArgumentException {
		validateCompletionTime(completionTime);
		this.completionTime = completionTime;
	}
	
	/**
	 * Validates the time it took to complete the drill on the first time it was passed. If the
	 * completion time is not between 0 and 120 seconds, an IllegalArgumentException is thrown.
	 *
	 * @param completionTime 			the time it took to complete the drill on the first time
	 * 									it was passed.
	 * @throws IllegalArgumentException	the exception thrown if the completion time passed is not
	 * 									between 0 and 120 inclusive.
	 */
	private void validateCompletionTime(int completionTime) throws IllegalArgumentException {
		if (completionTime < 0 || completionTime > 120) {
			throw new IllegalArgumentException("Completion Time must be between 0 and 120 seconds "
					+ "inclusive");
		}
	}
	
	/**
	 * Compares the class variables to the class variables of the level progeny passed as an argument.
	 * 
	 * @param level		the level progeny object to be compared with.
	 * @return			true if the objects are equal, false if they are not.
	 */
	public boolean equals(LevelProgeny level) {
		if (getLevel() == level.getLevel()) {
			if (getMistakes() == level.getMistakes()) {
				if (getAttempts() == level.getAttempts()) {
					if (getLevelHighScore() == level.getLevelHighScore()) {
						if (getCompletionTime() == level.getCompletionTime()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
