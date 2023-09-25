package com.revature.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.exceptions.EmailReservedException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.ProfileService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerUnitTest {

    @Mock
    private AuthService authService;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    AuthController authController;

    private MockMvc mockMvc;

    static String jsonLogin = "{\"email\":\"\",\"password\":\"\"}";
    static String jsonRegister = "{\"id\":\"0\",\"email\":\"\",\"password\":\"\",\"firstName\":\"\",\"lastName\":\"\"}";

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(authController);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    
    @Test
    public void authLoginCallsAuthServiceLogin(){
        User user = new User(0,"","","","");
        try{
        when(authService.login(anyString(),anyString())).thenReturn(user);
        this.mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(jsonLogin));
        verify(authService).login(anyString(),anyString());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authLoginSetsSessionUser(){
        User user = new User(0,"","","","");
        MockHttpSession session = spy(MockHttpSession.class);
        try{
        when(authService.login(anyString(),anyString())).thenReturn(user);
        this.mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(jsonLogin)
            .session(session));
        verify(session).setAttribute("user", user);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authLoginStatusOk(){
        User user = new User(0,"","","","");
        MockHttpSession session = new MockHttpSession();
        try{
        when(authService.login(anyString(),anyString())).thenReturn(user);
        this.mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(jsonLogin)
            .session(session))
            .andExpect(status().isOk());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void badAuthLoginStatusBad(){
        User user = new User(0,"","","","");
        MockHttpSession session = new MockHttpSession();
        try{
        when(authService.login(anyString(),anyString())).thenReturn(user);
        this.mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("bad request")
            .session(session))
            .andExpect(status().isBadRequest());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authLogoutRemovesSessionUser() {
        MockHttpSession session = spy(MockHttpSession.class);
        try {
            this.mockMvc.perform(post("/auth/logout")
            .session(session));
            verify(session).removeAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authLogoutStatusOk() {
        MockHttpSession session = spy(MockHttpSession.class);
        try {
            this.mockMvc.perform(post("/auth/logout")
            .session(session))
            .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authRegisterCallsAuthServiceRegister() {
        try {
            this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonRegister));
            verify(authService).register(any(User.class));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authRegisterStatusCreated() {
        try {
            this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonRegister))
                .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void BadAuthRegisterStatusBad() {
        try {
            this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("bad request"))
                .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authRegisterEmailTakenStatusBad() {
        try {
            when(authService.register(any(User.class))).thenThrow(new EmailReservedException(""));
            this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonRegister))
                .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void authRegisterEmailTakenReturnsMessage() {
        try {
            when(authService.register(any(User.class))).thenThrow(new EmailReservedException("test"));
            this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonRegister))
                .andExpect(content().string(containsString("test")));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
