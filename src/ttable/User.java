package ttable;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import service.LevelService;
import service.ProgenyService;
import json.JSONFailureException;
import json.Json;

// TODO: Auto-generated Javadoc
/**
 * The class User.
 * 
 * @author James Anderson
 * @version 2.1
 */
public final class User {
	
	/** The current Drill Game skin **/
	public static int drillSkin = 0;
	
	/** URL to the current drill game menu background **/
	public static int backgroundMenuCode = 20;

	/** URL to the current drill game background **/
	public static int backgroundCode = 0;
	
	/** The user's Facebook ID. */
	private String fbID;

	/** The user's first name. */
	private String firstName;
	
	/** The user's last name. */
	private String lastName;
	
	/** The instance. */
	private static User instance = null;
	
	/** The array of progeny. */
	private ArrayList<Progeny> progenyList;
	
	/** The array of levels. */
	private ArrayList<Level> levels;
	
	
	/**
	 * Instantiates a new user.
	 */
	private User() {
		// Exists to defeat foreign instantiation
	}

	/** 
	 * Returns a code indicating the background image currently used
	 * 
	 * @return	the background image code
	 */
	public static int getBackground() {
		return backgroundCode;
	}

	/**
	 * Returns a code indicating the background image currently used for the menus
	 * 
	 * @return	the background image code
	 */
	public static int getBackgroundMenu() {
		return backgroundMenuCode;
	}
	
	/**
	 * Gets the current skin for the Drill mode
	 * 
	 * @return	an int between 0 and 2 (inclusive), representing one of
	 * 			the three skins
	 */
	public static int getDrillSkin() {
		return drillSkin;
	}

	// ** NOTE ** //
	/**
	 * This is a dummy method that emulates how getFriends will work I'm
	 * including it in lieu of just creating a string array in the StatsMenu
	 * file.
	 * 
	 * @return an array of friends
	 * @throws JSONFailureException	the jSON failure exception
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
	 * @return the user's Facebook ID
	 */
	public String getFbId() {
		return fbID;
	}
	
	/**
	 * Gets the user's Facebook id
	 * 
	 * @return the FB id
	 */
	public String getFbID() {
		return fbID;
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
	 * Gets the single instance of User.
	 * 
	 * @return single instance of User
	 */
	public static User getInstance() throws JSONFailureException {
		if (instance == null) {
			Json json = new Json();

			JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");

			JSONObject userObj = (JSONObject) jsonObj.get("user");

			instance = new User();
			instance.setFbId((String) userObj.get("fb_id"));
			instance.setFirstName((String) userObj.get("first_name"));
			instance.setLastName((String) userObj.get("last_name"));
			instance.setProgenyList(ProgenyService.getProgenies());
			
			instance.setLevels(LevelService.getLevels());
		}
		return instance;
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
	 * Gets the specified level.
	 * 
	 * @param number	the level number to get.
	 * @return			the specified level.
	 */
	public Level getLevel(int number) {
		if (number > 0 && number <= levels.size()) {
			return levels.get(number - 1);
		}
		return null;
	}

	/**
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 */
	public ArrayList<Level> getLevels() {
		return levels;
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
	 * Sets the current background image
	 * 
	 * @param code	a code indicating the background image
	 */
	public static void setBackground(int code) {
		User.backgroundCode = code;
	}

	/**
	 * Sets the current background image for the menus
	 * 
	 * @param code	a code indicating the background image
	 */
	public static void setBackgroundMenu(int code) {
		User.backgroundMenuCode = code;
	}

	/**
	 * Sets the skin used for the Drill mode
	 * 
	 * @param	an int between 0 and 2 (inclusive), representing one of
	 * 			the three skins
	 */
	public static void setDrillSkin(int drillSkin) {
		User.drillSkin = drillSkin;
	}

	/**
	 * Sets the id.
	 * 
	 * @param fbID		the user's Facebook ID
	 */
	private void setFbId(String fbID) {
		this.fbID = fbID;
	}

	/**
	 * Set's the user's Facebook ID
	 * @param fbID	the new FB id
	 */
	public void setFbID(String fbID) {
		this.fbID = fbID;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

		/**
	 * Sets an instance of the User class
	 * 
	 * @param instance	the current instance of the User
	 */
	public static void setInstance(User instance) {
		User.instance = instance;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the array of levels.
	 * 
	 * @param levels	the array of levels
	 */
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
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
	 * Method updates the theme for the drill game
	 * 
	 * @param theme	the theme - 0 = default, 1 = airplanes, 2 = castles
	 */
	public static void updateTheme(int theme) {
		
		if (theme == 0) {
			 backgroundMenuCode = 20;
			 backgroundCode = 0;
			 drillSkin = 0;
		}
		else if (theme == 1) {
			backgroundCode = 21;
			backgroundMenuCode = 21;
			drillSkin = 1;
		}
		else if (theme == 2) {
			 backgroundCode = 22;
			 backgroundMenuCode = 22;
			 drillSkin = 2;
		}
		
	}
	
}