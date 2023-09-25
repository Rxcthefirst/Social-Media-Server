package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.exceptions.PostNotFoundException;
import com.revature.exceptions.VoteNotFoundException;
import com.revature.models.Vote;
import com.revature.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vote")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200","http://p3fev2.s3-website-us-west-1.amazonaws.com/"}, allowCredentials = "true")
public class VoteController {

    private final VoteService voteService;

    @Authorized
    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody Vote vote) throws PostNotFoundException, VoteNotFoundException {
        voteService.vote(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/one/{userId}&{postId}")
    public ResponseEntity<Optional<Vote>> getVoteByUserAndPost(@PathVariable int userId, @PathVariable int postId){
        return ResponseEntity.ok(this.voteService.getVoteByUserIdAndPostId(userId, postId));
    }
}
