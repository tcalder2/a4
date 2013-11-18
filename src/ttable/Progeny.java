package ttable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	
	/** 
	 * The amount of time the child is allowed to complete drill levels.
	 */
	private int timeAllowed;
	
	/** 
	 * Array of progeny's level data.
	 */
	private ArrayList<LevelProgeny> levels;

	
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

	public int getLevelNumber() {
		return 0;
	}

	public void changeLevel(int currentLevel) {
		// TODO Auto-generated method stub
		
	}
	
	
}

