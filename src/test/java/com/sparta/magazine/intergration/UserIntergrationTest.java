package com.sparta.magazine.intergration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.SignupRequestDto;
import com.sparta.magazine.model.User;
import com.sparta.magazine.service.UserService;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntergrationTest {

// 	@Autowired
// 	UserService userService;

// 	@Autowired
// 	BCryptPasswordEncoder bCryptPasswordEncoder;

// 	@Test
// 	@Order(1)
// 	@DisplayName("회원가입 성공")
// 	@Transactional
// 	void test1() {
// 		// given
// 		String username = "jinseoLee";
// 		String password = "qwer1234";
// 		String passwordCheck = "qwer1234";
// 		String nickname = "jin";

// 		SignupRequestDto signupRequestDto = new SignupRequestDto(
// 			username,
// 			password,
// 			passwordCheck,
// 			nickname
// 		);

// 		User user = userService.registerUser(signupRequestDto);

// 		assertEquals(username,user.getUsername());
// 		assertEquals(nickname,user.getNickname());
// 		assertTrue(bCryptPasswordEncoder.matches(password,user.getPassword()));
// 	}

// 	@Test
// 	@Order(2)
// 	@DisplayName("회원 가입 실패 - username 유효성 검사 실패1")
// 	void test2() {
// 		// given
// 		String username = "null";
// 		String password = "qwer1234";
// 		String passwordCheck = "qwer1234";
// 		String nickname = "jin";

// 		SignupRequestDto signupRequestDto = new SignupRequestDto(
// 			username,
// 			password,
// 			passwordCheck,
// 			nickname
// 		);

// 		// when
// 		Exception exception = assertThrows(RestException.class, () -> {
// 			userService.registerUser(signupRequestDto);
// 		});

// 		//then
// 		assertEquals("사용할 수 없는 ID 입니다.",exception.getMessage());
// 	}

// 	@Test
// 	@Order(3)
// 	@DisplayName("회원 가입 실패 - username 유효성 검사 실패2")
// 	void test3() {
// 		// given
// 		String username = "jinseo";
// 		String password = "qwer1234";
// 		String passwordCheck = "qwer1234";
// 		String nickname = "jin";

// 		SignupRequestDto signupRequestDto = new SignupRequestDto(
// 			username,
// 			password,
// 			passwordCheck,
// 			nickname
// 		);

// 		// when
// 		Exception exception = assertThrows(RestException.class, () -> {
// 			userService.registerUser(signupRequestDto);
// 		});

// 		// then
// 		assertEquals("중복된 사용자 ID 가 존재합니다.",exception.getMessage());
// 	}

}

