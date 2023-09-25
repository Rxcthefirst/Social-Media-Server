package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.dtos.ChangePasswordDTO;
import com.revature.dtos.GeneralInformationDTO;
import com.revature.dtos.ImageUrlDTO;
import com.revature.dtos.ProfileEducationDTO;
import com.revature.dtos.ProfileLocationDTO;
import com.revature.dtos.ProfileMaritalStatusDTO;
import com.revature.dtos.ProfileWorkDTO;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.NoNameException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongIdsFormatException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
class ProfileServiceUnitTest {

	@Mock
	private ProfileRepository profileRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService;
	@Mock
	private GeneralInformationDTO generalInformationDTO;
	@Mock
	private ProfileLocationDTO profileLocationDTO;
	@Mock
	private ProfileEducationDTO profileEducationDTO;
	@Mock
	private ProfileWorkDTO profileWorkDTO;
	@Mock
	private ProfileMaritalStatusDTO profileMaritalStatusDTO;
	@Mock
	private ImageUrlDTO imageUrlDTO;
	
	@InjectMocks
	ProfileService profileService;
	
	@Test
	public void registerProfileCallsRepositorySave() {
		User user = mock(User.class);
		Profile profile = new Profile();
		profile.setOwner(user);
		when(profileRepository.save(profile)).thenReturn(profile);
        profileService.registerProfile(user);
        verify(profileRepository).save(any(Profile.class));
	}
	
