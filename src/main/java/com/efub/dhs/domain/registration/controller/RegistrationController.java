package com.efub.dhs.domain.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efub.dhs.domain.program.dto.response.ProgramRegistrationResponseDto;
import com.efub.dhs.domain.registration.dto.RegistrationModificationRequestDto;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.service.RegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

	private final RegistrationService registrationService;

	@GetMapping("/{registrationId}")
	public ProgramRegistrationResponseDto findRegistration(@PathVariable Long registrationId) {
		Registration registration = registrationService.findRegistration(registrationId);
		return ProgramRegistrationResponseDto.from(registration);
	}

	@PatchMapping("/{registrationId}")
	public ProgramRegistrationResponseDto modifyRegistration(
		@PathVariable Long registrationId, @RequestBody RegistrationModificationRequestDto requestDto) {
		Registration modifiedRegistration = registrationService.modifyRegistration(registrationId, requestDto);
		return ProgramRegistrationResponseDto.from(modifiedRegistration);
	}
}
