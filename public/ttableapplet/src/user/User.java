package user;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import json.Json;
import json.Json.JSONFailureException;

public class User {


	public static boolean setQuestion(String question) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setquestion?question=" + question);		
		
		return true;
	}
	
	public static ArrayList<String> getQuestions() throws JSONFailureException
	{
		ArrayList<String> questions = new ArrayList<String>();
		
		Json json = new Json();
		JSONObject json_obj = json.sendRequest("https://jbaron6.cs2212.ca/getquestions");
		
		
		JSONArray questions_array = (JSONArray) json_obj.get("questions");
		
		Iterator<?> questions_it = questions_array.iterator();
		
		while(questions_it.hasNext())
			questions.add((String)questions_it.next());
		
		return questions;
	}
	
	public boolean setPassword(String password) throws JSONFailureException
	{
		Json json = new Json();
		
		json.sendRequest("https://jbaron6.cs2212.ca/setpassword?password=" + password);
		
		return true;
	}
	
	public boolean authenticate(String password) throws JSONFailureException
	{
		Json json = new Json();
		
		//Will throw error if success fail.  That error will have messages.
		json.sendRequest("https://jbaron6.cs2212.ca/authenticate?password=" + password);
		
		return true;
	}
	
	
}
