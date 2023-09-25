package com.revature.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.dtos.ChangePasswordDTO;
import com.revature.dtos.GeneralInformationDTO;
import com.revature.dtos.ImageUrlDTO;
import com.revature.dtos.ProfileEducationDTO;
import com.revature.dtos.ProfileLocationDTO;
import com.revature.dtos.ProfileMaritalStatusDTO;
import com.revature.dtos.ProfileWorkDTO;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.NoNameException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.ProfileService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerUnitTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    ProfileController profileController;
    
    private MockMvc mockMvc;

    private MockHttpSession session;

    private User user;
    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(profileController);
        this.mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        user = new User(0,"test user","test user","test user","test user");

        profile = new Profile(0, "test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile","test profile",user);
        session = spy(MockHttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    public void getOwnProfileReturnsSessionUserProfileFromService(){
        try {
            when(profileService.getProfileByUser(user)).thenReturn(profile);
            this.mockMvc.perform(get("/profile")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getOwnProfileRequestBadifUserNotFound(){
        try {
            when(profileService.getProfileByUser(user)).thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getOwnProfileCallsRegisterProfileIfProfileNotFound(){
        try {
            when(profileService.getProfileByUser(user)).thenThrow(new ProfileNotFoundException(""));
            when(profileService.registerProfile(user)).thenReturn(profile);
            this.mockMvc.perform(get("/profile")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
            verify(profileService).registerProfile(user);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileReturnsProfileFromService() {
        int x = 10_000;
        try {
            when(profileService.getProfile(x)).thenReturn(profile);
            this.mockMvc.perform(get("/profile/"+x)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileRequestBadIfProfileNotFound() {
        int x = 10_000;
        try {
            when(profileService.getProfile(x)).thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile/"+x)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void ChangePasswordCallsServiceChangePassword() {
        String json =  "{\"oldPassword\":\"\",\"newPassword\":\"\"}";
        try {
            when(profileService.changePassword(any(ChangePasswordDTO.class), any(User.class))).thenReturn(user);
            this.mockMvc.perform(post("/profile/change-password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isOk());
            verify(profileService).changePassword(any(ChangePasswordDTO.class), any(User.class));
        } catch (Exception e) {
            fail(e);
        }
        
    }

    @Test
    public void ChangePasswordBadRequestOnWrongPassword() {
        String json =  "{\"oldPassword\":\"\",\"newPassword\":\"\"}";
        try {
            when(profileService.changePassword(any(ChangePasswordDTO.class), any(User.class)))
                .thenThrow(new WrongPasswordException("test exception"));
            this.mockMvc.perform(post("/profile/change-password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void ChangePasswordBadRequestOnEmailTaken() {
        String json =  "{\"oldPassword\":\"\",\"newPassword\":\"\"}";
        try {
            when(profileService.changePassword(any(ChangePasswordDTO.class), any(User.class)))
                .thenThrow(new EmailReservedException("test exception"));
            this.mockMvc.perform(post("/profile/change-password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getGeneralInfoReturnsDataFromProfileService() {
        
        GeneralInformationDTO gi = new GeneralInformationDTO(profile);
        
        try {
            when(profileService.getGeneralInformation(user)).thenReturn(gi);
            this.mockMvc.perform(get("/profile/general-information")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getGeneralInfoRequestBadIfProfileNotFound() {
        try {
            when(profileService.getGeneralInformation(user)).thenThrow(new ProfileNotFoundException(("test exception")));
            this.mockMvc.perform(get("/profile/general-information")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void postGeneralInfoUpdatesInfo() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"firstName\":\"test gi\",");
        jsonBuilder.append("\"lastName\":\"\",");
        jsonBuilder.append("\"gender\":\"\",");
        jsonBuilder.append("\"email\":\"\",");
        jsonBuilder.append("\"phoneNumber\":\"\",");
        jsonBuilder.append("\"dob\":\"\"}");
        String json = jsonBuilder.toString();
        
        try {
            when(profileService.updateGeneralInformation(any(GeneralInformationDTO.class), any(User.class)))
            .thenReturn(profile);
            this.mockMvc.perform(post("/profile/general-information")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void postGeneralInfoBadRequestOnUserNotFound() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"firstName\":\"test gi\",");
        jsonBuilder.append("\"lastName\":\"\",");
        jsonBuilder.append("\"gender\":\"\",");
        jsonBuilder.append("\"email\":\"\",");
        jsonBuilder.append("\"phoneNumber\":\"\",");
        jsonBuilder.append("\"dob\":\"\"}");
        String json = jsonBuilder.toString();
        
        try {
            when(profileService.updateGeneralInformation(any(GeneralInformationDTO.class), any(User.class)))
            .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/general-information")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void postGeneralInfoBadRequestOnProfileNotFound() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"firstName\":\"test gi\",");
        jsonBuilder.append("\"lastName\":\"\",");
        jsonBuilder.append("\"gender\":\"\",");
        jsonBuilder.append("\"email\":\"\",");
        jsonBuilder.append("\"phoneNumber\":\"\",");
        jsonBuilder.append("\"dob\":\"\"}");
        String json = jsonBuilder.toString();
        
        try {
            when(profileService.updateGeneralInformation(any(GeneralInformationDTO.class), any(User.class)))
            .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/general-information")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void postGeneralInfoBadRequestOnNoName() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"firstName\":\"test gi\",");
        jsonBuilder.append("\"lastName\":\"\",");
        jsonBuilder.append("\"gender\":\"\",");
        jsonBuilder.append("\"email\":\"\",");
        jsonBuilder.append("\"phoneNumber\":\"\",");
        jsonBuilder.append("\"dob\":\"\"}");
        String json = jsonBuilder.toString();
        
        try {
            when(profileService.updateGeneralInformation(any(GeneralInformationDTO.class), any(User.class)))
            .thenThrow(new NoNameException("test exception"));
            this.mockMvc.perform(post("/profile/general-information")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void postGeneralInfoBadRequestOnEmailReserved() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"firstName\":\"test gi\",");
        jsonBuilder.append("\"lastName\":\"\",");
        jsonBuilder.append("\"gender\":\"\",");
        jsonBuilder.append("\"email\":\"\",");
        jsonBuilder.append("\"phoneNumber\":\"\",");
        jsonBuilder.append("\"dob\":\"\"}");
        String json = jsonBuilder.toString();
        
        try {
            when(profileService.updateGeneralInformation(any(GeneralInformationDTO.class), any(User.class)))
            .thenThrow(new EmailReservedException("test exception"));
            this.mockMvc.perform(post("/profile/general-information")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileLocationReturnsFromService(){
        ProfileLocationDTO pl = new ProfileLocationDTO(profile);
        try {
            when(profileService.getProfileLocation( any(User.class)))
                .thenReturn(pl);
            this.mockMvc.perform(get("/profile/profile-location")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void getProfileLocationBadRequestOnProfileNotFound(){
        ProfileLocationDTO pl = new ProfileLocationDTO(profile);
        try {
            when(profileService.getProfileLocation( any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile/profile-location")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileLocationReturnsFromService(){
        try {
            when(profileService.updateProfileLocation(any(ProfileLocationDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileLocationBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileLocation(any(ProfileLocationDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileLocationBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileLocation(any(ProfileLocationDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileEducationReturnsFromService(){
        ProfileEducationDTO pl = new ProfileEducationDTO(profile);
        try {
            when(profileService.getProfileEducation( any(User.class)))
                .thenReturn(pl);
            this.mockMvc.perform(get("/profile/profile-education")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void getProfileEducationBadRequestOnProfileNotFound(){
        ProfileEducationDTO pl = new ProfileEducationDTO(profile);
        try {
            when(profileService.getProfileEducation( any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile/profile-education")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileEducationReturnsFromService(){
        try {
            when(profileService.updateProfileEducation(any(ProfileEducationDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-education")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileEducationBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileEducation(any(ProfileEducationDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-education")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileEducationBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileEducation(any(ProfileEducationDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-education")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileWorkReturnsFromService(){
        ProfileWorkDTO pl = new ProfileWorkDTO(profile);
        try {
            when(profileService.getProfileWork( any(User.class)))
                .thenReturn(pl);
            this.mockMvc.perform(get("/profile/profile-work")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void getProfileWorkBadRequestOnProfileNotFound(){
        ProfileWorkDTO pl = new ProfileWorkDTO(profile);
        try {
            when(profileService.getProfileWork( any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile/profile-work")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileWorkReturnsFromService(){
        try {
            when(profileService.updateProfileWork(any(ProfileWorkDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-work")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileWorkBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileWork(any(ProfileWorkDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-work")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileWorkBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileWork(any(ProfileWorkDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-work")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getProfileMaritalStatusReturnsFromService(){
        ProfileMaritalStatusDTO pl = new ProfileMaritalStatusDTO(profile);
        try {
            when(profileService.getProfileMaritalStatus( any(User.class)))
                .thenReturn(pl);
            this.mockMvc.perform(get("/profile/profile-marital-status")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void getProfileMaritalStatusBadRequestOnProfileNotFound(){
        ProfileMaritalStatusDTO pl = new ProfileMaritalStatusDTO(profile);
        try {
            when(profileService.getProfileMaritalStatus( any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(get("/profile/profile-marital-status")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileMaritalStatusReturnsFromService(){
        try {
            when(profileService.updateProfileMaritalStatus(any(ProfileMaritalStatusDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-marital-status")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileMaritalStatusBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileMaritalStatus(any(ProfileMaritalStatusDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-marital-status")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileMaritalStatusBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileMaritalStatus(any(ProfileMaritalStatusDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-marital-status")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileBackgroundReturnsFromService(){
        try {
            when(profileService.updateProfileBackground(any(ImageUrlDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-background")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileBackgroundBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileBackground(any(ImageUrlDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-background")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileBackgroundBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileBackground(any(ImageUrlDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-background")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileAvatarReturnsFromService(){
        try {
            when(profileService.updateProfileAvatar(any(ImageUrlDTO.class), any(User.class)))
                .thenReturn(profile);
            this.mockMvc.perform(post("/profile/profile-avatar")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test profile")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileAvatarBadRequestOnProfileNotFound(){
        try {
            when(profileService.updateProfileAvatar(any(ImageUrlDTO.class), any(User.class)))
                .thenThrow(new ProfileNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-avatar")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    public void postProfileAvatarBadRequestOnUserNotFound(){
        try {
            when(profileService.updateProfileAvatar(any(ImageUrlDTO.class), any(User.class)))
                .thenThrow(new UserNotFoundException("test exception"));
            this.mockMvc.perform(post("/profile/profile-avatar")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{}")
                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("test exception")));
        } catch (Exception e) {
            fail(e);
        }
    }
}
