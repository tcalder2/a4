package ttable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The class Progeny.
 * 
 * @author James Anderson
 * @version 2.0
 */
public class Progeny {

	/** 
	 * The child's ID on the server.
	 */
	private int id;
	
	/**
	 * The child's first name.
	 */
	private String firstName;

	/**
	 * The child's date of birth.
	 */
	private Date birthdate;

	/** 
	 * Array of child's top five final game scores.
	 */
	private int[] highScores;
	
	/** 
	 * The amont of time the child is allowed to complete drill levels.
	 */
	private int timeAllowed;
	
	/** 
	 * Array of progeny's level data.
	 */
	private ArrayList<LevelProgeny> levels;

	/**
	 * Constructor requiring all variables be passed as arguments.  If any arguments are invalid,
	 * an IllegalArgumentException is thrown.
	 * 
	 * @param firstName				the child's first name.
	 * @param birthdate				the child's date of birth.
	 * @param id					the ID used to identify the child on the server.
	 */
	public Progeny(String firstName, int id, Date birthdate, int[] highScores, int timeAllowed, 
	ArrayList<LevelProgeny> levels) throws IllegalArgumentException {
		setID(id);
		setFirstName(firstName);
		setDateOfBirth(birthdate);
		setHighScores(highScores);
		setTimeAllowed(timeAllowed);
		setLevels(levels);
	}
	
	public Progeny(int id, String firstName, Date birthdate) {
		setID(id);
		setFirstName(firstName);
		setDateOfBirth(birthdate);
		setHighScores(new int[]{0,0,0,0,0});
		setTimeAllowed(30);
		ArrayList<LevelProgeny> levels = new ArrayList<LevelProgeny>();
		levels.add(new LevelProgeny(1));
		setLevels(levels);
	}

	/**
	 * Gets the child's ID on the server.
	 * 
	 * @return the child's ID on the server.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Sets the child's ID value used for identifying child object on the server.  If the ID is
	 * negative, an IllegalArgumentException is thrown.
	 * 
	 * @param id						the child's ID on the server.
	 * @throws IllegalArgumentException	the exception thrown if the ID is negative.
	 */
	public void setID(int id) throws IllegalArgumentException {
		validateID(id);
		this.id = id;
	}
	
	/**
	 * Validates the child's ID value used for identifying child object on the server.  If the ID is
	 * negative, an IllegalArgumentException is thrown.
	 * 
	 * @param id						the child's ID on the server.
	 * @throws IllegalArgumentException	the exception thrown if the ID is negative.
	 */
	public void validateID(int id) throws IllegalArgumentException {
		if (id < 0) {  //TODO: adjust range to what is actually accepted by the server (if different)
			throw new IllegalArgumentException("ID must be a positive value.");
		}
	}
	
	/**
	 * Gets the child's first name.
	 *
	 * @return the child's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the child's first name. If the name contains illegal characters or is longer than
	 * 20 characters, an IllegalArgumentException is thrown.
	 * 
	 * @param firstName					the child's first name.
	 * @throws IllegalArgumentException	the exception thrown if the childs name is too long or
	 * 									contains illegal characters.
	 */
	private void setFirstName(String firstName) throws IllegalArgumentException {
		validateFirstName(firstName);
		this.firstName = firstName;
	}
	
