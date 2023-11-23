package com.efub.dhs.domain.registration.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.MemberService;
import com.efub.dhs.domain.program.entity.Program;
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

	@Transactional(readOnly = true)
	public Boolean existsByMemberAndProgram(Member member, Program program) {
		return registrationRepository.existsByMemberAndProgram(member, program);
	}

	@Transactional(readOnly = true)
	public Registration saveRegistration(Registration registration) {
		return registrationRepository.save(registration);
	}

	@Transactional(readOnly = true)
	public List<RegistrationResponseDto> findRegistratorList(Program program) {
		Member member = memberService.getCurrentUser();
		if (member.equals(program.getHost())) {
			List<Registration> registrationList = registrationRepository.findAllByProgram(program);
			return registrationList.stream().map(RegistrationResponseDto::from).collect(Collectors.toList());
		} else {
			throw new SecurityException("접근 권한이 없습니다.");
		}
	}
}
