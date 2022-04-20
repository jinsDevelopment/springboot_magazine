package com.sparta.magazine.security;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String id) {
		System.out.println("loadUserByUsername : 진입");
		return userRepository.findById(id)
			.orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을 수 없습니다."));
	}
}
