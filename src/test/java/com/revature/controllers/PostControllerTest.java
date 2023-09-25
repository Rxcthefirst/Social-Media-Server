package com.revature.controllers;

import com.revature.models.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private static TestRestTemplate restTemplate;

    @Autowired
    private PostController postController;

    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/post");
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(postController).isNotNull();
    }

    @Test
    void getAllPosts() {
    }

    @Test
    void testUpsertPost() {
        Post post = new Post(1,"text", "text", 0, null, null);
        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        restTemplate.postForEntity(baseUrl, responseEntity, Post.class);
    }
}