package ttable;

import java.util.ArrayList;
import java.util.Date;

// TODO: Auto-generated Javadoc
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
	
	private ArrayList<LevelProgeny> levelProgenys;
	
	private int finalGameHighScore;
	
	/** 
	 * The amount of time the child is allowed to complete drill levels.
	 */
	private int timeAllowed = 30;
	
	/** 
	 * Array of progeny's level data.
	 */
	private ArrayList<LevelProgeny> levels;

	private int level;
	
	/**
	 * Instantiates a new progeny.
	 */
	public Progeny() {}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
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
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
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
	 * Gets the high scores.
	 *
	 * @return the high scores
	 */
	public int[] getHighScores() {
		return highScores;
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
	 * Gets the time allowed.
	 *
	 * @return the time allowed
	 */
	public int getTimeAllowed() {
		return timeAllowed;
	}

	/**
	 * Sets the time allowed.
	 *
	 * @param timeAllowed the new time allowed
	 */
	public void setTimeAllowed(int timeAllowed) {
		this.timeAllowed = timeAllowed;
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
	 * Sets the levels.
	 *
	 * @param levels the new levels
	 */
	public void setLevels(ArrayList<LevelProgeny> levels) {
		this.levels = levels;
	}

//	public int getLevelNumber() {
//		return 0;
//	}

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
	 * @return
	 */
	public ArrayList<LevelProgeny> getLevelProgenys() {
		return levelProgenys;
	}

	/**
	 * @param levelProgenys
	 */
	public void setLevelProgenys(ArrayList<LevelProgeny> levelProgenys) {
		this.levelProgenys = levelProgenys;
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

	public int getFinalGameHighScore() {
		return finalGameHighScore;
	}

	public void setFinalGameHighScore(int finalGameHighScore) {
		this.finalGameHighScore = finalGameHighScore;
	}
}

