package service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import json.JSONFailureException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ttable.Progeny;

public class ProgenyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetProgenies() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProgeny() {
		
		Date birthDate = null;
		int fiveMinutes = 3000;
		
		try {
			birthDate = ProgenyService.getBirthDateFormat().parse("1982-05-19");
		} catch (ParseException e1) {
		}
		
		String name = "James";
		Progeny progeny = null;
		
		try {
			progeny = ProgenyService.addProgeny(name, birthDate, fiveMinutes);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}
	
		assertTrue(progeny.getBirthDate().equals(birthDate));
		assertEquals(name, progeny.getFirstName());
		assertEquals(fiveMinutes, progeny.getTimeAllowed());
		
		try {
			ProgenyService.removeProgeny(progeny);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}
	}
	
	@Test
	public void testChangeBirthDate() {
		Date firstBirthDate = null;
		Date secondBirthDate = null;
		int fiveMinutes = 3000;
		
		try {
			firstBirthDate = ProgenyService.getBirthDateFormat().parse("1982-05-19");
			secondBirthDate = ProgenyService.getBirthDateFormat().parse("1942-07-12");
		} catch (ParseException e1) {
		}
		
		String name = "James";
		Progeny progeny = null;
		
		try {
			progeny = ProgenyService.addProgeny(name, firstBirthDate, fiveMinutes);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}
	
		try {
			ProgenyService.changeBirthDate(progeny, secondBirthDate);
		} catch (JSONFailureException e1) {
			fail(e1.getMessages().get(0));
		}
		
		assertTrue(progeny.getBirthDate().equals(secondBirthDate));		
		
		try {
			ProgenyService.removeProgeny(progeny);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}
	}

	@Test
	public void testFillProgeny() {
		fail("Not yet implemented");
	}



	@Test
	public void testGetAge() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveProgeny() {
		fail("Not yet implemented");
	}

}
