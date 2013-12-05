package ttable;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Friend.
 */
public class Friend {

	/** The fb_id. */
	private String fbId;
	
	/** The first_name. */
	private String firstName;
	
	/** The last_name. */
	private String lastName;
	
	/** The progenies. */
	private ArrayList<Progeny> progenies;

	/**
	 * Gets the Facebook id of the friend
	 *
	 * @return the fb id
	 */
	public String getFbId() {
		return fbId;
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
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the progenies (children) of a friend.
	 *
	 * @return the progenies
	 */
	public ArrayList<Progeny> getProgenies() {
		return progenies;
	}

	/**
	 * Sets the Facebook id of the friend
	 *
	 * @param fbId the new Facebook id
	 */
	public void setFbId(String fbId) {
		this.fbId = fbId;
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
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the progenies (children) of a friend.
	 *
	 * @param progenies the new progenies
	 */
	public void setProgenies(ArrayList<Progeny> progenies) {
		this.progenies = progenies;
	}
	
}
