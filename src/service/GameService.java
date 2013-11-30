package service;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import json.JSONFailureException;
import json.Json;
import ttable.LevelProgeny;
import ttable.Progeny;

/**
 * The services related to server calls for setting and getting ProgenyLevel class information.
 * This information pertains to child specific drill and game, data and settings.
 * 
 * @author James Anderson
 * @author James Baron
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
	
	public static void fillLevelProgeny(LevelProgeny levelProgeny, JSONObject level_progeny_data)
	{
		levelProgeny.setAttempts(Integer.parseInt(level_progeny_data.get("attempts").toString()));
		levelProgeny.setMistakes(Integer.parseInt(level_progeny_data.get("final_mistakes").toString()));
		levelProgeny.setLevelNumber(Integer.parseInt(level_progeny_data.get("level").toString()));
		levelProgeny.setCompletionTime(Integer.parseInt(level_progeny_data.get("final_completion_time").toString()));
	}

	public static void saveFinalGame(Progeny progeny, int score) throws JSONFailureException
	{
		Json json = new Json();
		JSONObject progeny_data = (JSONObject) json.sendRequest("https://jbaron6.cs2212.ca/savefinalgame?progeny_id=" + progeny.getId() + "&score=" + score ).get("progeny");

		ProgenyService.fillProgeny(progeny, progeny_data);
	}
	
	public static void saveGame(Progeny progeny, int levelNumber, int mistakes, int score, int time) throws JSONFailureException
	{
		Json json = new Json();
		JSONObject progeny_data = (JSONObject) json.sendRequest("https://jbaron6.cs2212.ca/savegame?progeny_id=" + 
				progeny.getId() + "&level=" + levelNumber + "&mistakes=" + mistakes + "&score=" + score + "&time=" + time).get("progeny");
		
		ProgenyService.fillProgeny(progeny, progeny_data);
	}
	
	/**
	 * Method returns the LevelProgeny for a given Progeny
	 * 
	 * @param progeny
	 * @throws JSONFailureException
	 */
	public static ArrayList<LevelProgeny> getLevels(Progeny progeny) throws JSONFailureException {
		//TODO: replace with actual server request
		return new ArrayList<LevelProgeny>();
	}
}
