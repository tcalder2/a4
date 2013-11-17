package ttable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import json.JSONFailureException;
import json.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author James Anderson
 * @author James Baron
 * @author Chuhan Qin
 * @version 1.0
 */
public class Services {
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
	 * Returns whether test mode is on or not.
	 * 
	 * @return						true if test mode is on, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static boolean isTestMode() throws JSONFailureException {
		//TODO: server call
		return false;
	}
	
	/**
	 * Set whether test mode is on or not.
	 * 
	 * @param testMode				the test mode boolean containing whether test mode is activated
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static void setTestMode(boolean testMode) throws JSONFailureException {
		//TODO: server call
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
	 * Get the users security question number.
	 * 
	 * @return						the users security question number
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static int getSecurityQuestionNumber() throws JSONFailureException {
		//TODO: add server call
		return 1;
	}
	
	/**
	 * Tests the security question answer for validity.
	 * 
	 * @param answer				the answer input by the user to be authenticated
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static void testSecurityQuestion(String answer) throws JSONFailureException {
		//TODO: add server call
	}
	
	/**
	 * Gets the current user.
	 *
	 * @return the currently logged user
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static User getUser() throws JSONFailureException
	{		
		Json json = new Json();
		
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");
		
		JSONObject userObj = (JSONObject)jsonObj.get("user");
		
		//TODO: add parsing for other fields and update the return statement
		int id = Integer.parseInt((String)userObj.get("fb_id"));
		String firstName = ((String)userObj.get("first_name"));
		String lastName = ((String)userObj.get("last_name"));
		
		return new User(id, firstName, lastName, false, false, new ArrayList<Progeny>(), new ArrayList<Level>());
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
	 * Gets an array of progeny.
	 *
	 * @return 						an array of progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getProgeny() throws JSONFailureException 
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
		Progeny result = new Progeny(0, firstName, new Date()); //TODO: replace this line with parsed info
		return result;
	}
	
	/**
	 * Removes the specified progeny.
	 * 
	 * @param id					the progeny's id
	 * @param progeny				the progeny to be removed
	 * @return						true if successful, false otherwise
	 * @throws JSONFailureException the JSON failure exception
	 */
	public static boolean removeProgeny(int id, Progeny progeny) throws JSONFailureException {
		//TODO: code this, perhaps use progeny id to refer to the progeny
		return false;
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
	 * Updates the progeny information on the server for the specified progeny.
	 * 
	 * @param id					the id of the progeny to be updated
	 * @param progeny				the updated progeny to be sent to server
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static void updateProgeny(int id, Progeny progeny) throws JSONFailureException {
		//TODO:  server call to update
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
	
	//			**		 NOTE			**		 //
	/**
	 * This is a dummy method that emulates how getFriends will work
	 * I'm including it in lieu of just creating a string array in the StatsMenu2 file
	 *
	 * @return 						an array of friends
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<String> getFriendsTest() throws JSONFailureException 
	{
		ArrayList<String> friendList = new ArrayList<String>();
		
		friendList.add("James Anderson;http://jbaron6.cs2212.ca/img/profilePictures/1.jpg");
		friendList.add("Yaqzan Ali;http://jbaron6.cs2212.ca/img/profilePictures/2.jpg");
		friendList.add("Chuhann Frank;http://jbaron6.cs2212.ca/img/profilePictures/3.jpg");
		friendList.add("Taylor Joseph;http://jbaron6.cs2212.ca/img/profilePictures/4.jpg");
		friendList.add("James Baron;http://jbaron6.cs2212.ca/img/profilePictures/5.jpg");
		
		
		// TODO: make server call and parse list
		
		return friendList;
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
}
