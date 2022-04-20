package com.sparta.magazine.model;

import com.sparta.magazine.dto.LikesRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
// LIKE로 네이밍시 에러남..
public class Likes {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Posts posts;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Likes(LikesRequestDto requestDto, String userId){
		this.posts = new Posts();
		this.posts.setId(requestDto.getPostId());
		this.user = new User();
		this.user.setId(userId);
	}

}
