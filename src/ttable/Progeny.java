package ttable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The class Progeny.
 * 
 * @author James Baron
 * @author James Anderson
 * @author Chuhan Qin
 * 
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
	 * @param firstName				the first name
	 * @param birthday				the birthday
	 * @param id					the ID
	 */
	public Progeny(String firstName, Date birthday, String id, int timeAllowed) {
		this.firstName = firstName;
		this.id = id;
		this.birthday = birthday;
		this.finalGameHighScores = new int[]{0,0,0,0,0};
		this.timeAllowed = timeAllowed;
	}

	/**
	 * Set the time allowed per level.
	 *
	 * @param timeAllowed 			the time allowed per level
	 */
	public void setTimeAllowed(int timeAllowed) {
		this.timeAllowed = timeAllowed;
	}

	/**
	 * Gets the time allowed per level.
	 * 
	 * @return		the time allowed per level
	 */
	public int getTimeAllowed() {
		return timeAllowed;
	}

	/**
	 * Gets the level progeny.
	 *
	 * @return 		the level progeny
	 */
	public ArrayList<LevelProgeny> getLevels() {
		return levels;
	}

	/**
	 * Sets the current level, removing all levels above current level.
	 *
	 * @param level		the level
	 */
	public void setLevel(int level) {
		while (true) {
			try {
			levels.remove(level);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
	}

	/**
	 * Gets the current level.
	 * 
	 * @return		the current level
	 */
	public int getLevel() {
		return (levels.size() + 1);
	}

	/**
	 * Adds a new level.
	 * 
	 * @param level		the level
	 */
	public void addLevel(LevelProgeny level) {
		levels.add(level);
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
	 * Gets the Birthday.
	 *
	 * @return the birthday
	 * @throws ParseException 
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Sets the Birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets the ID.
	 *
	 * @return the ID
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the final game high scores
	 * 
	 * @param progeny				the progeny
	 * @return						the progeny's final game high score
	 */
	public int[] getFGameHighScores() {
		return finalGameHighScores;
	}

	/**
	 * Updates the final game high scores with new score (if it is actually a high score, else no change)
	 * 
	 * @param score					the new score achieved
	 * @return						true if score is new high score, false otherwise
	 */
	public boolean updateFGameScores(int score) {
		int[] tmp = new int[5];
		int i = 0;
		for (int j = 0; j < 5; j++) {
			if (score < finalGameHighScores[j] && i < 5) {
				tmp[i] = finalGameHighScores[j];
				i++;
			}
			else if (i < 5) {
				tmp[i] = score;
				score = -1;
				i++;
				if (i < 5) {
					tmp[i] = finalGameHighScores[j];
					i++;
				}
			}
			else {
				break;
			}
		}
		finalGameHighScores = tmp;
		if (score < 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

