package com.efub.dhs.global.feign.dto.response;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class KakaoTokenResponseDto {

	private String token_type;
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private int refresh_token_expires_in;

	public String getAccessToken() {
		return access_token;
	}
}
