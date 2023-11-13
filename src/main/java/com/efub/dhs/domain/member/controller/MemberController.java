package com.efub.dhs.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.member.dto.ProfileResponseDto;
import com.efub.dhs.global.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	@GetMapping("/members/me")
	public ProfileResponseDto getProfile() {
		return new ProfileResponseDto(SecurityUtils.getCurrentUsername());
	}
}
