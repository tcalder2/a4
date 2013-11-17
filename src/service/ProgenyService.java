package service;

import java.sql.Date;
import java.util.ArrayList;

import json.JSONFailureException;
import ttable.Progeny;

public class ProgenyService {
	
	/**
	 * Gets an array of progeny.
	 *
	 * @return 						an array of progeny
	 * @throws JSONFailureException the jSON failure exception
	 */
	public static ArrayList<Progeny> getProgeny() throws JSONFailureException 
	{
		ArrayList<Progeny> progenyList;

		progenyList = new ArrayList<Progeny>();
		
		// TODO: make server call and parse list
		
		return progenyList;
	}
	
	public static Progeny AddProgeny(String name, Date birthdate) throws JSONFailureException
	{
		Progeny progeny = new Progeny();
		
		return progeny;
	}
}
