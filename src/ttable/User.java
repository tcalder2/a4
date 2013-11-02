package ttable;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import json.Json;
import json.Json.JSONFailureException;

public class User {

	private String firstName = "";
	private String lastName = "";
	private String fbId ="";
	
	public static User getUser() throws JSONFailureException
	{
		User user = new User();
		
		Json json = new Json();
		
		JSONObject json_obj = json.sendRequest("https://jbaron6.cs2212.ca/getuser");
		
		JSONObject user_obj = (JSONObject)json_obj.get("user");
		
		user.setFbId((String)user_obj.get("fb_id"));
		user.setFirstName((String)user_obj.get("first_name"));
		user.setLastName((String)user_obj.get("last_name"));
		
		return user;
	}
	
	public static boolean setAnswer(String answer, String password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setanswer?answer=" + answer + "&password=" + password);
		
		return true;
	}
	
	public static boolean resetPassword(String answer, String new_password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/resetpassword?answer=" + answer + "&new_password=" + new_password);
		
		return true;
	}

	public static boolean setQuestion(String question, String password) throws JSONFailureException
	{
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/setquestion?question=" + question + "&password=" + password);		
		
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
	
	public boolean setPassword(String old_password, String new_password) throws JSONFailureException
	{
		Json json = new Json();
		
		json.sendRequest("https://jbaron6.cs2212.ca/setpassword?new_password=" + new_password + "&old_password=" + old_password);
		
		return true;
	}
	
	public boolean authenticate(String password) throws JSONFailureException
	{
		Json json = new Json();
		
		//Will throw error if success fail.  That error will have messages.
		json.sendRequest("https://jbaron6.cs2212.ca/authenticate?password=" + password);
		
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
}
