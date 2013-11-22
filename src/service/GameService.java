package service;

import json.JSONFailureException;
import ttable.Progeny;

/**
 * The services related to server calls for setting and getting ProgenyLevel class information.
 * This information pertains to child specific drill and game, data and settings.
 * 
 * @author James Anderson
 * @version 1.0
 */
public class GameService {

	/**
	 * Sets the progeny's level back to an early one, removing all level data for levels higher
	 * than the new level number.
	 * 
	 * @param progeny				the progeny whose level is to be set.
	 * @param level					the new level number to set the progeny to.
	 * @throws JSONFailureException	the JSON failure exception.
	 */
	public static void setLevel(Progeny progeny, int level) throws JSONFailureException {
		//TODO: add server request
	}
	
	
}
