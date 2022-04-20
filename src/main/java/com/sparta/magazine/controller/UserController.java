package com.sparta.magazine.controller;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.LoginRequestDto;
import com.sparta.magazine.dto.SignupRequestDto;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.model.Success;
import com.sparta.magazine.model.User;
import com.sparta.magazine.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(
		UserService userService) {
		this.userService = userService;
	}

	// 회원 로그인 페이지
	@GetMapping("/")
	public String index() {

		//로그인 후 접근시 막기
		return "login";
	}

	@GetMapping("/user/signup")
	public String signupForm() {
		return "signup";
	}

	@PostMapping("/user/signup")
	@ResponseBody
	public ResponseEntity<Success> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, Errors errors) {

		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
			}
		}

		userService.registerUser(signupRequestDto);
		return new ResponseEntity<>(new Success(true, "회원가입에 성공했습니다."), HttpStatus.OK);
	}

	@PostMapping("/user/login")
	@ResponseBody
	public ResponseEntity<Success> login(@RequestBody LoginRequestDto requestDto,@AuthenticationPrincipal User user, HttpServletResponse response, Errors errors) {

        if (user != null){
            return new ResponseEntity<>(new Success(false, "이미 로그인 중입니다."), HttpStatus.BAD_REQUEST);
        }
		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
			}
		}

		String token = userService.login(requestDto);

		response.addHeader("Authorization", token);

		return new ResponseEntity<>(new Success(true, "로그인에 성공했습니다."), HttpStatus.OK);
	}

	@PostMapping("/user/logout")
	@ResponseBody
	public ResponseEntity<Success> logout(HttpServletRequest request) {
		userService.logout(request);
		return new ResponseEntity<>(new Success(true, "로그아웃 성공"), HttpStatus.OK);
	}

	@GetMapping("/user/info")
	@ResponseBody
	public UserResponseDto userInfo(@AuthenticationPrincipal User user) {

		UserResponseDto userResponseDto = new UserResponseDto();

		if (user != null){
			userResponseDto.setUsername(user.getId());
			userResponseDto.setNickname(user.getNickname());
		}

		return userResponseDto;
	}



}
