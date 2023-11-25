package com.efub.dhs.global.feign.dto.response;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class KakaoUserInfoResponseDto {

	private long id;
	private int expires_in;
	private int app_id;

	public long getId() {
		return id;
	}
}
