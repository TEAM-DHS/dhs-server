package com.efub.dhs.domain.program.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.efub.dhs.domain.heart.service.HeartService;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.MemberService;
import com.efub.dhs.domain.program.dto.GoalDto;
import com.efub.dhs.domain.program.dto.HostDto;
import com.efub.dhs.domain.program.dto.ImageDto;
import com.efub.dhs.domain.program.dto.NoticeDto;
import com.efub.dhs.domain.program.dto.PageInfoDto;
import com.efub.dhs.domain.program.dto.ProgramDto;
import com.efub.dhs.domain.program.dto.ProgramMemberDto;
import com.efub.dhs.domain.program.dto.request.ProgramCreationRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramListRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramRegistrationRequestDto;
import com.efub.dhs.domain.program.dto.response.ProgramDetailResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramListResponseDto;
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

	private static final int PAGE_SIZE = 12;

	private final ProgramRepository programRepository;
	private final ProgramImageRepository programImageRepository;
	private final NoticeRepository noticeRepository;
	private final MemberService memberService;
	private final HeartService heartService;
	private final RegistrationService registrationService;

	public Program getProgram(Long programId) {
		return programRepository.findById(programId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 행사를 찾을 수 없습니다." + programId));
	}

	@Transactional(readOnly = true)
	public ProgramDetailResponseDto findProgramById(Long programId) {
		Program program = getProgram(programId);

		Member currentUser = isLoggedIn(SecurityUtils.getCurrentUsername());

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
		boolean hasLike;
		boolean hasRegistration;
		boolean isHost;
		if (member == null) {
			hasLike = false;
			hasRegistration = false;
			isHost = false;
		} else {
			hasLike = heartService.existsByMemberAndProgram(member, program);
			hasRegistration = registrationService.existsByMemberAndProgram(member, program);
			isHost = program.getHost().equals(member);
		}
		return new ProgramMemberDto(hasLike, hasRegistration, isHost);
	}

	public List<ProgramOutlineResponseDto> findSimilarPrograms(Program program, Member member) {
		List<Program> similarProgramList =
			programRepository.findTop3ByCategoryAndIsOpenAndDeadlineAfterAndProgramIdNotOrderByDeadlineAsc(
				program.getCategory(), true, LocalDateTime.now(), program.getProgramId());
		return convertToProgramOutlineResponseDtoList(similarProgramList, member);
	}

	public List<ProgramOutlineResponseDto> convertToProgramOutlineResponseDtoList(
		List<Program> programList, Member member) {
		if (member == null) {
			return programList.stream().map(program ->
				new ProgramOutlineResponseDto(program,
					calculateRemainingDays(program.getDeadline()),
					findGoalByProgram(program.getTargetNumber(), program.getRegistrantNumber()),
					false)
			).collect(Collectors.toList());
		} else {
			return programList.stream().map(program ->
				new ProgramOutlineResponseDto(program,
					calculateRemainingDays(program.getDeadline()),
					findGoalByProgram(program.getTargetNumber(), program.getRegistrantNumber()),
					heartService.existsByMemberAndProgram(member, program))
			).collect(Collectors.toList());
		}
	}

	public Long createProgram(ProgramCreationRequestDto requestDto, List<ProgramImage> programImages) {
		Member currentUser = memberService.getCurrentUser();
		Program program = requestDto.toEntity(currentUser);
		Program savedProgram = programRepository.save(program);
		for (ProgramImage programImage : programImages) {
			programImage.setProgram(savedProgram);
			programImageRepository.save(programImage);
		}
		return savedProgram.getProgramId();
	}

	public ProgramDetailResponseDto closeProgram(Long programId) {
		Member currentUser = memberService.getCurrentUser();
		Program program = getProgram(programId);
		if (currentUser.equals(program.getHost())) {
			program.closeProgram();
			return findProgramById(program.getProgramId());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public Registration registerProgram(Long programId, ProgramRegistrationRequestDto requestDto) {
		Member currentUser = memberService.getCurrentUser();
		Program program = getProgram(programId);
		Registration registration = requestDto.toEntity(currentUser, program);
		Registration savedRegistration = registrationService.saveRegistration(registration);
		program.increaseRegistrantNumber();
		return savedRegistration;
	}

	public ProgramListResponseDto findProgramList(int page, ProgramListRequestDto requestDto) {
		Member currentUser = isLoggedIn(SecurityUtils.getCurrentUsername());

		Page<Program> programPage = programRepository.findProgramListByFilter(requestDto,
			PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.createProgramPageInfoDto(programPage);
		List<ProgramOutlineResponseDto> programOutlineResponseDtoList =
			convertToProgramOutlineResponseDtoList(programPage.getContent(), currentUser);
		return new ProgramListResponseDto(programOutlineResponseDtoList, pageInfoDto);
	}

	public List<ProgramOutlineResponseDto> findProgramPopular() {
		List<Program> popularProgramList =
			programRepository.findTop5ByIsOpenAndDeadlineAfterOrderByLikeNumberDesc(true, LocalDateTime.now());
		return convertToProgramOutlineResponseDtoList(popularProgramList, null);
	}

	public Member isLoggedIn(String username) {
		Member currentUser;
		if (username.equals("anonymousUser")) {
			currentUser = null;
		} else {
			currentUser = memberService.getCurrentUser();
		}
		return currentUser;
	}
}
