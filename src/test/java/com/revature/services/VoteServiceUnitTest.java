package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.PostNotFoundException;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.models.Vote;
import com.revature.models.VoteType;
import com.revature.repositories.PostRepository;
import com.revature.repositories.VoteRepository;

@ExtendWith(SpringExtension.class)
class VoteServiceUnitTest {

	@Mock
	private VoteRepository voteRepository;
	@Mock
	private PostRepository postRepository;
	
	@InjectMocks
	VoteService voteService;

	@Test
	public void voteHasAPost() {
		Post post = mock(Post.class);
		Vote vote = new Vote(0, null, post, null);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			voteService.vote(vote);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}
	
	@Test
	public void voteFirstTime() {
		Post post = mock(Post.class);
		Vote vote = new Vote(0, VoteType.UPVOTE, post, null);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			voteService.vote(vote);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}

	@Test
	public void voteAlreadyUpVote() {
		User user = mock(User.class);
		Post post = new Post(0, null, null, 0, null, user);
		Vote vote = new Vote(0, VoteType.UPVOTE, post, user);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user)).thenReturn(Optional.of(vote));
			voteService.vote(vote);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}
	
	@Test
	public void voteAlreadyVoteD() {
		User user = mock(User.class);
		Post post = new Post(0, null, null, 0, null, user);
		Vote vote = new Vote(0, VoteType.DOWNVOTE, post, user);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user)).thenReturn(Optional.of(vote));
			voteService.vote(vote);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}

	
	@Test
	public void voteUpVoteToDownVote() {
		User user = mock(User.class);
		Post post = new Post(0, null, null, 0, null, user);
		Vote vote = new Vote(0, VoteType.DOWNVOTE, post, user);
		Vote vote2 = new Vote(0, VoteType.UPVOTE, post, user);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user)).thenReturn(Optional.of(vote2));
			voteService.vote(vote);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}
	
	@Test
	public void voteDownVoteToUpVote() {
		User user = mock(User.class);
		Post post = new Post(0, null, null, 0, null, user);
		Vote vote = new Vote(0, VoteType.DOWNVOTE, post, user);
		Vote vote2 = new Vote(0, VoteType.UPVOTE, post, user);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.of(post));
		try {
			when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user)).thenReturn(Optional.of(vote));
			voteService.vote(vote2);
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}
}
