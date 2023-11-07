package com.efub.dhs.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

	private final String grantType;
	private final String accessToken;
}