	/**
	 * Validates the child's first name. If the name contains illegal characters or is longer
	 * than 20 characters, an IllegalArgumentException is thrown.
	 * 
	 * @param firstName					the child's first name.
	 * @throws IllegalArgumentException	the exception thrown if the childs name is longer than
	 * 									20 characters or contains illegal characters.
	 */
	private void validateFirstName(String firstName) throws IllegalArgumentException {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("First Name cannot be null or empty.");
		}
		else if (firstName.length() > 20) {
			throw new IllegalArgumentException("First Name must be 20 characters or less.");
		}
		StringCharacterIterator iterator = new StringCharacterIterator(firstName);
		char c = iterator.current();
		while (c != StringCharacterIterator.DONE) {
			//TODO: add checks for any characters that would be illegal for a JSON string (if any)
			c = iterator.next();
		}
	}
	
	/**
	 * Gets a defensive copy of the child's date of birth.
	 * 
	 * @return a defensive copy of the child's date of birth.
	 */
	public Date getDateOfBirth() {
		return new Date(birthdate.getTime());
	}
	
	/**
	 * Sets the child's date of birth to a defensive copy. If the child's date of birth is in the
	 * future, or before 1 January 1980, an IllegalArgumentException is thrown.
	 *
	 * @param birthdate					the new date of birth.
	 * @thows IllegalArgumentException	the exception thrown if the date of birth is in the future
	 * 									or before 1 January 1980.	
	 */
	public void setDateOfBirth(Date birthdate) throws IllegalArgumentException {
		validateDateOfBirth(birthdate);
		this.birthdate = new Date(birthdate.getTime());
	}
	
	/**
	 * Validates the child's date of birth. If the child's date of birth is in the future, or
	 * before 1 January 1980, an IllegalArgumentException is thrown.
	 *
	 * @param birthdate					the new date of birth.
	 * @thows IllegalArgumentException	the exception thrown if the date of birth is in the future
	 * 									or before 1 January 1980.	
	 */
	public void validateDateOfBirth(Date birthdate) throws IllegalArgumentException {
		if (birthdate.getTime() > new Date().getTime()) {
			throw new IllegalArgumentException("Date of Birth cannot be in the future.");
		}
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (birthdate.getTime() < f.parse("01/01/1980").getTime()) {
				throw new IllegalArgumentException("Date of Birth can not be before 1 January 1980.");
			}
		} catch (ParseException e) {
			// NULL BODY
		}
	}
	
	/**
	 * Gets the child's current age.
	 *
	 * @return the child's current age.
	 */
	public int getAge() {
		GregorianCalendar calPresent = new GregorianCalendar();
		calPresent.setTime(new Date());
		GregorianCalendar calBirth = new GregorianCalendar();
		calBirth.setTime(birthdate);

		int age = calPresent.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR);

		if (calBirth.get(Calendar.MONTH) < calPresent.get(Calendar.MONTH)) {
			age--; 
		}
		else if (calBirth.get(Calendar.MONTH) == calPresent.get(Calendar.MONTH)) {
			if (calBirth.get(Calendar.DAY_OF_MONTH) < calPresent.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		}
		return age;
	}
	
	public int[] getHighScores() {
		return highScores;
	}
	
	public void setHighScores(int[] highScores) {
		
	}
	
	public void validateHighScores(int [] highScores) {
		
	}
	
	/**
	 * Updates the final game high scores with new score (if it is actually a high score, else no change)
	 * 
	 * @param score			the new score achieved
	 * @return				true if score is new high score, false otherwise
	 */
	public boolean updateHighScores(int score) {
		int[] tmp = new int[5];
		int i = 0;
		for (int j = 0; j < 5; j++) {
			if (score < highScores[j] && i < 5) {
				tmp[i] = highScores[j];
				i++;
			}
			else if (i < 5) {
				tmp[i] = score;
				score = -1;
				i++;
				if (i < 5) {
					tmp[i] = highScores[j];
					i++;
				}
			}
			else {
				break;
			}
		}
		highScores = tmp;
		if (score < 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Gets the amount of time the child is allowed to complete drill levels.
	 * 
	 * @return the amount of time the child is allowed to complete drill levels.
	 */
	public int getTimeAllowed() {
		return timeAllowed;
	}

	/**
	 * Set the time allowed per level.
	 *
	 * @param timeAllowed 				the time allowed per level
	 * @throws IllegalArgumentException	the exception thrown if the 
	 */
	public void setTimeAllowed(int timeAllowed) throws IllegalArgumentException {
		validateTimeAllowed(timeAllowed);
		this.timeAllowed = timeAllowed;
	}
	
	private void validateTimeAllowed(int timeAllowed) throws IllegalArgumentException {
		
	}

	/**
	 * Gets the level progeny.
	 *
	 * @return 		the level progeny
	 */
	public ArrayList<LevelProgeny> getLevels() {
		return levels;
	}
	
	public void setLevels(ArrayList<LevelProgeny> levels) {
		
	}
	
	public void validateLevels(ArrayList<LevelProgeny> levels) {
		
	}
	
	public LevelProgeny getLevel(int level) {
		return levels.get(level);
	}

	/**
	 * Gets the current level number.
	 * 
	 * @return		the current level
	 */
	public int getLevelNumber() {
		return (levels.size() + 1);
	}

	/**
	 * Adds a new level.
	 * 
	 * @param level		the level
	 */
	public void addLevel(LevelProgeny level) {
		levels.add(level);
	}
	
	/**
	 * Changes the current level, removing all levels above current level.
	 *
	 * @param level		the level
	 */
	public void changeLevel(int level) {
		while (true) {
			try {
			levels.remove(level);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
	}
}

