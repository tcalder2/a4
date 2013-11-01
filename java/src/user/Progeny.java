package user;

import org.json.simple.JSONObject;

import json.Json;
import json.Json.JSONFailureException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Progeny {

	private String firstName;
	private String age;
	private String id;
	private boolean deleted;
	
	public static Progeny addProgeny(String first_name, String age) throws JSONFailureException
	{
		Progeny progeny = new Progeny();
		
		Json json = new Json();
		JSONObject json_user = (JSONObject)json.sendRequest("https://jbaron6.cs2212.ca/addchild?first_name=" + first_name + "&age=" + age);
		
		json_user.get("");
		
		return progeny;
	}
	
	public void deleteProgeny(Progeny progeny)
	{
		
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
