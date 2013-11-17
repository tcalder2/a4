package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ttable.Progeny;
import ttable.User;
import json.JSONFailureException;
import json.Json;

public class UserService {
	
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
	 * Posts a message to Facebook to indicate a child's score
	 *
	 * @param name	the child's name
	 * @param score	the score
	 * @param level	the level
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean postMessage(String name, int score, int level) throws JSONFailureException
	{
		Json json = new Json();
		String message = (name + " just reached a score of " + score + " on level " + level + "!");
		json.sendRequest("https://jbaron6.cs2212.ca/postmessage?message=" + message);
		
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
	 * Sets the number of mistakes allowed for a specified level.
	 *
	 * @param mistakesAllowed the new mistakes allowed
	 */
	public static void setMistakesAllowed(int level, int mistakesAllowed) throws JSONFailureException {
		//TODO:  server call to update
	}
	
	/**
	 * Gets the current user's password recovery questions.
	 *
	 * @return the questions		the array of security questions
	 * @throws JSONFailureException the JSON failure exception
	 */	
	public static ArrayList<String> getSecurityQuestions() throws JSONFailureException
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
	 * Returns if this is the first login attempt or not.
	 * 
	 * @return						true if this is the first login, false otherwise
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static boolean isFirstLogin() throws JSONFailureException {
		//TODO: server call
		return false;
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
		
		user.setFbId(((String)userObj.get("fb_id")));
		user.setFirstName(((String)userObj.get("first_name")));
		user.setLastName(((String)userObj.get("last_name")));
		
		return user;
	}
	
	/**
	 * Gets the users friends list
	 *
	 * @return 						an array of friends
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<String> getFriends() throws JSONFailureException 
	{
		ArrayList<String> friendList;

		friendList = new ArrayList<String>();
		
		// TODO: make server call and parse list
		
		return friendList;
	}
	
	/**
	 * Reset the user's password.
	 *
	 * @param oldPassword			the old password
	 * @param newPassword			the new password
	 * @return 						true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean resetPassword(String oldPassword, String newPassword) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/resetpassword?answer=" + oldPassword + "&new_password=" + newPassword);
		
		return true;
	}
	
	/**
	 * Gets a friend's progeny.
	 *
	 * @return 						an array of progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getFriendProgeny() throws JSONFailureException 
	{
		ArrayList<Progeny> progenyList;

		progenyList = new ArrayList<Progeny>();
		
		// TODO: make server call and parse list
		
		return progenyList;
	}
	
	/**
	 * Adds a progeny.
	 *
	 * @param  progeny				the progeny to add
	 * @return 						the newly added progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static Progeny addProgeny(String firstName, Date birthday) throws JSONFailureException {
		
		// TODO: server request
		Progeny result = new Progeny("Name", new Date(), "" + 0, 30); //TODO: replace this line with parsed info
		return result;
	}
	
	/**
	 * Removes the specified progeny.
	 * 
	 * @param progeny				the progeny to be removed
	 * @return						true if successful, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static boolean removeProgeny(Progeny progeny) throws JSONFailureException {
		//TODO: code this, perhaps use progeny id to refer to the progeny
		return false;
	}	

}
