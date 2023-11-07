package com.efub.dhs.global.feign.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoTokenRequestDto {

	private final String grant_type = "authorization_code";
	private final String client_id;
	private final String redirect_uri;
	private final String code;

	@Builder
	public KakaoTokenRequestDto(String clientId, String redirectUri, String code) {
		this.client_id = clientId;
		this.redirect_uri = redirectUri;
		this.code = code;
	}
}
