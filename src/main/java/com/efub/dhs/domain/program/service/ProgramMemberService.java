package com.efub.dhs.domain.program.service;

import static com.efub.dhs.domain.program.dto.response.ProgramRegisteredResponseDto.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.heart.service.HeartService;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.dto.PageInfoDto;
import com.efub.dhs.domain.program.dto.response.ProgramListResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramOutlineResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramRegisteredResponseDto;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.repository.ProgramRepository;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.repository.RegistrationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramMemberService {

	private static final int PAGE_SIZE = 12;

	private final ProgramRepository programRepository;
	private final RegistrationRepository registrationRepository;
	private final ProgramService programService;
	private final HeartService heartService;

	@Transactional(readOnly = true)
	public ProgramListResponseDto findProgramCreated(int page) {
		Member currentUser = programService.getCurrentUser();
		Page<Program> programPage = programRepository.findAllByHost(currentUser, PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.createProgramPageInfoDto(programPage);
		List<ProgramOutlineResponseDto> programOutlineResponseDtoList =
			programService.convertToProgramOutlineResponseDtoList(programPage.getContent(), currentUser);
		return new ProgramListResponseDto(programOutlineResponseDtoList, pageInfoDto);
	}

	@Transactional(readOnly = true)
	public ProgramListResponseDto findProgramLiked(int page) {
		Member currentUser = programService.getCurrentUser();
		Page<Program> programPage = programRepository.findAllProgramLiked(currentUser, PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.createProgramPageInfoDto(programPage);
		List<ProgramOutlineResponseDto> programOutlineResponseDtoList =
			programService.convertToProgramOutlineResponseDtoList(programPage.getContent(), currentUser);
		return new ProgramListResponseDto(programOutlineResponseDtoList, pageInfoDto);
	}

	@Transactional(readOnly = true)
	public ProgramRegisteredResponseDto findProgramRegistered(int page) {
		Member currentUser = programService.getCurrentUser();
		Page<Registration> registrationPage = registrationRepository.findRegisteredPrograms(
			currentUser, PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.createRegistrationPageInfoDto(registrationPage);
		List<ProgramOutlineWithStatusDto> programOutlineWithStatusDtoList =
			convertToProgramOutlineWithStatusDtoList(registrationPage.getContent(), currentUser);
		return new ProgramRegisteredResponseDto(programOutlineWithStatusDtoList, pageInfoDto);
	}

	public List<ProgramOutlineWithStatusDto> convertToProgramOutlineWithStatusDtoList(
		List<Registration> registrationList, Member member) {
		return registrationList.stream().map(registration -> {
			Program program = registration.getProgram();
			return new ProgramOutlineWithStatusDto(program,
				programService.calculateRemainingDays(program.getDeadline()),
				programService.findGoalByProgram(program.getTargetNumber(), program.getRegistrantNumber()),
				heartService.existsByMemberAndProgram(member, program),
				registration.getRefundStatus());
		}).collect(Collectors.toList());
	}
}
