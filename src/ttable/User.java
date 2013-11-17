package ttable;

import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

import json.JSONFailureException;
import json.Json;

// TODO: Auto-generated Javadoc
/**
 * The class User.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class User {

	/** The user's FaceBook ID. */
	private String fb_id;

	/** The user's first name. */
	private String firstName;

	/** The user's last name. */
	private String lastName = "";

	/** Whether or not this is the user's first login. */
	private boolean firstLogin;

	/** The array of progeny. */
	private ArrayList<Progeny> progenyList;

	/** The birth date. */
	private Date birthDate;

	/** The instance. */
	private static User instance = null;

	/**
	 * Instantiates a new user.
	 */
	protected User() {
		// Exists only to defeat instantiation.
	}

	/**
	 * Gets the single instance of User.
	 * 
	 * @return single instance of User
	 */
	public static User getInstance() throws JSONFailureException {
		if (instance == null) {
			User user = new User();

			Json json = new Json();

			JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");

			JSONObject userObj = (JSONObject) jsonObj.get("user");

			user.setFbId(((String) userObj.get("fb_id")));
			user.setFirstName(((String) userObj.get("first_name")));
			user.setLastName(((String) userObj.get("last_name")));
		}
		return instance;
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

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getFbId() {
		return fb_id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param fb_id
	 *            the new fb id
	 */
	public void setFbId(String fb_id) {
		this.fb_id = fb_id;
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
	 * @param firstName
	 *            the new first name
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
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Checks if is first login.
	 * 
	 * @return true, if is first login
	 */
	public boolean isFirstLogin() {
		return firstLogin;
	}

	/**
	 * Sets the first login.
	 * 
	 * @param firstLogin
	 *            the new first login
	 */
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * Gets the progeny list.
	 * 
	 * @return the progeny list
	 */
	public ArrayList<Progeny> getProgenyList() {
		return progenyList;
	}

	/**
	 * Sets the progeny list.
	 * 
	 * @param progenyList
	 *            the new progeny list
	 */
	public void setProgenyList(ArrayList<Progeny> progenyList) {
		this.progenyList = progenyList;
	}

	/**
	 * Gets the birth date.
	 * 
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the birth date.
	 * 
	 * @param birthDate
	 *            the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean getFirstLogin() {
		// TODO Auto-generated method stub
		return false;
	}
}
