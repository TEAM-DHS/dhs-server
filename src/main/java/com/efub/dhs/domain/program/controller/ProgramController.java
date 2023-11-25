package com.efub.dhs.domain.program.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efub.dhs.domain.program.dto.request.NoticeCreationRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramCreationRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramListRequestDto;
import com.efub.dhs.domain.program.dto.request.ProgramRegistrationRequestDto;
import com.efub.dhs.domain.program.dto.response.NoticeCreationResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramCreationResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramDetailResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramListResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramOutlineResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramRegisteredResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramRegistrationResponseDto;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.entity.ProgramImage;
import com.efub.dhs.domain.program.service.NoticeService;
import com.efub.dhs.domain.program.service.ProgramImageService;
import com.efub.dhs.domain.program.service.ProgramMemberService;
import com.efub.dhs.domain.program.service.ProgramService;
import com.efub.dhs.domain.registration.dto.RegistrationResponseDto;
import com.efub.dhs.domain.registration.entity.Registration;
import com.efub.dhs.domain.registration.service.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

	private final ProgramService programService;
	private final ProgramMemberService programMemberService;
	private final RegistrationService registrationService;
	private final NoticeService noticeService;
	private final ProgramImageService programImageService;

	@GetMapping("/{programId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ProgramDetailResponseDto findProgramById(@PathVariable Long programId) {
		return programService.findProgramById(programId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProgramCreationResponseDto createProgram(
		@RequestPart(value = "data") @Valid ProgramCreationRequestDto requestDto,
		@RequestPart(value = "image") List<MultipartFile> images) {
		List<ProgramImage> programImages = programImageService.createProgramImages(images);
		Long programId = programService.createProgram(requestDto, programImages);
		return new ProgramCreationResponseDto(programId);
	}

	@PostMapping("/{programId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProgramRegistrationResponseDto registerProgram(@PathVariable Long programId,
		@RequestBody @Valid ProgramRegistrationRequestDto requestDto) {
		Registration savedRegistration = programService.registerProgram(programId, requestDto);
		return ProgramRegistrationResponseDto.from(savedRegistration);
	}

	@PatchMapping("/{programId}/closed")
	@ResponseStatus(value = HttpStatus.OK)
	public ProgramDetailResponseDto closeProgram(@PathVariable Long programId) {
		return programService.closeProgram(programId);
	}

	@GetMapping("/created")
	public ProgramListResponseDto findProgramCreated(@RequestParam int page) {
		return programMemberService.findProgramCreated(page);
	}

	@GetMapping("/liked")
	public ProgramListResponseDto findProgramLiked(@RequestParam int page) {
		return programMemberService.findProgramLiked(page);
	}

	@GetMapping("/registered")
	public ProgramRegisteredResponseDto findProgramRegistered(@RequestParam int page) {
		return programMemberService.findProgramRegistered(page);
	}

	@GetMapping
	public ProgramListResponseDto findProgramList(@RequestParam int page, ProgramListRequestDto requestDto) {
		return programService.findProgramList(page, requestDto);
	}

	@GetMapping("/popular")
	public List<ProgramOutlineResponseDto> findProgramPopular() {
		return programService.findProgramPopular();
	}

	@GetMapping("/{programId}/registrations")
	public List<RegistrationResponseDto> findRegistratorList(@PathVariable Long programId) {
		Program program = programService.getProgram(programId);
		return registrationService.findRegistratorList(program);
	}

	@PostMapping("/{programId}/notice")
	@ResponseStatus(HttpStatus.CREATED)
	public NoticeCreationResponseDto createNotice(@PathVariable Long programId,
		@RequestBody NoticeCreationRequestDto requestDto) {
		Program program = programService.getProgram(programId);
		return noticeService.createNotice(program, requestDto);
	}
}
