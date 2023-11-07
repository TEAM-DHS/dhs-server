package com.efub.dhs.domain.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequestDto {

	@NotBlank(message = "아이디는 필수입니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;
}
