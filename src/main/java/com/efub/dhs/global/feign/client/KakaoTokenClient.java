package com.efub.dhs.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.efub.dhs.global.feign.dto.request.KakaoTokenRequestDto;
import com.efub.dhs.global.feign.dto.response.KakaoTokenResponseDto;

@FeignClient(name = "KakaoTokenClient", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoTokenClient {

	@PostMapping(consumes = "application/x-www-form-urlencoded")
	KakaoTokenResponseDto getToken(KakaoTokenRequestDto requestDto);
}
