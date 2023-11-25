package com.efub.dhs.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoOAuthRequestDto {

	private String redirectUri;
	private String code;
}
