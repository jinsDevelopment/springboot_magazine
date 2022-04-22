package com.sparta.magazine.service;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.model.Likes;
import com.sparta.magazine.model.Posts;
import com.sparta.magazine.repository.PostRepository;
import com.sparta.magazine.repository.LikesRepository;
import com.sparta.magazine.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final LikesRepository likesRepository;

	public PostService(PostRepository postRepository,
		UserRepository userRepository,
		LikesRepository likesRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.likesRepository = likesRepository;
	}

	@Transactional
	public PostResponseDto getBoardDetail(Long postId, String userId) {

		PostResponseDto postResponseDto = new PostResponseDto();

		Posts posts = postRepository.findById(postId).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
		);

		String nickname = userRepository.findById(posts.getUser().getId()).get().getNickname();
		int likeCount = likesRepository.countAllByPosts_Id(posts.getId());
		Boolean likesYn = false;

		if (userId != null){

			Optional<Likes> likes = likesRepository.findAllByPostsIdAndUserId(posts.getId(), userId);
			if (!likes.isPresent()) {
				likesYn = false;
			} else {
				likesYn = true;
			}

		}

		postResponseDto.setPostId(postId);
		postResponseDto.setNickname(nickname);
		postResponseDto.setContents(posts.getContents());
		postResponseDto.setImagePath(posts.getImagePath());
		postResponseDto.setLikeCount(likeCount);
		postResponseDto.setLiked(likesYn);
		postResponseDto.setCreatedAt(posts.getCreatedAt());
		postResponseDto.setModifiedAt(posts.getModifiedAt());
		postResponseDto.setLayout(posts.getLayout());

		return postResponseDto;
	}

	@Transactional
	public List<PostResponseDto> getBoardAll() {

		List<PostResponseDto> postResponseDtoList = new ArrayList<>();

		List<Posts> postsList = postRepository.findAllByOrderByCreatedAtDesc();

		for (Posts posts : postsList) {

			PostResponseDto postResponseDto = new PostResponseDto();
			String nickname = userRepository.findById(posts.getUser().getId()).get().getNickname();
			int likeCount = likesRepository.countAllByPosts_Id(posts.getId());
			postResponseDto.setPostId(posts.getId());
			postResponseDto.setNickname(nickname);
			postResponseDto.setContents(posts.getContents());
			postResponseDto.setImagePath(posts.getImagePath());
			postResponseDto.setLikeCount(likeCount);
			postResponseDto.setLiked(false);
			postResponseDto.setCreatedAt(posts.getCreatedAt());
			postResponseDto.setModifiedAt(posts.getModifiedAt());
			postResponseDto.setLayout(posts.getLayout());
			postResponseDtoList.add(postResponseDto);

		}
		return postResponseDtoList;
	}

	@Transactional
	public List<PostResponseDto> getBoardAll(String userId) {

		List<PostResponseDto> postResponseDtoList = new ArrayList<>();

		List<Posts> postsList = postRepository.findAllByOrderByCreatedAtDesc();

		for (Posts posts : postsList) {

			PostResponseDto postResponseDto = new PostResponseDto();

			String nickname = userRepository.findById(posts.getUser().getId()).get().getNickname();
			int likeCount = likesRepository.countAllByPosts_Id(posts.getId());
			Boolean likesYn = false;
			Optional<Likes> likes = likesRepository.findAllByPostsIdAndUserId(posts.getId(),
				userId);
			if (!likes.isPresent()) {
				likesYn = false;
			} else {
				likesYn = true;
			}
			postResponseDto.setPostId(posts.getId());
			postResponseDto.setNickname(nickname);
			postResponseDto.setContents(posts.getContents());
			postResponseDto.setImagePath(posts.getImagePath());
			postResponseDto.setLikeCount(likeCount);
			postResponseDto.setLiked(likesYn);
			postResponseDto.setCreatedAt(posts.getCreatedAt());
			postResponseDto.setModifiedAt(posts.getModifiedAt());
			postResponseDto.setLayout(posts.getLayout());
			postResponseDtoList.add(postResponseDto);

		}
		return postResponseDtoList;
	}

	@Transactional
	public void postSave(PostRequestDto postRequestDto, String userId) {
		userRepository.findById(userId).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
		);

		Posts posts = new Posts(postRequestDto, userId);
		postRepository.save(posts);
	}

	@Transactional
	public void delete(Long postId) {
		postRepository.deleteById(postId);
	}

	@Transactional
	public void modify(Long postId, PostRequestDto requestDto, String userId) {
		Posts post = postRepository.findById(postId).orElseThrow(
			() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
		);
		if (post.getUser().getId().equals(userId)) {
			post.update(requestDto);
		} else {
			throw new RestException(HttpStatus.BAD_REQUEST, "userId가 일치하지 않습니다.");
		}
	}
}
