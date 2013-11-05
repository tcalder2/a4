package ttable;

import java.util.ArrayList;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.Json;
import json.JSONFailureException;

/**
 * The User class represents the user of the application's current user
 */
public class User {

	/**
	 * Fields
	 */
	
	/** The user's Facebook id. */
	private String fbId ="";

	/** The user's first name. */
	private String firstName = "";
	
	/** The user's last name. */
	private String lastName = "";
	
	/** The progenies. */
	private ArrayList<Progeny> progenies;
	
	/**
	 * Static methods
	 */
	
	/**
	 * Authenticate the user.
	 *
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean authenticate(String password) throws JSONFailureException { }
	
	/**
	 * Gets the progenies.
	 *
	 * @return the progenies
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getProgenies() throws JSONFailureException { }
	
	/**
	 * Gets the current user's password recovery questions.
	 *
	 * @return the questions
	 * @throws JSONFailureException the jSON failure exception
	 */	
	public static ArrayList<String> getQuestions() throws JSONFailureException { }
	
	/**
	 * Gets the current user.
	 *
	 * @return the currently logged user
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static User getUser() throws JSONFailureException { }
	
	/**
	 * Reset the user's password.
	 *
	 * @param answer the answer
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean resetPassword(String answer, String newPassword) throws JSONFailureException { }

	/**
	 * Sets the answer to the user's password recovery question.
	 *
	 * @param answer the answer
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setAnswer(String answer, String password) throws JSONFailureException { }
	
	/**
	 * Sets the user's password.
	 *
	 * @param oldPassword the old password
	 * @param newpassword the new password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setPassword(String oldPassword, String newpassword) throws JSONFailureException { }
	
	/**
	 * Sets the user's password recovery question.
	 *
	 * @param question the question
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setQuestion(String question, String password) throws JSONFailureException { }
		
	/**
	 * Accessors
	 */
	
	/**
	 * Gets the Facebook id of the current user.
	 *
	 * @return the Facebook id
	 */
	public String getFbId() { }

	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstName() { }

	/**
	 * Gets the last name of the current user.
	 *
	 * @return the last name
	 */
	public String getLastName() { }

	/**
	 * Set's the user's Facebook ID
	 * 
	 * @param fbId the Facebook ID
	 */
	public void setFbId(String fbId) { }
	
	/**
	 * Sets the user's firstname
	 * 
	 * @param firstName the user's first name
	 */
	public void setFirstName(String firstName) { }

	/**
	 * Sets the user's lastname
	 * 
	 * @param firstName the user's last name
	 */
	public void setLastName(String lastName) { }
	
}
