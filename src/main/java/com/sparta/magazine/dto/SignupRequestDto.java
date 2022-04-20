package com.sparta.magazine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "아이디는 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기")
	private String username;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상 20자 이하")
	private String password;

	@NotBlank(message = "비밀번호 확인을 입력해주세요.")
	private String passwordCheck;

	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;

	public boolean passwordCheck(String password, String username) {
		return password.contains(username);
	}

	public boolean isPasswordEquals(String password, String passwordChk) {
		return password.equals(passwordChk);
	}
}