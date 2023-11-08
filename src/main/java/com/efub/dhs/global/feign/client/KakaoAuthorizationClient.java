package com.efub.dhs.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.efub.dhs.global.feign.dto.request.KakaoAuthorizationRequestDto;
import com.efub.dhs.global.feign.dto.response.KakaoAuthorizationResponseDto;

@FeignClient(name = "KakaoAuthorizationClient", url = "https://kauth.kakao.com/oauth/authorize")
public interface KakaoAuthorizationClient {

	@GetMapping
	KakaoAuthorizationResponseDto getAuthorizationCode(KakaoAuthorizationRequestDto requestDto);
}
