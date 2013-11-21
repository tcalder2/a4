package service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import json.JSONFailureException;
import json.Json;
import ttable.Progeny;

/**
 * The Class ProgenyService.
 * 
 * @author James Baron
 * @author James Anderson
 */
public class ProgenyService {

	static SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	
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
			fillProgeny(progeny, progenyObj);
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
	public static Progeny addProgeny(String name, Date birthDate, int timeAllowed) throws JSONFailureException
	{
		Json json = new Json();
		JSONObject progeny_obj = json.sendRequest("https://jbaron6.cs2212.ca/addprogeny?first_name=" + name + 				"&birth_date=" + birthDateFormat.format(birthDate) +				"&time_allowed=" + timeAllowed
				);
	
		Progeny progeny = new Progeny();

		fillProgeny(progeny, (JSONObject)progeny_obj.get("progeny"));
		
		return progeny;
	}
	
	public static void fillProgeny(Progeny progeny, JSONObject progeny_data)
	{
		progeny.setFirstName((String)progeny_data.get("first_name"));
		
		try {
			progeny.setBirthdate(birthDateFormat.parse((String)progeny_data.get("birth_date")));
		} catch (ParseException e) {}
		
		progeny.setTimeAllowed(Integer.parseInt(progeny_data.get("time_allowed").toString()));
		
		progeny.setId(progeny_data.get("id").toString());
	}
	
	public static void changeBirthDate(Progeny progeny, Date birthDate) throws JSONFailureException
	{
		SimpleDateFormat birth_date_format = new SimpleDateFormat("yyyy-MM-dd");
		
		Json json = new Json();
		JSONObject progeny_data = json.sendRequest("https://jbaron6.cs2212.ca/changeprogenybirthdate?progeny_id=" + progeny.getId() + "&birth_date=" + birth_date_format.format(birthDate));
	
		fillProgeny(progeny, (JSONObject)progeny_data.get("progeny"));
	}
	
	public static void changeTimeAllowed(Progeny progeny, int timeAllowed) throws JSONFailureException
	{
		
		Json json = new Json();
		JSONObject progeny_data = json.sendRequest("https://jbaron6.cs2212.ca/changeprogenytimeallowed?progeny_id=" + progeny.getId() + "&time_allowed=" + timeAllowed);
	
		fillProgeny(progeny, (JSONObject)progeny_data.get("progeny"));
				
	}

	
	/**
	 * Calculates the child's current age.
	 *
	 * @param birthDate		the child's date of birth
	 * @return 				the child's current age.
	 */
	public static int getAge(Date birthDate) {
		GregorianCalendar calPresent = new GregorianCalendar();
		calPresent.setTime(new Date());
		GregorianCalendar calBirth = new GregorianCalendar();
		calBirth.setTime(birthDate);

		int age = calPresent.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR);

		if (calBirth.get(Calendar.MONTH) > calPresent.get(Calendar.MONTH)) {
			age--; 
		}
		else if (calBirth.get(Calendar.MONTH) == calPresent.get(Calendar.MONTH)) {
			if (calBirth.get(Calendar.DAY_OF_MONTH) > calPresent.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		}
		return age;
	}

	/**
	 * Removes the progeny.
	 *
	 * @param progeny the progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static void removeProgeny(Progeny progeny) throws JSONFailureException {
		Json json = new Json();
		json.sendRequest("https://jbaron6.cs2212.ca/removeprogeny?progeny_id=" + progeny.getId());
	}

	public static SimpleDateFormat getBirthDateFormat() {
		return birthDateFormat;
	}

	public static void setBirthDateFormat(SimpleDateFormat birthDateFormat) {
		ProgenyService.birthDateFormat = birthDateFormat;
	}
	
	
}
