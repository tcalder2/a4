package service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.JSONFailureException;
import json.Json;
import ttable.Level;
import ttable.Progeny;

public class LevelService {

	/**
	 * Gets an array of the levels (ie. global level settings).
	 * 
	 * @return 						an array of the levels 
	 * @throws JSONFailureException	the JSON failure exception
	 */
	public static ArrayList<Level> getLevels() throws JSONFailureException {
		
		ArrayList<Level> levels = new ArrayList<Level>();
		
		Json json = new Json();
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getlevels");
		
		JSONArray levelsArray = (JSONArray) jsonObj.get("levels");
		
		Iterator<?> levelsIt = levelsArray.iterator();
		
		while(levelsIt.hasNext())
		{
			JSONObject levelObj = (JSONObject)levelsIt.next();
			
			Level level = new Level();
			fillLevel(level, levelObj);
			levels.add(level);
		}
		
		return levels;
	}
	
	public static Level getLevel(int level_number) throws JSONFailureException
	{
		Json json = new Json();
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getlevel?level=" + level_number);
		
		Level level = new Level();
		fillLevel(level, (JSONObject)jsonObj.get("level"));
		
		return level;
	}
	
	public static void changeMistakesAllowed(Level level, int mistakes_allowed) throws JSONFailureException
	{
		Json json = new Json();
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/changelevelmistakesallowed?level=" + level.getLevelNumber() + "&mistakes_allowed=" + mistakes_allowed);
		
		fillLevel(level, (JSONObject)jsonObj.get("level"));
	}
	
	public static void fillLevel(Level level, JSONObject level_data)
	{
		level.setMistakesAllowed(Integer.parseInt(level_data.get("mistakes_allowed").toString()));
		level.setLevelNumber(Integer.parseInt(level_data.get("level").toString()));
		level.setId(level_data.get("id").toString());
	}
	
	
}
