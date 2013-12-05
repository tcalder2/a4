package ttable;

import java.util.ArrayList;
import java.util.Date;

/**
 * The class Progeny.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class Progeny {

	/** 
	 * The child's ID on the server.
	 */
	private String id;
	
	/**
	 * The child's first name.
	 */
	private String firstName;

	/**
	 * The child's date of birth.
	 */
	private Date birthDate;

	/** 
	 * Array of child's top five final game scores.
	 */
	private int[] highScores;
	
	/**
	 * Array of the child's level progeny
	 */
	private ArrayList<LevelProgeny> levelProgenys;
	
	/**
	 * The child's high score on the final game
	 */
	private int finalGameHighScore;
	
	/** 
	 * The amount of time the child is allowed to complete drill levels.
	 */
	private int timeAllowed = 30;
	
	/** 
	 * Array of progeny's level data.
	 */
	private ArrayList<LevelProgeny> levels;

	/**
	 * The child's current level
	 */
	private int level;
	
	/**
	 * Instantiates a new progeny.
	 */
	public Progeny() {}

	/**
	 * Changes the current level, removing all levels above current level.
	 *
	 * @param level		the level
	 */
	public void changeLevel(int newLevel) {
		while (true) {
			try {
			levels.remove(newLevel);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
	}

	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Gets the child's score on the final game.
	 * 
	 * @return	the final game high score
	 */
	public int getFinalGameHighScore() {
		return finalGameHighScore;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the high scores.
	 *
	 * @return the high scores
	 */
	public int[] getHighScores() {
		return highScores;
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
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Gets the LevelProgeny of a child
	 * 
	 * @return	ArrayList of LevelProgeny
	 */
	public ArrayList<LevelProgeny> getLevelProgenys() {
		return levelProgenys;
	}

	/**
	 * Gets the levels.
	 *
	 * @return the levels
	 */
	public ArrayList<LevelProgeny> getLevels() {
		return levels;
	}

	/**
	 * Gets the time allowed.
	 *
	 * @return the time allowed
	 */
	public int getTimeAllowed() {
		return timeAllowed;
	}

	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthdate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Sets the child's high score for the final game
	 * 
	 * @param finalGameHighScore	the high score
	 */
	public void setFinalGameHighScore(int finalGameHighScore) {
		this.finalGameHighScore = finalGameHighScore;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the high scores.
	 *
	 * @param highScores the new high scores
	 */
	public void setHighScores(int[] highScores) {
		this.highScores = highScores;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Sets the level progeny.
	 * 
	 * @param levelProgenys	the new level progenies
	 */
	public void setLevelProgenys(ArrayList<LevelProgeny> levelProgenys) {
		this.levelProgenys = levelProgenys;
	}

	/**
	 * Sets the levels.
	 *
	 * @param levels		the new levels
	 */
	public void setLevels(ArrayList<LevelProgeny> levels) {
		this.levels = levels;
	}

	/**
	 * Sets the time allowed for a level.
	 *
	 * @param timeAllowed	the new time allowed
	 */
	public void setTimeAllowed(int timeAllowed) {
		this.timeAllowed = timeAllowed;
	}
}