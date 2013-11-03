package ttable;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import json.Json;
import json.Json.JSONFailureException;

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
		
		JSONObject json_obj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");
		
		JSONObject user_obj = (JSONObject)json_obj.get("user");
		
		user.setFbId((String)user_obj.get("fb_id"));
		user.setFirstName((String)user_obj.get("first_name"));
		user.setLastName((String)user_obj.get("last_name"));
		
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
	 * @param new_password the new_password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean resetPassword(String answer, String new_password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/resetpassword?answer=" + answer + "&new_password=" + new_password);
		
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
		JSONObject json_obj = json.sendRequest("https://jbaron6.cs2212.ca/getquestions");
		
		
		JSONArray questions_array = (JSONArray) json_obj.get("questions");
		
		Iterator<?> questions_it = questions_array.iterator();
		
		while(questions_it.hasNext())
			questions.add((String)questions_it.next());
		
		return questions;
	}
	
	/**
	 * Sets the user's password.
	 *
	 * @param old_password the old_password
	 * @param new_password the new_password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setPassword(String old_password, String new_password) throws JSONFailureException
	{
		Json json = new Json();
		
		json.sendRequest("https://jbaron6.cs2212.ca/setpassword?new_password=" + new_password + "&old_password=" + old_password);
		
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
