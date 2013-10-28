package user;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.json;
import json.json.JSONFailureException;

public class User {


	
	public boolean Authenticate(String password) throws JSONFailureException
	{
		json json = new json();
		
		//Will throw error if success fail.  That error will have messages.
		json.sendRequest("https://jbaron6.cs2212.ca/authenticate?password=" + password);
		
		return true;
	}
	
	
}
