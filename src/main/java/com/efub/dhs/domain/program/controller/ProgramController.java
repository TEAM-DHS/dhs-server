package com.efub.dhs.domain.program.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.program.dto.ProgramDetailResponseDto;
import com.efub.dhs.domain.program.service.ProgramService;

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
}
