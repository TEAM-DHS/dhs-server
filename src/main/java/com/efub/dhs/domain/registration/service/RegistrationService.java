package com.efub.dhs.domain.registration.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.repository.RegistrationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

	private final RegistrationRepository registrationRepository;

	@Transactional(readOnly = true)
	public Boolean existsByMemberAndProgram(Member member, Program program) {
		return registrationRepository.existsByMemberAndProgram(member, program);
	}

	@Transactional(readOnly = true)
	public Registration saveRegistration(Registration registration) {
		return registrationRepository.save(registration);
	}
}
