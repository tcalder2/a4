package service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.JSONFailureException;
import json.Json;
import ttable.Progeny;

/**
 * The Class ProgenyService.
 */
public class ProgenyService {
	
	/**
	 * Gets an array of progenies.
	 *
	 * @return 						an array of progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getProgenies() throws JSONFailureException 
	{
		ArrayList<Progeny> progenies = new ArrayList<Progeny>();
		
		Json json = new Json();
		JSONObject jsonObj = json.sendRequest("https://jbaron6.cs2212.ca/getprogenies");
		
		
		JSONArray progeniesArray = (JSONArray) jsonObj.get("progenies");
		
		Iterator<?> progeniesIt = progeniesArray.iterator();
		
		while(progeniesIt.hasNext())
		{
			JSONObject progenyObj = (JSONObject)progeniesIt.next();
			
			Progeny progeny = new Progeny();

			try {
				progeny.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse((String)progenyObj.get("birth_date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			progeny.setFirstName((String)progenyObj.get("first_name"));
			progenies.add(progeny);
		}
		
		return progenies;
	}
	
	/**
	 * Adds the progeny.
	 *
	 * @param name the name
	 * @param birthDate the birth date
	 * @return the progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static Progeny addProgeny(String name, Date birthDate) throws JSONFailureException
	{
		Progeny progeny = new Progeny();
		
		SimpleDateFormat birth_date_format = new SimpleDateFormat("yyyy-MM-dd");
		
		Json json = new Json();
		JSONObject progeny_obj = json.sendRequest("https://jbaron6.cs2212.ca/addprogeny?first_name=" + name + "&birth_date=" + birth_date_format.format(birthDate));
		
		try {
			progeny.setBirthdate(birth_date_format.parse((String)progeny_obj.get("birth_date")));
		} catch (ParseException e) {
		}
		
		progeny.setFirstName((String)progeny_obj.get("first_name"));
		
		return progeny;
	}

	/**
	 * Removes the progeny.
	 *
	 * @param progeny the progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static void removeProgeny(Progeny progeny) throws JSONFailureException {
				
	}
}
