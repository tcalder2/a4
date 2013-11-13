package ttable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import json.JSONFailureException;
import json.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The class User.
 * 
 * @author James Baron
 * @author James Anderson
 * @author Chuhan Qin
 * 
 */
public class User {

	/** The user's FaceBook id. */
	private String fbId ="";

	/** The user's first name. */
	private String firstName = "";
	
	/** The user's last name. */
	private String lastName = "";
	
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
		
		user.fbId = ((String)userObj.get("fb_id"));
		user.firstName = ((String)userObj.get("first_name"));
		user.lastName = ((String)userObj.get("last_name"));
		
		return user;
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
	 * Gets an array of progeny.
	 *
	 * @return an array of progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getProgeny() throws JSONFailureException 
	{
		ArrayList<Progeny> progeny;
		
		// TODO: remove this line
		progeny = new ArrayList<Progeny>();
		
		// TODO: make server call
		
		return progeny;
	}
	
	/**
	 * Adds a progeny.
	 *
	 * @param  firstName 			the first name
	 * @param  age					the age
	 * @return 						the progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static Progeny addProgeny(String firstName, Date birthday) throws JSONFailureException {
		Json json = new Json();
		JSONObject jsonUser = (JSONObject)json.sendRequest("https://jbaron6.cs2212.ca/addchild?first_name=" + firstName + "&birthday=" + birthday);
	
		// TODO: parse the array of the new progeny
		jsonUser.get("");
		Progeny progeny = new Progeny(firstName, birthday, "" + 0); //TODO: replace this line with parsed info
		return progeny;
	}
	
	/**
	 * Removes the specified progeny.
	 * 
	 * @param progeny				the progeny to be removed
	 * @return						true if successful, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static boolean removeProgeny(Progeny progeny) throws JSONFailureException {
		//TODO: code this
		return false;
	}
	
	/**
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static ArrayList<Level> getLevels() throws JSONFailureException {
		ArrayList<Level> levels = new ArrayList<Level>();
		//TODO: replace previous line with actual server call
		return levels;
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
	 * Gets the FaceBook ID of the current user.
	 *
	 * @return the FaceBook ID
	 */
	public String getFbId() {
		return fbId;
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
	 * Gets the last name of the current user.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
}
