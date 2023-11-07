package com.efub.dhs.global.feign.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoAuthorizationResponseDto {

	private String code;
	private String error;
	private String error_description;
	private String state;
}
