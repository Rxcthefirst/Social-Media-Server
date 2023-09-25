package com.revature.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PostUnitTest {

	@Test
	public void compareToPostGreater() {
		Post post = new Post();
		post.setId(0);
		Post post2 = new Post();
		post2.setId(1);
		assertEquals(1, post.compareTo(post2));
	}
	
	@Test
	public void compareToPostLess() {
		Post post = new Post();
		post.setId(1);
		Post post2 = new Post();
		post2.setId(0);
		assertEquals(-1, post.compareTo(post2));
	}
}
