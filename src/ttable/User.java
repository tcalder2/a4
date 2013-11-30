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

	/** The instance. */
	private static User instance = null;
	
	/** The user's Facebook ID. */
	private String fbID;

	/** The user's first name. */
	private String firstName;
	
	/** The user's last name. */
	private String lastName;
	
	/** The array of progeny. */
	private ArrayList<Progeny> progenyList;
	
	/** The array of levels. */
	private ArrayList<Level> levels;
	
	/** The current Drill Game skin **/
	public static int drillSkin = 0;
	
	/** URL to the current drill game menu background **/
	public static String backgroundMenu = "http://jbaron6.cs2212.ca/img/level_background.png";

	/** URL to the current drill game background **/
	public static String background = "http://jbaron6.cs2212.ca/img/default_background.png";
	
	
	/**
	 * Instantiates a new user.
	 */
	private User() {
		// Exists to defeat foreign instantiation
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
	 * Method updates the theme for the drill game
	 * 
	 * @param theme	the theme - 0 = default, 1 = airplanes, 2 = castles
	 */
	public static void updateTheme(int theme) {
		
		if (theme == 0) {
			 backgroundMenu = "http://jbaron6.cs2212.ca/img/level_background.png";
			 background = "http://jbaron6.cs2212.ca/img/default_background.png";
			 drillSkin = 0;
		}
		else if (theme == 1) {
			background = "http://jbaron6.cs2212.ca/img/themes/theme_1/level_background.png";
			backgroundMenu = "http://jbaron6.cs2212.ca/img/themes/theme_1/level_background.png";
			drillSkin = 1;
		}
		else if (theme == 2) {
			 background = "http://jbaron6.cs2212.ca/img/themes/theme_2/level_background.png";
			 backgroundMenu = "http://jbaron6.cs2212.ca/img/themes/theme_2/level_background.png";
			 drillSkin = 2;
		}
		
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
	 * Sets the id.
	 * 
	 * @param fbID		the user's Facebook ID
	 */
	private void setFbId(String fbID) {
		this.fbID = fbID;
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
	private void setFirstName(String firstName) {
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
	private void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 */
	public ArrayList<Level> getLevels() {
		return levels;
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

	public String getFbID() {
		return fbID;
	}

	public void setFbID(String fbID) {
		this.fbID = fbID;
	}

	public static int getDrillSkin() {
		return drillSkin;
	}

	public static void setDrillSkin(int drillSkin) {
		User.drillSkin = drillSkin;
	}

	public static String getBackgroundMenu() {
		return backgroundMenu;
	}

	public static void setBackgroundMenu(String backgroundMenu) {
		User.backgroundMenu = backgroundMenu;
	}

	public static String getBackground() {
		return background;
	}

	public static void setBackground(String background) {
		User.background = background;
	}

	public static void setInstance(User instance) {
		User.instance = instance;
	}
	
	
}
