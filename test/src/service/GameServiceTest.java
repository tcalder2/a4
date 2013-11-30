package service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import json.JSONFailureException;

import org.junit.Test;

import ttable.Progeny;

public class GameServiceTest {

	@Test
	public void testSetLevel() {
		fail("Not yet implemented");
	}

	@Test
	public void saveFinalGame() {
		Date birthDate = null;
		int fiveMinutes = 3000;

		try {
			birthDate = ProgenyService.getBirthDateFormat().parse("1982-05-19");
		} catch (ParseException e1) {
		}

		String name = "Derricka";
		Progeny progeny = null;

		try {
			progeny = ProgenyService.addProgeny(name, birthDate, fiveMinutes);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}

		try {
			GameService.saveFinalGame(progeny, 4);
		} catch (JSONFailureException e1) {
			fail(e1.getMessages().get(0));
		}

		assertEquals(4, progeny.getFinalGameHighScore());

		try {
			ProgenyService.removeProgeny(progeny);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}
	}

	@Test
	public void testSaveGame() {

		Date birthDate = null;
		int fiveMinutes = 3000;

		try {
			birthDate = ProgenyService.getBirthDateFormat().parse("1982-05-19");
		} catch (ParseException e1) {
		}

		String name = "Wally";
		Progeny progeny = null;

		try {
			progeny = ProgenyService.addProgeny(name, birthDate, fiveMinutes);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}

		try {
			GameService.saveGame(progeny, 4, 5, 21, 84);
		} catch (JSONFailureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ProgenyService.removeProgeny(progeny);
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}

	}

}
