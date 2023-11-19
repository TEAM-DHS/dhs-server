package com.efub.dhs.domain.heart.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.heart.dto.HeartRequestDto;
import com.efub.dhs.domain.heart.dto.HeartResponseDto;
import com.efub.dhs.domain.heart.service.HeartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

	private final HeartService heartService;

	@PostMapping
	public ResponseEntity<HeartResponseDto> likeProgram(@RequestBody @Valid HeartRequestDto requestDto) {
		return heartService.heartProgram(requestDto.getProgramId());
	}
}
