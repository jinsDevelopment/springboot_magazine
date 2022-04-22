package com.sparta.magazine.controller;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.model.Success;
import com.sparta.magazine.model.User;
import com.sparta.magazine.service.PostService;
import com.sparta.magazine.service.LikesService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class PostController {

	private final PostService postService;

	private final LikesService likesService;

	public PostController(PostService boardService,
		LikesService likesService) {
		this.postService = boardService;
		this.likesService = likesService;
	}

	@GetMapping("/post")
	public List<PostResponseDto> postList(HttpServletRequest request, @AuthenticationPrincipal User user) {

		List<PostResponseDto> boardResponseDtoList = new ArrayList<PostResponseDto>();

		if(user == null){

			boardResponseDtoList = postService.getBoardAll();

		}else {

			boardResponseDtoList = postService.getBoardAll(user.getId());

		}

		return boardResponseDtoList;
	}

	@GetMapping("/post/{postId}")
	public PostResponseDto getPostDetail(@PathVariable Long postId, @AuthenticationPrincipal User user) {

		PostResponseDto postResponseDto;

		if(user == null){

			postResponseDto = postService.getBoardDetail(postId, null);

		} else {

			postResponseDto = postService.getBoardDetail(postId,user.getId());

		}

		return postResponseDto;
	}

	@PostMapping("/post")
	public ResponseEntity<Success> savePost(@RequestBody @Valid PostRequestDto requestDto, @AuthenticationPrincipal User user, Errors errors) {
		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
			}
		}
		postService.postSave(requestDto, user.getId());
		return new ResponseEntity<>(new Success(true, "게시글 저장 성공"), HttpStatus.OK);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<Success> deletePost(@PathVariable Long postId) {
		postService.delete(postId);
		return new ResponseEntity<>(new Success(true, "게시글 삭제 성공"), HttpStatus.OK);
	}


	@PatchMapping("post/{postId}")
	public ResponseEntity<Success> modifyPost(@PathVariable Long postId, @RequestBody @Valid PostRequestDto requestDto, @AuthenticationPrincipal User user, Errors errors) {
		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
			}
		}
		postService.modify(postId, requestDto, user.getId());
		return new ResponseEntity<>(new Success(true, "게시글 수정 성공"), HttpStatus.OK);
	}

}
