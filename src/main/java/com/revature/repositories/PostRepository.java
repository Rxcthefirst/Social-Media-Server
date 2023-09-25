package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;
import com.revature.models.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByAuthor(User user);
}
