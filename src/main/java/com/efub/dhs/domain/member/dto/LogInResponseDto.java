package com.efub.dhs.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogInResponseDto {

	private final String grantType;
	private final String accessToken;
}
