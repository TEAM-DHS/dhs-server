package com.efub.dhs.global.feign.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoAuthorizationRequestDto {

	private final String client_id;
	private final String redirect_uri;
	private final String response_type = "code";

	@Builder
	public KakaoAuthorizationRequestDto(String clientId, String redirectUri) {
		this.client_id = clientId;
		this.redirect_uri = redirectUri;
	}
}
