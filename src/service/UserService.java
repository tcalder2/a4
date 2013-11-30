package service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ttable.Progeny;
import json.JSONFailureException;
import json.Json;
/**
 * 
 * @author James Baron
 * @author James Anderson
 * @version 1.1
 */
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
	public static boolean postMessage(String message) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/postmessage?message=" + message);
		
		return true;
	}
	
	/**
	 * Sets the user's password.
	 *
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static boolean setPassword(String oldPassword, String newPassword) throws JSONFailureException
	{
		Json json = new Json();
		
		json.sendRequest("https://jbaron6.cs2212.ca/setpassword?new_password=" + newPassword + "&old_password=" + oldPassword);
		
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
	public static boolean setQuestion(String questionNumber, String password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setquestion?question=" + questionNumber + "&password=" + password);		
		
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
	 *  Resets the user's password using the security question for validation.  If the
	 *  security question is input incorrectly, the password is automatically reset to the
	 *  default, cs2212.
	 *  
	 * @param answer				the answer input for the security question.
	 * @param newpassword			the new password to be changed to.
	 * @return						true if successful, false otherwise.
	 * @throws JSONFailureException	the JSON exception thrown if the password change is unsuccessful.
	 */
	public static boolean resetPassword(String answer, String newPassword) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/resetpassword?answer=" + answer + "&new_password=" + newPassword);
		
		return true;
	}
	
	/**
	 * Gets the security question number chosen by the current user.  The index will be a number
	 * between 0 and 2 inclusive.
	 * 
	 * @return						the index of the user's chosen security question.
	 * @throws JSONFailureException	the exception thrown if there is an issue retrieving the index.
	 */
	public static int getQuestionIndex() throws JSONFailureException
	{
		Json json = new Json();
		JSONObject result =  json.sendRequest("https://jbaron6.cs2212.ca/getquestionindex");
		
		return result.get("question_index") == null ? -1 : Integer.parseInt((String)result.get("question_index"));
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

	// ** NOTE ** //
		/**
		 * This is a dummy method that emulates how getFriends will work I'm
		 * including it in lieu of just creating a string array in the StatsMenu2
		 * file.
		 * 
		 * @return an array of friends
		 * @throws JSONFailureException
		 *             the jSON failure exception
		 */
		public static ArrayList<String> getFriendsTest()
				throws JSONFailureException {
			ArrayList<String> friendList = new ArrayList<String>();

			friendList
					.add("James Anderson;http://jbaron6.cs2212.ca/img/profilePictures/1.jpg");
			friendList
					.add("Yaqzan Ali;http://jbaron6.cs2212.ca/img/profilePictures/2.jpg");
			friendList
					.add("Chuhann Frank;http://jbaron6.cs2212.ca/img/profilePictures/3.jpg");
			friendList
					.add("Taylor Joseph;http://jbaron6.cs2212.ca/img/profilePictures/4.jpg");
			friendList
					.add("James Baron;http://jbaron6.cs2212.ca/img/profilePictures/5.jpg");

			// TODO: make server call and parse list

			return friendList;
		}
	}	

