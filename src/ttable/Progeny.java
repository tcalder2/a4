package ttable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.simple.JSONObject;

import json.Json;
import json.JSONFailureException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ttable.Level;
import ttable.Progeny;

/**
 * The Class Progeny.
 */
public class Progeny {

	/** The first name. */
	private String firstName;
	
	/** The ID. */
	private String id;
	
	/** The birthday. */
	private Date birthday;
	
	/** The progeny's final game high score. */
	private int finalGameHighScore;
	
	/**
	 * Instantiates a Progeny instance.
	 * 
	 * @param firstName				the first name
	 * @param birthday				the birthday
	 * @param id					the ID
	 */
	public Progeny(String firstName, Date birthday, String id) {
		this.firstName = firstName;
		this.id = id;
		this.birthday = birthday;
	}

	/**
	 * Set the time allowed per level.
	 *
	 * @param progeny 				the progeny
	 * @param timeAllowed 			the time allowed per level
	 * @return 						true if successful, false otherwise
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static void setTimeAllowed(Progeny progeny, String timeAllowed) throws JSONFailureException {
		//TODO: Make server call
	}
	
	/**
	 * Gets the time allowed per level.
	 * 
	 * @param progeny				the progeny
	 * @return						the time allowed per level
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static int getTimeAllowed(Progeny progeny) throws JSONFailureException {
		//TODO: Make server call
		return 0;
	}

	/**
	 * Gets the level progeny.
	 *
	 * @param progeny				the progeny
	 * @return 						the level progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<LevelProgeny> getLevels(Progeny progeny) throws JSONFailureException {
		ArrayList<LevelProgeny> levels;
		
		// TODO: delete this line
		levels = new ArrayList<>();
		
		//TODO: make server call
		
		//TODO: parse the array
		
		return levels;
	}

	/**
	 * Sets the current level.
	 *
	 * @param level					the level
	 * @return						true if successful, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static void setLevel(int level) throws JSONFailureException {
		//TODO: Make server call, this should delete all level progeny for levels greater than the level set
	}
	
	/**
	 * Gets the current level.
	 * 
	 * @param progeny				the progeny
	 * @return						the current level
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static int getLevel(Progeny progeny) throws JSONFailureException {
		return Progeny.getLevels(progeny).size();
	}
	
	/**
	 * Adds a new level.
	 * 
	 * @param level					the level
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static void addLevel(LevelProgeny level) throws JSONFailureException {
		//TODO: Make server call
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
		//TODO: Make server call
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
	 * Gets the final game high score
	 * 
	 * @param progeny				the progeny
	 * @return						the progeny's final game high score
	 */
	public int getFGameHighScore() {
		return finalGameHighScore;
	}
	
	/**
	 * Updates the final game high score (ie. if the new score is greater than old high score then 
	 * changes it, otherwise leaves it the same).
	 * 
	 * @param score					the new score achieved
	 * @return						true if score is new high score, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public boolean updateFGameScore(Progeny progeny, int score) throws JSONFailureException {
		if (score > progeny.getFGameHighScore()) {
			//TODO: Make server call
			finalGameHighScore = score;
			return true;
		}
		else {
			return false;
		}
	}
}

