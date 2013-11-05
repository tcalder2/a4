package ttable;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import json.Json;
import json.JSONFailureException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ttable.Level;
import ttable.Progeny;

// TODO: Auto-generated Javadoc
/**
 * The Class Progeny.
 */
public class Progeny {

	/**
	 * Fields.
	 */

	/** The age. */
	private String age;

	/** The first name. */
	private String firstName;

	/** The id. */
	private String id;

	/** The time allowed. */
	private String timeAllowed;

	/**
	 * Adds the progeny.
	 * 
	 * @param firstName
	 *            the first name
	 * @param age
	 *            the age
	 * @return the progeny
	 * @throws JSONFailureException
	 *             the jSON failure exception
	 */
	public static Progeny addProgeny(String firstName, String age) throws JSONFailureException { }

	/**
	 * Change age.
	 * 
	 * @param progeny
	 *            the progeny
	 * @param age
	 *            the age
	 * @return true, if successful
	 * @throws JSONFailureException
	 *             the jSON failure exception
	 */
	public static boolean changeAge(Progeny progeny, String age) throws JSONFailureException { }

	/**
	 * Change time allowed.
	 * 
	 * @param progeny
	 *            the progeny
	 * @param timeAllowed
	 *            the time allowed
	 * @return true, if successful
	 * @throws JSONFailureException
	 *             the jSON failure exception
	 */
	public static boolean changeTimeAllowed(Progeny progeny, String timeAllowed) throws JSONFailureException { }

	/**
	 * Static methods.
	 *
	 * @param progeny the progeny
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */

	/**
	 * Delete progeny.
	 * 
	 * @param progeny
	 *            the progeny
	 * @return true, if successful
	 * @throws JSONFailureException
	 *             the jSON failure exception
	 */
	public static boolean deleteProgeny(Progeny progeny) throws JSONFailureException { }

	/**
	 * Gets the LevelProgenys.
	 *
	 * @param levelProgeny the level progeny
	 * @return the LevelProgenys
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Level> getLevelProgenys(LevelProgeny levelProgeny) throws JSONFailureException { }

	/**
	 * Sets the level.
	 * 
	 * @param level
	 *            the level
	 * @return true, if successful
	 * @throws JSONFailureException
	 *             the jSON failure exception
	 */
	public static boolean setLevel(Integer level) throws JSONFailureException {	}

	/**
	 * Accessors.
	 *
	 * @return the age
	 */

	/**
	 * Gets the age.
	 * 
	 * @return the age
	 */
	public String getAge() { }

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() { }

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {	}

	/**
	 * Gets the time allowed.
	 * 
	 * @return the time allowed
	 */
	public String getTimeAllowed() { }

	/**
	 * Sets the age.
	 * 
	 * @param age
	 *            the new age
	 */
	public void setAge(String age) { }

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) { }

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) { }

	/**
	 * Sets the time allowed.
	 * 
	 * @param timeAllowed
	 *            the new time allowed
	 */
	public void setTimeAllowed(String timeAllowed) { }

}
