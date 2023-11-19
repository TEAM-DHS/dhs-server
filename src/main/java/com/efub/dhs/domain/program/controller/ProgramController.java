package com.efub.dhs.domain.program.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.program.dto.request.ProgramCreationRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramRegistrationRequestDto;
import com.efub.dhs.domain.program.dto.response.ProgramCreationResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramDetailResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramRegistrationResponseDto;
import com.efub.dhs.domain.program.service.ProgramService;
import com.efub.dhs.domain.registration.entity.Registration;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

	private final ProgramService programService;

	@GetMapping("/{programId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ProgramDetailResponseDto programFind(@PathVariable Long programId) {
		return programService.findProgramById(programId);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProgramCreationResponseDto createProgram(@RequestBody @Valid ProgramCreationRequestDto requestDto) {
		return new ProgramCreationResponseDto(programService.createProgram(requestDto));
	}

	@PostMapping("/{programId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProgramRegistrationResponseDto applyProgram(@PathVariable Long programId,
		@RequestBody @Valid ProgramRegistrationRequestDto requestDto) {
		Registration savedRegistration = programService.registerProgram(programId, requestDto);
		return ProgramRegistrationResponseDto.from(savedRegistration);
	}
}
