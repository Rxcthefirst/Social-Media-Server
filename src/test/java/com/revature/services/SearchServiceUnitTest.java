package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.NotUsersFoundException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
class SearchServiceUnitTest {

	@Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    SearchService searchService;
	
	@Test
	public void getProfilesReturns() {
		long length = 1;
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		when(profileRepository.count()).thenReturn(length);
		when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
		assertNotNull(searchService.getProfiles(length));
	}

	@Test
	public void getSpecificProfilesReturns() {
		User user = new User(0, "", "", "test", "test");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			when(userRepository.findAllMatchesByFirstAndLastNames(user.getLastName(), user.getFirstName())).thenReturn(Optional.of(list));
			assertNotNull(searchService.getSpecificProfiles("test", "test"));
		}catch(NotUsersFoundException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void getSpecificProfilesNull() {
		try {
			assertNull(searchService.getSpecificProfiles(null, null));
		}catch(NotUsersFoundException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void getSpecificProfilesFirstName() {
		User user = new User(0, "", "", "test", "test");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			when(userRepository.findAllMatchesByEitherFirstOrLastName(user.getFirstName())).thenReturn(Optional.of(list));
			assertNotNull(searchService.getSpecificProfiles("test", null));
		}catch(NotUsersFoundException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void getSpecificProfilesLastName() {
		User user = new User(0, "", "", "test", "test");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			when(userRepository.findAllMatchesByEitherFirstOrLastName(user.getLastName())).thenReturn(Optional.of(list));
			assertNotNull(searchService.getSpecificProfiles(null, "test"));
		}catch(NotUsersFoundException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void getSpecificProfilesNotFound() {
		User user = new User(0, "", "", "test", "test");
		Profile profile = new Profile(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, user);
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			when(userRepository.findAllMatchesByFirstAndLastNames(user.getLastName(), user.getFirstName())).thenReturn(Optional.empty());
			searchService.getSpecificProfiles("test", "test");
			fail();
		}catch(NotUsersFoundException e) {
			e.printStackTrace();
            return;
		}
	}
	
}
