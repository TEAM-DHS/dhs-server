package com.efub.dhs.global.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoTokenRequestDto {

	private String grant_type;
	private String client_id;
	private String redirect_uri;
	private String code;
}
