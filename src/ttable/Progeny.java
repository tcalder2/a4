package ttable;

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

	/** The first name. */
	private String firstName;

	/** The ID. */
	private String id;

	/** The birthday. */
	private Date birthday;

	/** Array of progeny's top five final game scores. */
	private int[] finalGameHighScores;
	
	/** The time allowed to complete drill level. */
	private int timeAllowed;
	
	/** Array of progeny's level data. */
	private ArrayList<LevelProgeny> levels;

	/**
	 * Instantiates a Progeny instance.
	 *
	 * @param firstName 			the first name
	 * @param birthday 			the birthday
	 * @param id 				the ID
	 * @param timeAllowed the time allowed
	 */
	public Progeny() {}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		GregorianCalendar calPresent = new GregorianCalendar();
		calPresent.setTime(new Date());
		GregorianCalendar calBirth = new GregorianCalendar();
		calBirth.setTime(birthday);

		int age = calPresent.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR);

		if (calBirth.get(Calendar.MONTH) < calPresent.get(Calendar.MONTH)) {
			age--; 
		}
		else if (calBirth.get(Calendar.MONTH) == calPresent.get(Calendar.MONTH)) {
			if (calBirth.get(Calendar.DAY_OF_MONTH) < calPresent.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		}
		return age;
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
	 * Gets the birthday.
	 *
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets the final game high scores.
	 *
	 * @return the final game high scores
	 */
	public int[] getFinalGameHighScores() {
		return finalGameHighScores;
	}

	/**
	 * Sets the final game high scores.
	 *
	 * @param finalGameHighScores the new final game high scores
	 */
	public void setFinalGameHighScores(int[] finalGameHighScores) {
		this.finalGameHighScores = finalGameHighScores;
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
}

