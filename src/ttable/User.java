package ttable;

import java.util.ArrayList;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.Json;
import json.JSONFailureException;

/**
 * The User class represents the user of the application's current user
 * 
 * @author CS2212 Group 4
 */

public class User {

	/** The user's first name. */
	private String firstName = "";
	
	/** The user's last name. */
	private String lastName = "";
	
	/** The user's Facebook id. */
	private String fbId ="";
	
	/**
	 * Gets the current user.
	 *
	 * @return the currently logged user
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static User getUser() throws JSONFailureException
	{
		User user = new User();
		
		Json json = new Json();
		
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");
		
		JSONObject userObj = (JSONObject)jsonObj.get("user");
		
		user.setFbId((String)userObj.get("fb_id"));
		user.setFirstName((String)userObj.get("first_name"));
		user.setLastName((String)userObj.get("last_name"));
		
		return user;
	}
	
	/**
	 * Sets the answer to the user's password recovery question.
	 *
	 * @param answer the answer
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setAnswer(String answer, String password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setanswer?answer=" + answer + "&password=" + password);
		
		return true;
	}
	
	/**
	 * Reset the user's password.
	 *
	 * @param answer the answer
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean resetPassword(String answer, String newPassword) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/resetpassword?answer=" + answer + "&new_password=" + newPassword);
		
		return true;
	}

	/**
	 * Sets the user's password recovery question.
	 *
	 * @param question the question
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setQuestion(String question, String password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setquestion?question=" + question + "&password=" + password);		
		
		return true;
	}
	
	/**
	 * Gets the current user's password recovery questions.
	 *
	 * @return the questions
	 * @throws JSONFailureException the jSON failure exception
	 */	
	public static ArrayList<String> getQuestions() throws JSONFailureException
	{
		ArrayList<String> questions = new ArrayList<String>();
		
		Json json = new Json();
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getquestions");
		
		
		JSONArray questionsArray = (JSONArray) jsonObj.get("questions");
		
		Iterator<?> questionsIt = questionsArray.iterator();
		
		while(questionsIt.hasNext())
			questions.add((String)questionsIt.next());
		
		return questions;
	}
	
	/**
	 * Sets the user's password.
	 *
	 * @param oldPassword the old password
	 * @param newpassword the new password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setPassword(String oldPassword, String newpassword) throws JSONFailureException
	{
		Json json = new Json();
		
		json.sendRequest("https://jbaron6.cs2212.ca/setpassword?new_password=" + newpassword + "&old_password=" + oldPassword);
		
		return true;
	}
	
	/**
	 * Authenticate the user.
	 *
	 * @param password the password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean authenticate(String password) throws JSONFailureException
	{
		Json json = new Json();
		
		//Will throw error if success fail.  That error will have messages.
		json.sendRequest("https://jbaron6.cs2212.ca/authenticate?password=" + password);
		
		return true;
	}

	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the user's firstname
	 * 
	 * @param firstName the user's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the current user.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user's lastname
	 * 
	 * @param firstName the user's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the Facebook id of the current user.
	 *
	 * @return the Facebook id
	 */
	public String getFbId() {
		return fbId;
	}

	/**
	 * Set's the user's Facebook ID
	 * 
	 * @param fbId the Facebook ID
	 */
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
}
