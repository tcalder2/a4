package service;

import java.util.ArrayList;
import json.JSONFailureException;
import ttable.Level;

public class LevelService {

	/**
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static ArrayList<Level> getLevels() throws JSONFailureException {
		ArrayList<Level> levels = new ArrayList<Level>();  //TODO: replace line with actual server call
		return levels;
	}
	
}
