package service;

import static org.junit.Assert.*;
import json.JSONFailureException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSetAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testPostMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPasswordWithQ() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMistakesAllowed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSecurityQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthenticate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFriends() {
		fail("Not yet implemented");
	}

	@Test
	public void testResetPassword() {
		fail("Not yet implemented");
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
