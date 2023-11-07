package com.efub.dhs.global.auth.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.global.auth.dto.SignUpRequestDto;
import com.efub.dhs.global.auth.dto.SignUpResponseDto;
import com.efub.dhs.global.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public SignUpResponseDto signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
		Member member = authService.signUp(requestDto.getUsername(), requestDto.getPassword());
		return new SignUpResponseDto(member.getMemberId(), member.getUsername());
	}
}
