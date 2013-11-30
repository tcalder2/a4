package service;


import json.JSONFailureException;
import org.junit.Test;


public class FriendServiceTest {

	@Test
	public void testGetFriends() {
		
		//ArrayList<Friend> friends = new ArrayList<Friend>();
		try {
			//friends = FriendService.getFriends();
			FriendService.getFriends();
		} catch (JSONFailureException e) {
			System.out.println(e.getMessages().get(0));
		}
		
		
	}

}
