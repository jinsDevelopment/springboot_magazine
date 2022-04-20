package com.sparta.magazine.controller;

import com.sparta.magazine.dto.LikesRequestDto;
import com.sparta.magazine.model.Success;
import com.sparta.magazine.model.User;
import com.sparta.magazine.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikesController {

	private final LikesService likesService;

	public LikesController(LikesService likesService) {
		this.likesService = likesService;
	}


	@PostMapping("/api/post/like")
	public ResponseEntity<Success> postLike(@RequestBody LikesRequestDto requestDto, @AuthenticationPrincipal User user) {

		likesService.saveLike(requestDto, user.getId());
		return new ResponseEntity<>(new Success(true, "좋아요 성공"), HttpStatus.OK);
	}

	@DeleteMapping("/api/post/like")
	public ResponseEntity<Success> postLikeDelete(@RequestBody LikesRequestDto requestDto, @AuthenticationPrincipal User user) {

		likesService.deleteLike(requestDto, user.getId());
		return new ResponseEntity<>(new Success(true, "좋아요 취소"), HttpStatus.OK);
	}

}
