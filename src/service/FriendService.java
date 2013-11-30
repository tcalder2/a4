package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import settings.Settings;
import ttable.Friend;
import ttable.Progeny;
import json.JSONFailureException;
import json.Json;

public class FriendService {

	// holy shit that's a long ass method name
	
	public static LinkedHashMap<Progeny, Friend> getHighTopThreeProgeniesPerParentByLevel(
			ArrayList<Friend> friends, int level) {
		
		LinkedHashMap<Progeny, Friend> sourceHashMap = getProgenyFriendHashMap(friends);
		LinkedHashMap<Progeny, Friend> hashMap = new LinkedHashMap<Progeny, Friend>();

		Progeny sorted_progenies[] = new Progeny[3];

		for (Progeny progeny : sourceHashMap.keySet()) {

			if (progeny.getLevelProgenys().get(level - 1).getCompletionTime() == 0) continue;
			
			if (sorted_progenies[0] == null) {
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[0].getLevelProgenys().get(level - 1)
					.getCompletionTime() > progeny.getLevelProgenys()
					.get(level - 1).getCompletionTime()) {
				
				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[1] = sorted_progenies[0];
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[1] == null) {
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[1].getLevelProgenys().get(level - 1)
					.getCompletionTime() > progeny.getLevelProgenys()
					.get(level - 1).getCompletionTime()) {
				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[2] == null) {
				sorted_progenies[2] = progeny;
				continue;
			}

			if (sorted_progenies[2].getLevelProgenys().get(level - 1)
					.getCompletionTime() > progeny.getLevelProgenys()
					.get(level - 1).getCompletionTime()) {
				sorted_progenies[2] = progeny;
				continue;
			}

		}
		
		for(Progeny progeny : sorted_progenies)
		{
			hashMap.put(progeny, sourceHashMap.get(progeny));
		}
		
		return hashMap;
	}

	public static LinkedHashMap<Progeny, Friend> getProgenyFriendHashMap(
			ArrayList<Friend> friends) {
		LinkedHashMap<Progeny, Friend> progeny_friend_dictoinary = new LinkedHashMap<Progeny, Friend>();

		for (Friend friend : friends) {
			for (Progeny progeny : friend.getProgenies()) {
				progeny_friend_dictoinary.put(progeny, friend);
			}
		}

		return progeny_friend_dictoinary;
	}

