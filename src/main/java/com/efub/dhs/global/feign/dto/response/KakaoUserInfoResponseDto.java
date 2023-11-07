package com.efub.dhs.global.feign.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoUserInfoResponseDto {

	private long id;
	private int expires_in;
	private int app_id;
}
