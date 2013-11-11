package ttable;

import json.JSONFailureException;

/**
 * The Class LevelProgeny joins the many-to-many relationship betweel Level and Progeny and allows some game settings.
 */
public class LevelProgeny {
	
	/** The attempt count. */
	private String attempts;

	/** The final completion time. */
	private String finalCompletionTime;
	
	/** The final game high score. */
	private String finalGameHighScore;
	
	/** The id. */
	private String id;
	
	/** The level. */
	private Level level;
	
	/** The mistake count. */
	private String mistakes;
	
	/** The progeny. */
	private Progeny progeny;

	
	/**
	 * Static methods
	 */
	
	/**
	 * Sets the age.
	 *
	 * @param level progeny the new age
	 */
	public static boolean changeAge(LevelProgeny levelProgeny) throws JSONFailureException { }

	/**
	 * Delete a level progeny instance.
	 *
	 * @param levelProgeny the level progeny
	 */
	public static boolean deleteLevelProgeny(LevelProgeny levelProgeny) throws JSONFailureException { }

	/**
	 * Acessors
	 */
	
	/**
	 * Gets the attempts.
	 *
	 * @return the attempts
	 */
	public String getAttempts() throws JSONFailureException { }

	/**
	 * Gets the final completion time.
	 *
	 * @return the final completion time
	 */
	public String getFinalCompletionTime() { }

	/**
	 * Gets the final game high score.
	 *
	 * @return the final game high score
	 */
	public String getFinalGameHighScore() { }

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() { }

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Level getLevel() { }

	/**
	 * Gets the mistakes.
	 *
	 * @return the mistakes
	 */
	public String getMistakes()	{ }

	/**
	 * Gets the progeny.
	 *
	 * @return the progeny
	 */
	public Progeny getProgeny() { }

	/**
	 * Sets the attempts for this level per progeny.
	 *
	 * @param attempts the new attempts
	 */
	private boolean setAttempts(String attempts) throws JSONFailureException { }
	
	/**
	 * Sets the final completion time.
	 *
	 * @param finalCompletionTime the new final completion time
	 */
	private boolean setFinalCompletionTime(String finalCompletionTime) throws JSONFailureException { }

	/**
	 * Sets the final game high score.
	 *
	 * @param finalGameHighScore the new final game high score
	 */
	private boolean setFinalGameHighScore(String finalGameHighScore) throws JSONFailureException { }
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) { }

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(Level level) { }

	/**
	 * Sets the number of mistakes.
	 *
	 * @param mistakes the new mistakes
	 */
	private boolean setMistakes(String mistakes) throws JSONFailureException { }

	/**
	 * Sets the progeny.
	 *
	 * @param progeny the new progeny
	 */
	public void setProgeny(Progeny progeny) { }
}
