package ttable;

/**
 * The class LevelProgeny.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class LevelProgeny {

	/** The number of mistakes made on latest play-through. */
	private int mistakes;
	
	/** The number of attempts made. */
	private int attempts;
	
	/** The final game high score. */
	private int levelGameHighScore;
	
	/** The final completion time. */
	private int finalCompletionTime;

	/** The progeny. */
	private Progeny progeny;
	
	/** The level. */
	private Level level;
	
	/** The id. */
	private String id;

	/**
	 * Instantiates a LevelProgeny instance.
	 * 
	 * @param level					the level
	 * @param progeny				the progeny
	 * @param id					the id
	 * @param mistakes				the number of mistakes made
	 * @param attempts				the number of attempts made
	 * @param levelGameHighScore	the level game high score
	 * @param finalCompletionTime	the time it took to complete the drill when completed
	 */
	public LevelProgeny(Level level, Progeny progeny, String id, int mistakes, int attempts, int levelGameHighScore, int finalCompletionTime) {
		this.level = level;
		this.progeny = progeny;
		this.id = id;
		this.mistakes = mistakes;
		this.attempts = attempts;
		this.levelGameHighScore = levelGameHighScore;
		this.finalCompletionTime = finalCompletionTime;
	}
	
	/**
	 * Gets the number of mistakes.
	 *
	 * @return the number of mistakes
	 */
	public int getMistakes() {
		return mistakes;
	}

	/**
	 * Sets the number of mistakes made on the latest play through.
	 *
	 * @param mistakes		the new mistakes
	 */
	private void setMistakesMade(int mistakes) {
		this.mistakes = mistakes;
	}

	/**
	 * Gets the number of attempts.
	 *
	 * @return the attempts
	 */
	public int getAttempts() { 
		return attempts;
	}

	/**
	 * Sets the number of attempts.
	 *
	 * @param attempts		the new attempts
	 */
	private void setAttempts(int attempts)  { 
		this.attempts = attempts;
	}

	/**
	 * Gets the level game high score.
	 *
	 * @return the level game high score
	 */
	public int getLevelGameHighScore() { 		
		return levelGameHighScore;
	}

	/**
	 * Sets the level game high score.
	 *
	 * @param levelGameHighScore	the new level game high score
	 */
	private void setLevelGameHighScore(int levelGameHighScore) {
		this.levelGameHighScore = levelGameHighScore;
	}

	/**
	 * Gets the final completion time.
	 *
	 * @return the final completion time
	 */
	public int getFinalCompletionTime() {
		return finalCompletionTime;
	}

	/**
	 * Sets the drill completion time on the attempt when they completed the level.
	 *
	 * @param finalCompletionTime 	the new final completion time
	 */
	private void setFinalCompletionTime(int finalCompletionTime) {
		this.finalCompletionTime = finalCompletionTime;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the progeny.
	 *
	 * @return the progeny
	 */
	public Progeny getProgeny() {
		return progeny;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}
}
