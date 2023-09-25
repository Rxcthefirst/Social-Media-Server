package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.dtos.SubscriptionDTO;
import com.revature.exceptions.NoSuchSubscriberException;
import com.revature.exceptions.ProfileAlreadySubscribedException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.SelfSubscriptionException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;



@ExtendWith(SpringExtension.class)
class SubscriptionServiceUnitTest {

	@Mock
	private ProfileService profileService;
	@Mock
	private ProfileRepository profileRepository;
	@Mock
	private SubscriptionDTO subscriptionDTO;
	
	@InjectMocks
	SubscriptionService subscriptionService;
	
	@Test
	public void patchSubscriptionReturns() { 
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			when(subscriptionDTO.getId()).thenReturn(1);
			subscriptionService.patchSubscription(subscriptionDTO, user);
			verify(profileRepository).save(any(Profile.class));
		}
		catch(UserNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(SelfSubscriptionException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileAlreadySubscribedException e) {
			e.printStackTrace();
            fail();
		}
	}

	@Test
	public void patchSubscriptionSelfSubscribe() { 
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, new LinkedList<Integer>(Arrays.asList(1)), null, user);
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			when(subscriptionDTO.getId()).thenReturn(0);
			subscriptionService.patchSubscription(subscriptionDTO, user);
			fail();
		}
		catch(UserNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(SelfSubscriptionException e) {
			e.printStackTrace();
            return;
		}
		catch(ProfileAlreadySubscribedException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void patchSubscriptionAlreadySubed() { 
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, new LinkedList<Integer>(Arrays.asList(1)), null, user);
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			when(subscriptionDTO.getId()).thenReturn(1);
			subscriptionService.patchSubscription(subscriptionDTO, user);
			fail();
		}
		catch(UserNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(SelfSubscriptionException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileAlreadySubscribedException e) {
			e.printStackTrace();
            return;
		}
	}
	
	@Test
	public void patchUnsubscribeReturns() { 
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, new LinkedList<Integer>(Arrays.asList(1)), null, user);
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			when(subscriptionDTO.getId()).thenReturn(1);
			subscriptionService.patchUnsubscribe(subscriptionDTO, user);
			verify(profileRepository).save(any(Profile.class));
		}
		catch(UserNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(NoSuchSubscriberException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void patchUnsubscribeNoSuchSubscriber() { 
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			when(subscriptionDTO.getId()).thenReturn(1);
			subscriptionService.patchUnsubscribe(subscriptionDTO, user);
			fail();
		}
		catch(UserNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(ProfileNotFoundException e) {
			e.printStackTrace();
            fail();
		}
		catch(NoSuchSubscriberException e) {
			e.printStackTrace();
            return;
		}
	}
	
}
