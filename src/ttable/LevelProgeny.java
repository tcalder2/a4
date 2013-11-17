package ttable;

// TODO: Auto-generated Javadoc
/**
 * The class LevelProgeny.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class LevelProgeny {

	/** The level. */
	private int level;
	
	/** The mistakes. */
	private int mistakes;
	
	/** The attempts. */
	private int attempts;
	
	/** The level high score. */
	private int levelHighScore;
	
	/** The completion time. */
	private int completionTime;
	
	/**
	 * Instantiates a new level progeny.
	 */
	public LevelProgeny() {}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMistakes() {
		return mistakes;
	}

	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getLevelHighScore() {
		return levelHighScore;
	}

	public void setLevelHighScore(int levelHighScore) {
		this.levelHighScore = levelHighScore;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	/**
	 * Sets the completion time.
	 *
	 * @param completionTime the new completion time
	 */
	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}
}
