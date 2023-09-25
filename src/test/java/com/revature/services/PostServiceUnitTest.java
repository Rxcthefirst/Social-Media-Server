package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;

@ExtendWith(SpringExtension.class)
public class PostServiceUnitTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    PostService postService;
    

    @Test
    public void getAllCallsRepositoryFindAll(){
        when(postRepository.findAll()).thenReturn(new ArrayList<Post>());
        postService.getAll();
        verify(postRepository).findAll();
    }

    @Test
    public void upsertCallsRepositorySave(){
        Post post = mock(Post.class);
        when(postRepository.save(post)).thenReturn(post);
        postService.upsert(post);
        verify(postRepository).save(post);
    }
    
    @Test
    public void getOneCallsRepositoryFindById(){ 
    	Post post = mock(Post.class);
    	when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
    	postService.getOne(post.getId());
    	verify(postRepository).findById(post.getId());
    }
    
    @Test
    public void getAllSortedCallsRepositoryFindAll(){
        when(postRepository.findAll()).thenReturn(new ArrayList<Post>());
        postService.getAllSorted();
        verify(postRepository).findAll();
    }
        
    @Test
    public void userPostsCallsRepositoryFindAllByAuthor(){
    	User user = mock(User.class);
        when(postRepository.findAllByAuthor(user)).thenReturn(new ArrayList<Post>());
        postService.userPosts(user);
        verify(postRepository).findAllByAuthor(user);
    }
    
    
}