	@Test
	public void getProfileIfReal() {
		int id = 0;
		Profile profile = mock(Profile.class);
		when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
		try {
			profileService.getProfile(id);
            verify(profileRepository).findById(id);
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}

	@Test
	public void getProfileThrowsIfEmpty() {
		int id = 0;
		when(profileRepository.findById(id)).thenReturn(Optional.empty());
		try {
			profileService.getProfile(id);
            fail();
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileByUserIfReal() {
		User user = new User(0,"","","","");
		Profile profile = mock(Profile.class);
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			try {
				profileService.getProfileByUser(user);
				verify(userService).findByCredentials("","");
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            fail();
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileByUserThrowsNotFoundUser() {
		User user = new User(0,"","","","");
		Profile profile = mock(Profile.class);
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			try {
				profileService.getProfileByUser(user);
				fail();
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            fail();
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileByUserThrowsNotFoundProfile() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
			try {
				profileService.getProfileByUser(user);
				fail();
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            return;
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void changePasswordWrongPassword() {
		User user = new User(0,"","","","");
		ChangePasswordDTO changePassword = mock(ChangePasswordDTO.class);
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			profileService.changePassword(changePassword, user);
			fail();
        } 
		catch (WrongPasswordException e) {
            e.printStackTrace();
            return;
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void changePasswordReturns() {
		User user = new User(0,"","","","");
		ChangePasswordDTO changePassword = mock(ChangePasswordDTO.class);
		when(changePassword.getOldPassword()).thenReturn("");
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			User result = profileService.changePassword(changePassword, user);
			assertNull(result);
        } 
		catch (WrongPasswordException e) {
            e.printStackTrace();
            fail();
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getGeneralInformationEmpty() {
		User user = new User(0,"","","","");
		when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
		try {
			profileService.getGeneralInformation(user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateGeneralInformationReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			when(generalInformationDTO.getFirstName()).thenReturn(user.getFirstName());
			when(generalInformationDTO.getLastName()).thenReturn(user.getLastName());
			when(generalInformationDTO.getEmail()).thenReturn(user.getEmail());
			Profile result = profileService.updateGeneralInformation(generalInformationDTO, user);
			assertNull(result);
        } 
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (NoNameException e) {
            e.printStackTrace();
            fail();
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateGeneralInformationNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateGeneralInformation(generalInformationDTO, user);
			fail();
        } 
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (NoNameException e) {
            e.printStackTrace();
            fail();
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateGeneralInformationProfileNotFound() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			profileService.updateGeneralInformation(generalInformationDTO, user);
			fail();
        } 
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
		catch (NoNameException e) {
            e.printStackTrace();
            fail();
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateGeneralInformationNoFirstName() {
		User user = new User(0,"","","","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			when(generalInformationDTO.getFirstName()).thenReturn(user.getFirstName());
			profileService.updateGeneralInformation(generalInformationDTO, user);
			fail();
        } 
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (NoNameException e) {
            e.printStackTrace();
            return;
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateGeneralInformationNoLastName() {
		User user = new User(0,"","","test","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			when(generalInformationDTO.getFirstName()).thenReturn(user.getFirstName());
			when(generalInformationDTO.getLastName()).thenReturn(user.getLastName());
			profileService.updateGeneralInformation(generalInformationDTO, user);
			fail();
        } 
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (NoNameException e) {
            e.printStackTrace();
            return;
        }
		catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileLocationReturns() {
		User user = new User(0,"","","test","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			ProfileLocationDTO result = profileService.getProfileLocation(user);
			assertNotNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileLocationNoProfile() {
		User user = new User(0,"","","test","");
		when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
		try {
			profileService.getProfileLocation(user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileLocationReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileLocation(profileLocationDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileLocationNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileLocation(profileLocationDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileEducationReturns() {
		User user = new User(0,"","","test","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			ProfileEducationDTO result = profileService.getProfileEducation(user);
			assertNotNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileEducationNoProfile() {
		User user = new User(0,"","","test","");
		when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
		try {
			profileService.getProfileEducation(user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileEducationReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileEducation(profileEducationDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileEducationNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileEducation(profileEducationDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileWorkReturns() {
		User user = new User(0,"","","test","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			ProfileWorkDTO result = profileService.getProfileWork(user);
			assertNotNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileWorkNoProfile() {
		User user = new User(0,"","","test","");
		when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
		try {
			profileService.getProfileWork(user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileWorkReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileWork(profileWorkDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileWorkNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileWork(profileWorkDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileMaritalStatusReturns() {
		User user = new User(0,"","","test","");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
		try {
			ProfileMaritalStatusDTO result = profileService.getProfileMaritalStatus(user);
			assertNotNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileMaritalStatusNoProfile() {
		User user = new User(0,"","","test","");
		when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
		try {
			profileService.getProfileMaritalStatus(user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileMaritalStatusReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileMaritalStatus(profileMaritalStatusDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileMaritalStatusNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileMaritalStatus(profileMaritalStatusDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileBackgroundReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileBackground(imageUrlDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileBackgroundNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileBackground(imageUrlDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updateProfileAvatarReturns() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			Profile result = profileService.updateProfileAvatar(imageUrlDTO, user);
			assertNull(result);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updateProfileAvatarNoUser() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updateProfileAvatar(imageUrlDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void updatePhotosUserNoProfile() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			profileService.updatePhotos(imageUrlDTO, user);
			verify(profileRepository).save(profile);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void updatePhotosUserNotFound() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.updatePhotos(imageUrlDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void removePhotosUserNoProfile() {
		User user = new User(0,"","test@email.com","test","test");
		Profile profile = new Profile(0, "", "", "", "", "", "", "", "", "", "", "", "", "", user);
		when(userService.findByCredentials(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
		try {
			when(profileService.getProfileByUser(user)).thenReturn(profile);
			profileService.removePhoto(imageUrlDTO, user);
			verify(profileRepository).save(profile);
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (ImageNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void removePhotosUserNotFound() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			profileService.removePhoto(imageUrlDTO, user);
			fail();
        } 
		catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
		catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
		catch (ImageNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getAllProfilesByIdsReturns() {
		String list = "0,1";
		try{
			assertNotNull(profileService.getAllProfilesByIds(list));
		} catch(WrongIdsFormatException e) {
			 e.printStackTrace();
	         fail();
		}
	}
	
	@Test
	public void getAllProfilesByIdsWrongFormat() {
		String list = "0, 1";
		try{
			assertNotNull(profileService.getAllProfilesByIds(list));
			fail();
		} catch(WrongIdsFormatException e) {
			 e.printStackTrace();
	         return;
		}
	}
	
	@Test
	public void getAllProfilesByIdsReturnsLimit() {
		String list = "0,1";
		try{
			assertNotNull(profileService.getAllProfilesByIds(list, 2));
		} catch(WrongIdsFormatException e) {
			 e.printStackTrace();
	         fail();
		}
	}
	
	@Test
	public void getAllProfilesByIdsReturnsLimitShuffled() {
		String list = "0,1";
		try{
			assertNotNull(profileService.getAllProfilesByIds(list, 2, true));
		} catch(WrongIdsFormatException e) {
			 e.printStackTrace();
	         fail();
		}
	}
	
}
