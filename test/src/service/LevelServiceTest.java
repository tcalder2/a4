package service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import json.JSONFailureException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ttable.Level;

public class LevelServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testChangeMistakesAllowed()
	{
		Level level = null;
		
		try {
			level = LevelService.getLevel(4);
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}
		
		int mistakes_allowed = level.getMistakesAllowed();
		int new_mistakes_allowed = 700;
		
		if (mistakes_allowed == 700)
			new_mistakes_allowed = 30;
		
		try {
			LevelService.changeMistakesAllowed(level, new_mistakes_allowed);
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}

		assertEquals(new_mistakes_allowed, level.getMistakesAllowed());
		
		try {
			LevelService.changeMistakesAllowed(level, mistakes_allowed);
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}

		assertEquals(mistakes_allowed, level.getMistakesAllowed());

	}
	
	@Test
	public void testGetLevels() {
		
		ArrayList<Level> levels = null;
		
		try {
			levels = LevelService.getLevels();
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}
		
		assertEquals(12, levels.size());
		

		assertEquals(4, levels.get(3).getLevel());
		assertEquals(7, levels.get(6).getLevel());
		assertEquals(12, levels.get(11).getLevel());
	}
	
	@Test
	public void testGetLevel()
	{
		Level level4 = null;
		Level level7 = null;
		Level level12 = null;
		
		try {
			level4 = LevelService.getLevel(4);
			level7 = LevelService.getLevel(7);
			level12 = LevelService.getLevel(12);
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}
		
		assertEquals(4, level4.getLevel());
		assertEquals(7, level7.getLevel());
		assertEquals(12, level12.getLevel());
	}

}
