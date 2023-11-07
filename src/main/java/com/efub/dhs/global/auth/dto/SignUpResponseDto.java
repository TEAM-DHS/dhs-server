package com.efub.dhs.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {

	private final Long memberId;
	private final String username;
}
