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
	
	private boolean firstLogin;
	
	private boolean testMode;
	
	private ArrayList<Progeny> progenyList;
	
	private ArrayList<Level> levels;
	
	public User(int id, String firstName, String lastName, boolean firstLogin, boolean testMode, ArrayList<Progeny> progeny, ArrayList<Level> levels) {
		
	}
	
	public User(int id, String firstName, String lastName) {
		this(id, firstName, lastName, true, false, new ArrayList<Progeny>(), new ArrayList<Level>());
	}
	
	public void setID(int id) throws IllegalArgumentException {
		validateID(id);
		this.id = id;
	}
	
	public void validateID(int id) throws IllegalArgumentException {
		
	}
	
	public void setFirstName(String firstName) throws IllegalArgumentException {
		validateFirstName(firstName);
		this.firstName = firstName;
	}
	
	public void validateFirstName(String firstName) throws IllegalArgumentException {
		
	}
	
	public void setLastName(String lastName) throws IllegalArgumentException {
		validateLastName(lastName);
		this.lastName = lastName;
	}
	
	public void validateLastName(String lastName) throws IllegalArgumentException {
		
	}
	
	/**
	 * Gets whether this is the first login attempt or not.
	 * 
	 * @return true if this is the first login, false otherwise.
	 */
	public boolean isFirstLogin() {
		return firstLogin;
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
	public void setTestMode(boolean testMode) throws IllegalArgumentException {
		this.testMode = testMode;
	}
	
	/**
	 * Gets an array of progeny.
	 *
	 * @return 						an array of progeny
	 */
	public ArrayList<Progeny> getProgeny() {			
		return progenyList;
	}
	
	/**
	 * Adds a progeny.
	 *
	 * @param  progeny				the progeny to add
	 * @return 						the newly added progeny
	 */
	public static Progeny addProgeny(String firstName, Date birthdate) throws IllegalArgumentException {
		return new Progeny(0, firstName, birthdate);
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
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}

	/**
	 * Gets the FaceBook ID of the current user.
	 *
	 * @return the FaceBook ID
	 */
	public int getID() {
		return id;
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
	
	/**
	 * 
	 * @param id					the id of the progeny to be updated
	 * @param progeny				the updated progeny to be sent to server
	 */
	public void updateProgeny(Progeny progeny) {
		progenyList.set(findProgeny(progeny), progeny);
	}
}
