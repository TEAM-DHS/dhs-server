package com.efub.dhs.domain.member.controller;

import static com.efub.dhs.global.jwt.utils.JwtUtils.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.member.dto.AuthRequestDto;
import com.efub.dhs.domain.member.dto.AuthResponseDto;
import com.efub.dhs.domain.member.dto.ProfileResponseDto;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.AuthService;
import com.efub.dhs.global.jwt.entity.JwtToken;
import com.efub.dhs.global.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public ProfileResponseDto signUp(@RequestBody @Valid AuthRequestDto requestDto) {
		Member member = authService.signUp(requestDto.getUsername(), requestDto.getPassword());
		return new ProfileResponseDto(member.getUsername());
	}

	@PostMapping("/login")
	public AuthResponseDto logIn(@RequestBody @Valid AuthRequestDto requestDto) {
		JwtToken jwtToken = authService.logIn(requestDto.getUsername(), requestDto.getPassword());
		return new AuthResponseDto(BEARER, jwtToken.getAccessToken());
	}

	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpServletRequest request) {
		String accessToken = resolveToken(request);
		jwtService.removeJwtToken(accessToken);
	}

	@PostMapping("/refresh-token")
	public AuthResponseDto refreshToken(HttpServletRequest request) {
		String accessToken = resolveToken(request);
		JwtToken jwtToken = jwtService.refreshToken(accessToken);
		return new AuthResponseDto(BEARER, jwtToken.getAccessToken());
	}
}
