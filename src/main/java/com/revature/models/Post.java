package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post implements Comparable<Post>  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(length = 3_000)
	private String text;
	private String imageUrl;
	private Integer voteCount = 0;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToOne
	private User author;

	@Override
	public int compareTo(Post o) {
		if (this.id > o.id)
			return -1;
		return 1;
	}
}