	public static ArrayList<Friend> getFriends() throws JSONFailureException {
		Json json = new Json();
		JSONArray friends_data = null;

		if (!Settings.getFbTest())
			friends_data = (JSONArray) json.sendRequest(
					"https://jbaron6.cs2212.ca/getfriends").get("friends");
		else
			friends_data = (JSONArray) ((JSONObject) JSONValue
					.parse("{\"success\":true,\"friends\":[{\"first_name\":\"Taylor\",\"last_name\":\"Joseph\",\"fb_id\":\"89907980\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Anderson\",\"fb_id\":\"507432116\",\"progenies\":[]},{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430727\",\"progenies\":[{\"first_name\":\"Rob\",\"birth_date\":\"2013-01-01\",\"id\":265,\"time_allowed\":30,\"level\":8,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":1527,\"attempts\":2,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":27,\"level\":1,\"cumulative_score\":0},{\"id\":1528,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":12,\"level\":2,\"cumulative_score\":0},{\"id\":1529,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":23,\"level\":3,\"cumulative_score\":0},{\"id\":1530,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":25,\"level\":4,\"cumulative_score\":0},{\"id\":1531,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":24,\"level\":5,\"cumulative_score\":0},{\"id\":1532,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":25,\"level\":6,\"cumulative_score\":0},{\"id\":1533,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":24,\"level\":7,\"cumulative_score\":0},{\"id\":1534,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8,\"cumulative_score\":0},{\"id\":1535,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9,\"cumulative_score\":0},{\"id\":1536,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10,\"cumulative_score\":0},{\"id\":1537,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11,\"cumulative_score\":0},{\"id\":1538,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12,\"cumulative_score\":0}]},{\"first_name\":\"Wally\",\"birth_date\":\"1982-05-19\",\"id\":311,\"time_allowed\":3000,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2079,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":12,\"level\":1,\"cumulative_score\":0},{\"id\":2080,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2,\"cumulative_score\":0},{\"id\":2081,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3,\"cumulative_score\":0},{\"id\":2082,\"attempts\":2,\"final_mistakes\":5,\"high_score\":24,\"final_completion_time\":84,\"level\":4,\"cumulative_score\":48},{\"id\":2083,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5,\"cumulative_score\":0},{\"id\":2084,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6,\"cumulative_score\":0},{\"id\":2085,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7,\"cumulative_score\":0},{\"id\":2086,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8,\"cumulative_score\":0},{\"id\":2087,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9,\"cumulative_score\":0},{\"id\":2088,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10,\"cumulative_score\":0},{\"id\":2089,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11,\"cumulative_score\":0},{\"id\":2090,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12,\"cumulative_score\":0}]},{\"first_name\":\"Slothrop\",\"birth_date\":\"2013-01-01\",\"id\":316,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2139,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":25,\"level\":1,\"cumulative_score\":0},{\"id\":2140,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2,\"cumulative_score\":0},{\"id\":2141,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3,\"cumulative_score\":0},{\"id\":2142,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4,\"cumulative_score\":0},{\"id\":2143,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5,\"cumulative_score\":0},{\"id\":2144,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6,\"cumulative_score\":0},{\"id\":2145,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7,\"cumulative_score\":0},{\"id\":2146,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8,\"cumulative_score\":0},{\"id\":2147,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9,\"cumulative_score\":0},{\"id\":2148,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10,\"cumulative_score\":0},{\"id\":2149,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11,\"cumulative_score\":0},{\"id\":2150,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12,\"cumulative_score\":0}]},{\"first_name\":\"Oedipa\",\"birth_date\":\"2013-01-01\",\"id\":317,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2151,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":24,\"level\":1,\"cumulative_score\":0},{\"id\":2152,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2,\"cumulative_score\":0},{\"id\":2153,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3,\"cumulative_score\":0},{\"id\":2154,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4,\"cumulative_score\":0},{\"id\":2155,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5,\"cumulative_score\":0},{\"id\":2156,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6,\"cumulative_score\":0},{\"id\":2157,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7,\"cumulative_score\":0},{\"id\":2158,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8,\"cumulative_score\":0},{\"id\":2159,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9,\"cumulative_score\":0},{\"id\":2160,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10,\"cumulative_score\":0},{\"id\":2161,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11,\"cumulative_score\":0},{\"id\":2162,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12,\"cumulative_score\":0}]}]},{\"first_name\":\"Yaqzan\",\"last_name\":\"Ali\",\"fb_id\":\"671305884\",\"progenies\":[]},{\"first_name\":\"Chuhan\",\"last_name\":\"Qin\",\"fb_id\":\"1046793757\",\"progenies\":[]}]}"))
					.get("friends");

		
		
		
		ArrayList<Friend> friends = new ArrayList<Friend>();
		Iterator<?> friendsIt = friends_data.iterator();

		while (friendsIt.hasNext()) {
			Friend friend = new Friend();

			JSONObject friend_object = (JSONObject) friendsIt.next();

			friend.setFirstName((String) friend_object.get("first_name"));
			friend.setLastName((String) friend_object.get("last_name"));
			friend.setFbId((String) friend_object.get("fb_id"));

			JSONArray progenies_data = (JSONArray) friend_object
					.get("progenies");
			ArrayList<Progeny> progenies = new ArrayList<Progeny>();
			Iterator<?> progeniesIt = progenies_data.iterator();

			while (progeniesIt.hasNext()) {
				Progeny progeny = new Progeny();
				ProgenyService.fillProgeny(progeny,
						(JSONObject) progeniesIt.next());
				progenies.add(progeny);
			}

			friend.setProgenies(progenies);

			friends.add(friend);
		}

		return friends;
	}

}
