package com.efub.dhs.domain.member.controller;

import static com.efub.dhs.global.jwt.utils.JwtUtils.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.member.dto.AuthResponseDto;
import com.efub.dhs.domain.member.dto.KakaoOAuthRequestDto;
import com.efub.dhs.domain.member.service.KakaoOAuthService;
import com.efub.dhs.global.jwt.entity.JwtToken;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

	private final KakaoOAuthService kakaoOAuthService;

	@PostMapping("/kakao")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponseDto kakaoSignUp(@RequestBody KakaoOAuthRequestDto requestDto) {
		JwtToken jwtToken = kakaoOAuthService.signUp(requestDto.getRedirectUri(), requestDto.getCode());
		return new AuthResponseDto(BEARER, jwtToken.getAccessToken());
	}
}
