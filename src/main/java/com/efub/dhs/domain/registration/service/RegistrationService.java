package com.efub.dhs.domain.registration.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.MemberService;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.dto.RegistrationModificationRequestDto;
import com.efub.dhs.domain.registration.dto.RegistrationResponseDto;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.repository.RegistrationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

	private final RegistrationRepository registrationRepository;
	private final MemberService memberService;

	private Registration getRegistration(Long registrationId) {
		return registrationRepository.findById(registrationId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Boolean existsByMemberAndProgram(Member member, Program program) {
		return registrationRepository.existsByMemberAndProgram(member, program);
	}

	@Transactional(readOnly = true)
	public Registration saveRegistration(Registration registration) {
		return registrationRepository.save(registration);
	}

	@Transactional(readOnly = true)
	public Registration findRegistration(Long registrationId) {
		Registration registration = getRegistration(registrationId);
		Member currentUser = memberService.getCurrentUser();
		validateCurrentUser(currentUser, registration);
		return registration;
	}

	private void validateCurrentUser(Member currentUser, Registration registration) {
		if (!currentUser.equals(registration.getMember())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@Transactional(readOnly = true)
	public List<RegistrationResponseDto> findRegistratorList(Program program) {
		Member member = memberService.getCurrentUser();
		if (member.equals(program.getHost())) {
			List<Registration> registrationList = registrationRepository.findAllByProgram(program);
			return registrationList.stream().map(RegistrationResponseDto::from).collect(Collectors.toList());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public Registration modifyRegistration(Long registrationId, RegistrationModificationRequestDto requestDto) {
		Registration registration = getRegistration(registrationId);
		Member currentUser = memberService.getCurrentUser();
		validateCurrentUser(currentUser, registration);
		return registration.modifyRegistration(requestDto);
	}
}
