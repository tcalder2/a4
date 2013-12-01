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

			if (progeny.getLevelProgenys().get(level - 1).getCompletionTime() == 0)
				continue;

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

		for (Progeny progeny : sorted_progenies) {
			if (progeny != null)
				hashMap.put(progeny, sourceHashMap.get(progeny));
		}

		return hashMap;
	}

	public static LinkedHashMap<Progeny, Friend> getHighTopThreeProgeniesPerParentForFinal(
			ArrayList<Friend> friends) {

		LinkedHashMap<Progeny, Friend> sourceHashMap = getProgenyFriendHashMap(friends);
		LinkedHashMap<Progeny, Friend> hashMap = new LinkedHashMap<Progeny, Friend>();

		Progeny sorted_progenies[] = new Progeny[3];

		for (Progeny progeny : sourceHashMap.keySet()) {

			if (progeny.getFinalGameHighScore() == 0)
				continue;

			if (sorted_progenies[0] == null) {
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[0].getFinalGameHighScore() < progeny
					.getFinalGameHighScore()) {

				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[1] = sorted_progenies[0];
				sorted_progenies[0] = progeny;
				continue;
			}

			if (sorted_progenies[1] == null) {
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[1].getFinalGameHighScore() < progeny
					.getFinalGameHighScore()) {
				sorted_progenies[2] = sorted_progenies[1];
				sorted_progenies[1] = progeny;
				continue;
			}

			if (sorted_progenies[2] == null) {
				sorted_progenies[2] = progeny;
				continue;
			}

			if (sorted_progenies[2].getFinalGameHighScore() < progeny
					.getFinalGameHighScore()) {
				sorted_progenies[2] = progeny;
				continue;
			}

		}

		for (Progeny progeny : sorted_progenies) {
			if (progeny != null)
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
					.parse("{\"success\":true,\"friends\":[{\"first_name\":\"Taylor\",\"last_name\":\"Joseph\",\"fb_id\":\"89907980\",\"progenies\":[]},{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430727\",\"progenies\":[{\"first_name\":\"Rob\",\"birth_date\":\"1980-03-05\",\"id\":265,\"time_allowed\":40,\"level\":12,\"final_game_high_score\":1250,\"level_progenys\":[{\"id\":1527,\"attempts\":4,\"final_mistakes\":0,\"final_completion_time\":26,\"level\":1},{\"id\":1528,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":12,\"level\":2},{\"id\":1529,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":3},{\"id\":1530,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":4},{\"id\":1531,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":5},{\"id\":1532,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":6},{\"id\":1533,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":7},{\"id\":1534,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":8},{\"id\":1535,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":23,\"level\":9},{\"id\":1536,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":10},{\"id\":1537,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":11},{\"id\":1538,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":12}]},{\"first_name\":\"Bill\",\"birth_date\":\"2013-01-01\",\"id\":356,\"time_allowed\":30,\"level\":1,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2595,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":1},{\"id\":2596,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":2},{\"id\":2597,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2598,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2599,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2600,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2601,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2602,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2603,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2604,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2605,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2606,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]}]},{\"first_name\":\"Yaqzan\",\"last_name\":\"Ali\",\"fb_id\":\"671305884\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Baron\",\"fb_id\":\"100001201459747\",\"progenies\":[]}]}"))
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

	

	public static ArrayList<Friend> getAllFbUsers() throws JSONFailureException {
		Json json = new Json();
		JSONArray friends_data = null;

		if (!Settings.getFbTest())
			friends_data = (JSONArray) json.sendRequest(
					"https://jbaron6.cs2212.ca/getallfbusers").get("fb_users");
		else
			friends_data = (JSONArray) ((JSONObject) JSONValue
					.parse("{\"success\":true,\"fb_users\":[{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430727\",\"progenies\":[{\"first_name\":\"Rob\",\"birth_date\":\"1980-03-05\",\"id\":265,\"time_allowed\":40,\"level\":12,\"final_game_high_score\":1250,\"level_progenys\":[{\"id\":1527,\"attempts\":10,\"final_mistakes\":0,\"final_completion_time\":37,\"level\":1},{\"id\":1528,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":12,\"level\":2},{\"id\":1529,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":3},{\"id\":1530,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":4},{\"id\":1531,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":5},{\"id\":1532,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":6},{\"id\":1533,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":7},{\"id\":1534,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":8},{\"id\":1535,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":23,\"level\":9},{\"id\":1536,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":24,\"level\":10},{\"id\":1537,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":11},{\"id\":1538,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":25,\"level\":12}]},{\"first_name\":\"Bill\",\"birth_date\":\"2013-01-01\",\"id\":356,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2595,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":8,\"level\":1},{\"id\":2596,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":2},{\"id\":2597,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2598,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2599,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2600,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2601,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2602,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2603,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2604,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2605,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2606,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Dude\",\"birth_date\":\"2010-01-01\",\"id\":366,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2715,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":27,\"level\":1},{\"id\":2716,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":2},{\"id\":2717,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2718,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2719,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2720,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2721,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2722,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2723,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2724,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2725,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2726,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Man\",\"birth_date\":\"2010-01-01\",\"id\":367,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2727,\"attempts\":4,\"final_mistakes\":0,\"final_completion_time\":27,\"level\":1},{\"id\":2728,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":2},{\"id\":2729,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2730,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2731,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2732,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2733,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2734,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2735,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2736,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2737,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2738,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]},{\"first_name\":\"Guy\",\"birth_date\":\"2010-01-01\",\"id\":368,\"time_allowed\":30,\"level\":2,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2739,\"attempts\":2,\"final_mistakes\":0,\"final_completion_time\":27,\"level\":1},{\"id\":2740,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":2},{\"id\":2741,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2742,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2743,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2744,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2745,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2746,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2747,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2748,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2749,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2750,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]}]},{\"first_name\":\"Taylor\",\"last_name\":\"Joseph\",\"fb_id\":\"89907980\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Baron\",\"fb_id\":\"100001201459747\",\"progenies\":[{\"first_name\":\"Link\",\"birth_date\":\"2002-03-05\",\"id\":363,\"time_allowed\":30,\"level\":3,\"final_game_high_score\":0,\"level_progenys\":[{\"id\":2679,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":16,\"level\":1},{\"id\":2680,\"attempts\":1,\"final_mistakes\":0,\"final_completion_time\":9,\"level\":2},{\"id\":2681,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":3},{\"id\":2682,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":4},{\"id\":2683,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":5},{\"id\":2684,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":6},{\"id\":2685,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":7},{\"id\":2686,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":8},{\"id\":2687,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":9},{\"id\":2688,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":10},{\"id\":2689,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":11},{\"id\":2690,\"attempts\":0,\"final_mistakes\":0,\"final_completion_time\":0,\"level\":12}]}]},{\"first_name\":\"Jordan\",\"last_name\":\"Dyk\",\"fb_id\":\"677545564\",\"progenies\":[]},{\"first_name\":\"Zaid\",\"last_name\":\"Albirawi\",\"fb_id\":\"1035731302\",\"progenies\":[]},{\"first_name\":\"Justin\",\"last_name\":\"Doyle\",\"fb_id\":\"501680823\",\"progenies\":[]},{\"first_name\":\"Yaqzan\",\"last_name\":\"Ali\",\"fb_id\":\"671305884\",\"progenies\":[]},{\"first_name\":\"Crystal\",\"last_name\":\"Keenan\",\"fb_id\":\"508430725\",\"progenies\":[]},{\"first_name\":\"James\",\"last_name\":\"Anderson\",\"fb_id\":\"507432116\",\"progenies\":[]}]}"))
					.get("fb_users");
		
		
		

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
