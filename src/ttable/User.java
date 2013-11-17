package ttable;

import java.util.ArrayList;
import java.util.Date;

/**
 * The class User.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class User {

	/** The user's FaceBook ID. */
	private int id;

	/** The user's first name. */
	private String firstName;
	
	/** The user's last name. */
	private String lastName;
	
	/** Whether or not this is the user's first login. */
	private boolean firstLogin;
	
	/** Status of test mode activation. */
	private boolean testMode;
	
	/** The array of progeny. */
	private ArrayList<Progeny> progenyList;
	
	/** The array of levels. */
	private ArrayList<Level> levels;
	
	public User(int id, String firstName, String lastName, boolean firstLogin, boolean testMode,
			ArrayList<Progeny> progenyList, ArrayList<Level> levels) {
		setID(id);
		setFirstName(firstName);
		setLastName(lastName);
		setTestMode(testMode);
		setProgenyList(progenyList);
		setLevels(levels);
		setFirstLogin(firstLogin);
	}
	
	public User(int id, String firstName, String lastName) {
		this(id, firstName, lastName, true, false, new ArrayList<Progeny>(), new ArrayList<Level>());
		for (int i = 0; i < 12; i++) {
			levels.add(new Level(i));
		}
	}
	
	/**
	 * Gets the FaceBook ID of the current user.
	 *
	 * @return the FaceBook ID
	 */
	public int getID() {
		return id;
	}
	
	public void setID(int id) throws IllegalArgumentException {
		validateID(id);
		this.id = id;
	}
	
	private void validateID(int id) throws IllegalArgumentException {
		if (id < 0) {
			throw new IllegalArgumentException("Child's ID must be a positive number.");
		}
	}
	
	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) throws IllegalArgumentException {
		validateFirstName(firstName);
		this.firstName = firstName;
	}
	
	private void validateFirstName(String firstName) throws IllegalArgumentException {
		//TODO
	}
	
	/**
	 * Gets the last name of the current user.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) throws IllegalArgumentException {
		validateLastName(lastName);
		this.lastName = lastName;
	}
	
	private void validateLastName(String lastName) throws IllegalArgumentException {
		//TODO
	}
	
	/**
	 * Gets whether this is the first login attempt or not.
	 * 
	 * @return true if this is the first login, false otherwise.
	 */
	public boolean isFirstLogin() {
		return firstLogin;
	}
	
	public void setFirstLogin(boolean firstLogin) throws IllegalArgumentException {
		validateFirstLogin(firstLogin);
		this.firstLogin = firstLogin;
	}
	
	public void validateFirstLogin(boolean firstLogin) throws IllegalArgumentException {
		if (progenyList.size() > 0 && firstLogin == true) {
			throw new IllegalArgumentException("First Login set as true is proven invalid by existence of other data.");
		}
	}
	
	/**
	 * Gets whether the test mode is activated or not.
	 * 
	 * @return true if test mode is activated, false otherwise.
	 */
	public boolean isTestMode() {
		return testMode;
	}
	
	/**
	 * Set whether test mode is on or not.
	 * 
	 * @param testMode				the test mode boolean containing whether test mode is activated
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode; //This boolean can not be invalid
	}
	
	/**
	 * Gets an array of progeny.
	 *
	 * @return 						an array of progeny
	 */
	public ArrayList<Progeny> getProgenyList() {			
		return progenyList;
	}
	
	public void setProgenyList(ArrayList<Progeny> progenyList) throws IllegalArgumentException {
		validateProgenyList(progenyList);
		this.progenyList = progenyList;
	}
	
	public void validateProgenyList(ArrayList<Progeny> progenyList) throws IllegalArgumentException {
		//TODO
	}
	
	/**
	 * Adds a progeny.
	 *
	 * @param  progeny				the progeny to add
	 * @return 						the newly added progeny
	 */
	public Progeny addProgeny(String firstName, Date birthdate) throws IllegalArgumentException {
		return new Progeny(0, firstName, birthdate);
	}
	
	public int findProgeny(Progeny progeny) {
		int id = progeny.getID();
		for (int i = 0; i < progenyList.size(); i++) {
			if (progenyList.get(i).getID() == id) {
				return i;
			}
		}
		return (-1);
	}
	
	/**
	 * Removes the specified progeny.
	 * 
	 * @param progeny				the progeny to be removed
	 * @return						true if successful, false otherwise
	 */
	public Progeny removeProgeny(Progeny progeny) throws IndexOutOfBoundsException {
		int index = findProgeny(progeny);
		Progeny result = progenyList.get(index);
		progenyList.remove(index);
		return result;
	}
	
	/**
	 * 
	 * @param id					the id of the progeny to be updated
	 * @param progeny				the updated progeny to be sent to server
	 */
	public void updateProgeny(Progeny progeny) {
		progenyList.set(findProgeny(progeny), progeny);
	}
	
	/**
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}
	
	public void setLevels(ArrayList<Level> levels) throws IllegalArgumentException {
		validateLevels(levels);
		this.levels = levels;
	}
	
	public void validateLevels(ArrayList<Level> levels) throws IllegalArgumentException {
		//TODO
	}
}
