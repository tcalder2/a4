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

			if (sorted_progenies[0].getLevelProgenys().get(level - 1).getLevelHighScore() == 0) continue;
			
			if (sorted_progenies[0] == null) {
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[0].getLevelProgenys().get(level - 1)
					.getLevelHighScore() < progeny.getLevelProgenys()
					.get(level - 1).getLevelHighScore()) {
				sorted_progenies[1] = sorted_progenies[0];
				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[1] == null) {
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[1].getLevelProgenys().get(level - 1)
					.getLevelHighScore() < progeny.getLevelProgenys()
					.get(level - 1).getLevelHighScore()) {
				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[2] == null) {
				sorted_progenies[2] = progeny;
				continue;
			}

			if (sorted_progenies[2].getLevelProgenys().get(level - 1)
					.getLevelHighScore() < progeny.getLevelProgenys()
					.get(level - 1).getLevelHighScore()) {
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
					.parse("{\"success\":true,\"friends\":[{\"first_name\":\"Taylor\",\"last_name\":\"Joseph\",\"fb_id\":\"89907980\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Anderson\",\"fb_id\":\"507432116\",\"progenies\":[]},{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430727\",\"progenies\":[{\"first_name\":\"Gertrude\",\"birth_date\":\"2013-01-01\",\"id\":174,\"time_allowed\":50,\"level\":12,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":435,\"attempts\":26,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":27,\"level\":1},{\"id\":436,\"attempts\":3,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":19,\"level\":2},{\"id\":437,\"attempts\":2,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":16,\"level\":3},{\"id\":438,\"attempts\":1,\"final_mistakes\":1,\"high_score\":0,\"final_completion_time\":2,\"level\":4},{\"id\":439,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":3,\"level\":5},{\"id\":440,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":4,\"level\":6},{\"id\":441,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":4,\"level\":7},{\"id\":442,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":443,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":444,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":3,\"level\":10},{\"id\":445,\"attempts\":1,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":5,\"level\":11},{\"id\":446,\"attempts\":2,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":44,\"level\":12}]},{\"first_name\":\"Popeye\",\"birth_date\":\"2013-01-01\",\"id\":175,\"time_allowed\":40,\"level\":1,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":447,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":1},{\"id\":448,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2},{\"id\":449,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":450,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4},{\"id\":451,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":452,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":453,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":454,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":455,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":456,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":457,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":458,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Sam\",\"birth_date\":\"2007-03-04\",\"id\":263,\"time_allowed\":30,\"level\":1,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":1503,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":1},{\"id\":1504,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2},{\"id\":1505,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":1506,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4},{\"id\":1507,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":1508,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":1509,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":1510,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":1511,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":1512,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":1513,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":1514,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Rob\",\"birth_date\":\"2013-01-01\",\"id\":265,\"time_allowed\":30,\"level\":1,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":1527,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":1},{\"id\":1528,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":2},{\"id\":1529,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":3},{\"id\":1530,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":4},{\"id\":1531,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":5},{\"id\":1532,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":6},{\"id\":1533,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":7},{\"id\":1534,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":8},{\"id\":1535,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":9},{\"id\":1536,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":10},{\"id\":1537,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":11},{\"id\":1538,\"attempts\":0,\"final_mistakes\":0,\"high_score\":0,\"final_completion_time\":0,\"level\":12}]}]},{\"first_name\":\"Yaqzan\",\"last_name\":\"Ali\",\"fb_id\":\"671305884\",\"progenies\":[]},{\"first_name\":\"Chuhan\",\"last_name\":\"Qin\",\"fb_id\":\"1046793757\",\"progenies\":[]}]}"))
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
