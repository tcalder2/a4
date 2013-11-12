package ttable;

import json.JSONFailureException;

/**
 * The Class LevelProgeny.
 * 
 * Description: Handles a many-to-many join between Level and Progeny and provides settings for the game logic.
 */
public class LevelProgeny {

	/** The number of mistakes made. */
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
	 * Sets the mistakes.
	 *
	 * @param mistakes the new mistakes
	 * @throws JSONFailureException the JSON failure exception
	 */
	private void setMistakes(int mistakes) throws JSONFailureException  {
		// TODO: Make server call
		this.mistakes = mistakes;
	}

	/**
	 * Gets the attempts.
	 *
	 * @return the attempts
	 */
	public int getAttempts() { 
		return attempts;
	}

	/**
	 * Sets the attempts.
	 *
	 * @param attempts the new attempts
	 * @throws JSONFailureException the JSON failure exception
	 */
	private void setAttempts(int attempts) throws JSONFailureException  { 
		// TODO: Make server call
		this.attempts = attempts;
	}

	/**
	 * Gets the final game high score.
	 *
	 * @return the final game high score
	 */
	public int getLevelGameHighScore() { 		
		return levelGameHighScore;
	}

	/**
	 * Sets the final game high score.
	 *
	 * @param finalGameHighScore the new final game high score
	 * @throws JSONFailureException the JSON failure exception
	 */
	private void setLevelGameHighScore(int finalGameHighScore) throws JSONFailureException {
		// TODO: make server call
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
	 * Sets the final completion time.
	 *
	 * @param finalCompletionTime 	the new final completion time
	 * @throws JSONFailureException the JSON failure exception
	 */
	private void setFinalCompletionTime(int finalCompletionTime) throws JSONFailureException {
		// TODO: Make server call
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
