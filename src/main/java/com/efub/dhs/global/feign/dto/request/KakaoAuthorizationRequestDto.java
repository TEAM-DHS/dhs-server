package com.efub.dhs.global.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class KakaoAuthorizationRequestDto {

	private final String client_id;
	private final String redirect_uri;
	private final String response_type;
}
