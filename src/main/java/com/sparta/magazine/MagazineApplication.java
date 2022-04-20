package com.sparta.magazine;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.SignupRequestDto;
import com.sparta.magazine.service.PostService;
import com.sparta.magazine.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ServletComponentScan
@EnableJpaAuditing
@SpringBootApplication
public class MagazineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagazineApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(PostService postService, UserService userService) {
//		return (args) -> {
//
//			PostRequestDto requestDTO = new PostRequestDto();
//			String username = "aaa";
//			String nickname = "nick";
//			String password = "ccc";
//
//			SignupRequestDto signupRequestDto = new SignupRequestDto();
//
//			signupRequestDto.setUsername(username);
//			signupRequestDto.setNickname(nickname);
//			signupRequestDto.setPassword(password);
//			signupRequestDto.setPasswordCheck(password);
//
//			userService.registerUser(signupRequestDto);
//
//			requestDTO.setContents("내용1");
//			requestDTO.setImagePath("");
//			postService.postSave(requestDTO,username);
//
//		};
//	}

}
