package com.sparta.magazine.service;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.LikesRequestDto;
import com.sparta.magazine.model.Likes;
import com.sparta.magazine.model.Posts;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.LikesRepository;
import com.sparta.magazine.repository.PostRepository;
import com.sparta.magazine.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

	private final LikesRepository likesRepository;

	private final PostRepository postRepository;

	private final UserRepository userRepository;


	public LikesService(LikesRepository likesRepository,
		PostRepository postRepository, UserRepository userRepository) {
		this.likesRepository = likesRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}


	@Transactional
	public void saveLike(LikesRequestDto requestDto, String userId) {

		postRepository.findById(requestDto.getPostId()).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
		);
		userRepository.findById(userId).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 userId가 존재하지 않습니다.")
		);
		Optional<Likes> findLike = likesRepository.findAllByPostsIdAndUserId(requestDto.getPostId(), userId);
		if (!findLike.isPresent()) {
			Likes likes = new Likes(requestDto, userId);
			likesRepository.save(likes);
		}
	}

	@Transactional
	public void deleteLike(LikesRequestDto requestDto, String userId) {

		postRepository.findById(requestDto.getPostId()).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
		);
		userRepository.findById(userId).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 userId가 존재하지 않습니다.")
		);
		Optional<Likes> findLike = likesRepository.findAllByPostsIdAndUserId(requestDto.getPostId(), userId);
		if (findLike.isPresent()) {
			likesRepository.deleteById(findLike.get().getId());
		}
	}


}
