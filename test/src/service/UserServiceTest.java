package service;

import static org.junit.Assert.*;
import json.JSONFailureException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ttable.User;

public class UserServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testChangeSkin()
	{
		try{
			UserService.changeSkin(2);
			assertEquals(2, User.getDrillSkin());
			UserService.changeSkin(0);	
			assertEquals(0, User.getDrillSkin());
		}
		catch(JSONFailureException e)
		{
			fail(e.getMessages().get(0));
		}
		
		
	}
	
	@Test
	public void testResetPassword() {
		try {
			UserService.resetPassword("beer", "cs2212");
		} catch (JSONFailureException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetQuestionIndex() {

		int question_index = -2;
		
		try {
			question_index = UserService.getQuestionIndex();
		} catch (JSONFailureException e) {
			fail(e.getMessages().get(0));
		}

		assertTrue(question_index > -2 && question_index < 3);
	}

	@Test
	public void testGetFriendProgeny() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFriendsTest() {
		fail("Not yet implemented");
	}

}
