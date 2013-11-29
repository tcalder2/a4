package service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import json.JSONFailureException;

import org.junit.Test;

import ttable.Friend;

public class FriendServiceTest {

	@Test
	public void testGetFriends() {
		
		ArrayList<Friend> friends = new ArrayList<Friend>();
		try {
			friends = FriendService.getFriends();
		} catch (JSONFailureException e) {
			System.out.println(e.getMessages().get(0));
		}
	}

}
