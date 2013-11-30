package service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import settings.Settings;
import ttable.Friend;
import ttable.Progeny;
import json.JSONFailureException;
import json.Json;

public class FriendService {

	public static HashMap<Progeny, Friend> getProgenyFriendDictionary(ArrayList<Friend> friends)
	{
		HashMap<Progeny, Friend> progeny_friend_dictoinary = new HashMap<Progeny,Friend>();

		for(Friend friend : friends)
		{
			for(Progeny progeny : friend.getProgenies())
			{
				progeny_friend_dictoinary.put(progeny, friend);
			}
		}
		
		return progeny_friend_dictoinary;
	}
	
	public static ArrayList<Friend> getFriends() throws JSONFailureException
	{
		Json json = new Json();
		JSONArray friends_data = null; 
				
		if (!Settings.getFbTest())
			friends_data = (JSONArray)json.sendRequest("https://jbaron6.cs2212.ca/getfriends").get("friends");
		else
			friends_data = (JSONArray)((JSONObject)JSONValue.parse("{\"success\":true,\"friends\":[{\"first_name\":\"Taylor\",\"last_name\":\"Joseph\",\"fb_id\":\"89907980\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Anderson\",\"fb_id\":\"507432116\",\"progenies\":[]},{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430727\",\"progenies\":[{\"first_name\":\"Gertrude\",\"birth_date\":\"2013-01-01\",\"id\":174,\"time_allowed\":40,\"level_progenys\":[{\"id\":435,\"attempts\":5,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":26,\"level\":1},{\"id\":436,\"attempts\":1,\"final_mistakes\":1,\"high_score\":0,\"final_completion_time\":30,\"level\":2},{\"id\":437,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":438,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4},{\"id\":439,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":440,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":441,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":442,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":443,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":444,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":445,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":446,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Popeye\",\"birth_date\":\"2013-01-01\",\"id\":175,\"time_allowed\":40,\"level_progenys\":[{\"id\":447,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":1},{\"id\":448,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2},{\"id\":449,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":450,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4},{\"id\":451,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":452,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":453,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":454,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":455,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":456,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":457,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":458,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Wally\",\"birth_date\":\"1982-05-19\",\"id\":189,\"time_allowed\":3000,\"level_progenys\":[{\"id\":615,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":1},{\"id\":616,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2},{\"id\":617,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":618,\"attempts\":1,\"final_mistakes\":5,\"high_score\":21,\"final_completion_time\":84,\"level\":4},{\"id\":619,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":620,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":621,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":622,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":623,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":624,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":625,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":626,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]}]},{\"first_name\":\"Yaqzan\",\"last_name\":\"Ali\",\"fb_id\":\"671305884\",\"progenies\":[]},{\"first_name\":\"Chuhan\",\"last_name\":\"Qin\",\"fb_id\":\"1046793757\",\"progenies\":[]}]}")).get("friends");
		
		
		
		ArrayList<Friend> friends = new ArrayList<Friend>();
		Iterator<?> friendsIt = friends_data.iterator();
		
		while(friendsIt.hasNext())
		{
			Friend friend = new Friend();
			
			JSONObject friend_object = (JSONObject)friendsIt.next();
			
			friend.setFirstName((String)friend_object.get("first_name"));
			friend.setLastName((String)friend_object.get("last_name"));
			friend.setFbId((String)friend_object.get("fb_id"));
			
			JSONArray progenies_data = (JSONArray)friend_object.get("progenies");
			ArrayList<Progeny> progenies = new ArrayList<Progeny>();
			Iterator<?> progeniesIt = progenies_data.iterator();
			
			while(progeniesIt.hasNext())
			{
				Progeny progeny = new Progeny();
				ProgenyService.fillProgeny(progeny, (JSONObject)progeniesIt.next());
				progenies.add(progeny);
			}
			
			friend.setProgenies(progenies);

			friends.add(friend);
		}

		return friends;
	}
	
}
