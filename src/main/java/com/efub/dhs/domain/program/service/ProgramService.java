package com.efub.dhs.domain.program.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.heart.service.HeartService;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.repository.MemberRepository;
import com.efub.dhs.domain.program.dto.GoalDto;
import com.efub.dhs.domain.program.dto.HostDto;
import com.efub.dhs.domain.program.dto.ImageDto;
import com.efub.dhs.domain.program.dto.NoticeDto;
import com.efub.dhs.domain.program.dto.ProgramDto;
import com.efub.dhs.domain.program.dto.ProgramMemberDto;
import com.efub.dhs.domain.program.dto.request.ProgramCreationRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramRegistrationRequestDto;
import com.efub.dhs.domain.program.dto.response.ProgramDetailResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramOutlineResponseDto;
import com.efub.dhs.domain.program.entity.Notice;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.entity.ProgramImage;
import com.efub.dhs.domain.program.repository.NoticeRepository;
import com.efub.dhs.domain.program.repository.ProgramImageRepository;
import com.efub.dhs.domain.program.repository.ProgramRepository;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.service.RegistrationService;
import com.efub.dhs.global.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {

	private final ProgramRepository programRepository;
	private final ProgramImageRepository programImageRepository;
	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final HeartService heartService;
	private final RegistrationService registrationService;

	public Member getCurrentUser() {
		String username = SecurityUtils.getCurrentUsername();
		return memberRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원을 찾을 수 없습니다."));
	}

	private Program getProgram(Long programId) {
		return programRepository.findById(programId)
			.orElseThrow(() -> new IllegalArgumentException("해당 ID의 행사를 찾을 수 없습니다."));
	}

	@Transactional(readOnly = true)
	public ProgramDetailResponseDto findProgramById(Long programId) {
		Program program = getProgram(programId);

		Member currentUser = getCurrentUser();

		Integer remainingDays = calculateRemainingDays(program.getDeadline());

		GoalDto goal = findGoalByProgram(program.getTargetNumber(), program.getRegistrantNumber());

		return new ProgramDetailResponseDto(
			findProgramInfo(program, remainingDays, goal),
			findProgramMemberInfo(program, currentUser),
			findSimilarPrograms(program, currentUser)
		);
	}

	public Integer calculateRemainingDays(LocalDateTime deadline) {
		return Period.between(LocalDateTime.now().toLocalDate(), deadline.toLocalDate()).getDays();
	}

	public GoalDto findGoalByProgram(Integer targetNumber, Integer registrantNumber) {
		Double progressRate = registrantNumber.doubleValue() / targetNumber.doubleValue() * 100;
		return new GoalDto(targetNumber, registrantNumber, progressRate);
	}

	public List<ImageDto> findProgramImagesByProgram(Program program) {
		List<ProgramImage> programImages = programImageRepository.findAllByProgram(program);
		return programImages.stream().map(ImageDto::new).collect(Collectors.toList());
	}

	public List<NoticeDto> findNoticesByProgram(Program program) {
		List<Notice> notices = noticeRepository.findAllByProgram(program);
		return notices.stream().map(NoticeDto::new).collect(Collectors.toList());
	}

	public ProgramDto findProgramInfo(Program program, Integer remainingDays, GoalDto goal) {
		List<ImageDto> contentImages = findProgramImagesByProgram(program);

		List<NoticeDto> notices = findNoticesByProgram(program);

		String depositInfo =
			program.getDepositBank() + " " + program.getDepositAccount() + " " + program.getDepositName();

		HostDto host = new HostDto(program.getHostName(), program.getHostDescription());

		return new ProgramDto(program, remainingDays, goal, contentImages, depositInfo, notices, host);
	}

	public ProgramMemberDto findProgramMemberInfo(Program program, Member member) {
		Boolean hasLike = heartService.existsByMemberAndProgram(member, program);
		Boolean hasRegistration = registrationService.existsByMemberAndProgram(member, program);
		Boolean isHost = program.getHost().equals(member);
		return new ProgramMemberDto(hasLike, hasRegistration, isHost);
	}

	public List<ProgramOutlineResponseDto> findSimilarPrograms(Program program, Member member) {
		List<Program> similarPrograms =
			programRepository.findTop3ByCategory(program.getCategory());
		return convertToProgramOutlineResponseDtoList(similarPrograms, member);
	}

	public List<ProgramOutlineResponseDto> convertToProgramOutlineResponseDtoList(
		List<Program> programList, Member member) {
		return programList.stream().map(program ->
			new ProgramOutlineResponseDto(program,
				calculateRemainingDays(program.getDeadline()),
				findGoalByProgram(program.getTargetNumber(), program.getRegistrantNumber()),
				heartService.existsByMemberAndProgram(member, program))
		).collect(Collectors.toList());
	}

	public Long createProgram(ProgramCreationRequestDto requestDto) {
		Member currentUser = getCurrentUser();
		Program program = requestDto.toEntity(currentUser);
		Long programId = programRepository.save(program).getProgramId();
		List<ProgramImage> images = program.getImages();
		programImageRepository.saveAll(images);
		return programId;
	}

	public Registration registerProgram(Long programId, ProgramRegistrationRequestDto requestDto) {
		Member currentUser = getCurrentUser();
		Program program = getProgram(programId);
		Registration registration = requestDto.toEntity(currentUser, program);
		return registrationService.saveRegistration(registration);
	}
}
