package com.sparta.magazine.service;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.LoginRequestDto;
import com.sparta.magazine.dto.SignupRequestDto;
import com.sparta.magazine.dto.UserResponseDto;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.security.JwtTokenProvider;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
		JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Transactional
	public void registerUser(SignupRequestDto requestDto) {

		String userId = requestDto.getUsername();
		// 회원 ID 중복 확인
		Optional<User> found = userRepository.findById(userId);
		if (found.isPresent()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "중복된 사용자 ID 가 존재합니다.");
		}

		if (requestDto.passwordCheck(requestDto.getPassword(), requestDto.getUsername())) {
			throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호 내에 아이디를 포함할 수 없습니다.");
		}

		if (!requestDto.isPasswordEquals(requestDto.getPassword(), requestDto.getPasswordCheck())) {
			throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호확인이 일치하지 않습니다.");
		}

		// 패스워드 암호화
		String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
		String nickname = requestDto.getNickname();

		User user = new User(userId, nickname, password, Collections.singletonList("ROLE_USER"));
		userRepository.save(user);
	}
	@Transactional
	public String login(LoginRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUsername())
			.orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "가입되지 않은 ID 입니다."));
		if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new RestException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
		}
		String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());

		return accessToken;
	}

	@Transactional
	public void logout(HttpServletRequest request){
		SecurityContextHolder.clearContext();
	}

}
