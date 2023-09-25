package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.models.Post;
import com.revature.services.PostService;
import com.revature.utils.ProfanityFilter;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerUnitTest {

    @Mock
    private PostService postService;

    @Mock
    private ProfanityFilter profanityFilter;

    @InjectMocks
    PostController postController;
    
    private MockMvc mockMvc;

    static String jsonMockPut;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(postController);
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }
    
    @BeforeAll
    public static void setupMockPutJson(){

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"id\":0,");
        jsonBuilder.append("\"text\":\"\",");
        jsonBuilder.append("\"imageUrl\":\"\",");
        jsonBuilder.append("\"comments\":[],");
        jsonBuilder.append("\"author\":");
            jsonBuilder.append("{\"id\":0,");
            jsonBuilder.append("\"email\":\"\",");
            jsonBuilder.append("\"password\":\"\",");
            jsonBuilder.append("\"firstName\":\"\",");
            jsonBuilder.append("\"lastName\":\"\"");
        jsonBuilder.append("}}");
        jsonMockPut = jsonBuilder.toString();
    }

    @Test
    public void getPostCallsPostServiceGetAll() {
        try {
            this.mockMvc.perform(get("/post"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        verify(postService).getAllSorted();
    }
    
    @Test
    public void getPostStatusOK() {
        try {
            this.mockMvc.perform(get("/post"))
            .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void putPostCallsPostServiceUpsertIfNoProfanity() {

        try {
            when(profanityFilter.hasProfanity(anyString())).thenReturn(false);
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonMockPut));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        verify(postService).upsert(any(Post.class));
    }

    @Test
    public void putPostStatusOK() {

        try {
            when(profanityFilter.hasProfanity(anyString())).thenReturn(false);
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonMockPut.toString()))
                    .andExpect(status().isOk());
                
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void badPutPostStatusBad() {

        try {
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("bad json"))
                    .andExpect(status().isBadRequest());
                
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
