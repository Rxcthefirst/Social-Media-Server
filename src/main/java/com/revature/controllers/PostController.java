package com.revature.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.PostNotFoundException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.utils.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.annotations.Authorized;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    @Autowired
    private UserService userService;

    private final ProfanityFilter profanityFilter;

    public PostController(PostService postService, ProfanityFilter profanityFilter) {
        this.postService = postService;
        this.profanityFilter = profanityFilter;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAllSorted());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<?> upsertPost(@RequestBody Post post) throws IOException {
        if (profanityFilter.hasProfanity(post.getText())){
            return ResponseEntity.badRequest().body("profanity");
        }
    	return ResponseEntity.ok(this.postService.upsert(post));
    }

    @Authorized
    @PutMapping("/comment")
    public ResponseEntity<Comment> upsertComment(@RequestBody Comment comment) {
    	return ResponseEntity.ok(this.postService.upsertComment(comment));
    }


    /**
     * Retrieves all user's posts by <b>user's id</b> specified in the path.
     * @param id
     * @return List<Post> || Status "400"
     */
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<List<Post>> userPosts(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.postService.userPosts(this.userService.findById(id).get()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Authorized
    @GetMapping("/one/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.postService.getOne(id).get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Authorized
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id){
        try {
            postService.delete(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Authorized
    @GetMapping("/subscribed")
    public ResponseEntity<Object> getAllSubscribedPosts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        
    	try {
            return ResponseEntity.ok(this.postService.getAllSubscribedPosts(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
