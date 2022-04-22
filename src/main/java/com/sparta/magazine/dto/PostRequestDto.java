package com.sparta.magazine.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
	@NotBlank(message = "내용을 입력해 주세요.")
	private String contents;

	private String imagePath;

	private String layout;

}
