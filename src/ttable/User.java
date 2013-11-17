package ttable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import json.JSONFailureException;
import json.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The class User.
 * 
 * @author James Baron
 * @author James Anderson
 * @author Chuhan Qin
 * @version 2.0
 */
public class User {

	/** The user's FaceBook id. */
	private String fbId ="";

	/** The user's first name. */
	private String firstName = "";
	
	/** The user's last name. */
	private String lastName = "";	
	
	//			**		 NOTE			**		 //
	/**
	 * This is a dummy method that emulates how getFriends will work
	 * I'm including it in lieu of just creating a string array in the StatsMenu2 file.
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
	 * Gets the fb id.
	 *
	 * @return the fb id
	 */
	public String getFbId() {
		return fbId;
	}

	/**
	 * Sets the fb id.
	 *
	 * @param fbId the new fb id
	 */
	public void setFbId(String fbId) {
		this.fbId = fbId;
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
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
