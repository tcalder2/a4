package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Json {

	public class JSONFailureException extends Exception {

		private ArrayList<String> messages;

		public JSONFailureException(ArrayList<String> messages) {
			super("JSON failure message occurred.");
			this.messages = messages;
		}

		public ArrayList<String> getMessages() {
			return messages;
		}

	}

	public JSONObject sendRequest(String url) throws JSONFailureException {
		String json_data = "";
		URLConnection yc = null;
		BufferedReader in = null;

		
		try {
			// Read in the data
			yc = new URL(url).openConnection();
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			json_data = in.readLine();

		} catch (IOException e) {
			// Custom exception message
			throw new JSONFailureException(new ArrayList<String>() {
				{
					add("Could not connect to server");
				}
			});

		}

		// JSON parse the data
		JSONObject data = (JSONObject) JSONValue.parse(json_data);

		if (data == null)
			throw new JSONFailureException(new ArrayList<String>() {
				{
					add("Could not parse response");
				}
			});

		if (!(boolean) data.get("success"))
			throw new JSONFailureException(getMessages(data));

		return data;
	}

	public static ArrayList<String> getMessages(JSONObject json) {
		ArrayList<String> messages = new ArrayList<String>();

		if (json == null)
			return messages;

		JSONObject json_messages = (JSONObject) json.get("messages");

		if (json_messages != null) {
			Iterator<?> keys = json_messages.keySet().iterator();

			while (keys.hasNext()) {
				String key = (String) keys.next();

				messages.add((String) json_messages.get(key));
			}

		}

		String message = (String) json.get("message");

		if (message != null)
			messages.add(message);

		return messages;

	}

}
