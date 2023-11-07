package com.efub.dhs.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {

	private final Long memberId;
	private final String username;
}
