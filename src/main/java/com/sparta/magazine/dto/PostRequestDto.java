package com.sparta.magazine.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
	@NotBlank(message = "내용을 입력해 주세요.")
	private String contents;

	private String imagePath;

	private String layout;

}
