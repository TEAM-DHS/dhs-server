package com.efub.dhs.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.efub.dhs.global.feign.dto.request.KakaoTokenRequestDto;
import com.efub.dhs.global.feign.dto.response.KakaoTokenResponseDto;

@FeignClient(name = "kakaoOAuth", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoTokenClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KakaoTokenResponseDto getToken(@RequestBody KakaoTokenRequestDto requestDto);
}
