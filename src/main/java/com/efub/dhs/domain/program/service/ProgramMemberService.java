package com.efub.dhs.domain.program.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.dto.PageInfoDto;
import com.efub.dhs.domain.program.dto.response.ProgramCreatedResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramListResponseDto;
import com.efub.dhs.domain.program.dto.response.ProgramOutlineResponseDto;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramMemberService {

	private static final int PAGE_SIZE = 12;

	private final ProgramRepository programRepository;
	private final ProgramService programService;

	@Transactional(readOnly = true)
	public ProgramCreatedResponseDto findProgramCreated(int page) {
		Member currentUser = programService.getCurrentUser();
		Page<Program> programPage = programRepository.findAllByHost(currentUser, PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.from(programPage);
		List<ProgramOutlineResponseDto> programOutlineResponseDtoList =
			programService.convertToProgramOutlineResponseDtoList(programPage.getContent(), currentUser);
		return new ProgramCreatedResponseDto(programOutlineResponseDtoList, pageInfoDto);
	}

	@Transactional(readOnly = true)
	public ProgramListResponseDto findProgramLiked(int page) {
		Member currentUser = programService.getCurrentUser();
		Page<Program> programPage = programRepository.findAllProgramLiked(currentUser, PageRequest.of(page, PAGE_SIZE));
		PageInfoDto pageInfoDto = PageInfoDto.from(programPage);
		List<ProgramOutlineResponseDto> programOutlineResponseDtoList =
			programService.convertToProgramOutlineResponseDtoList(programPage.getContent(), currentUser);
		return new ProgramListResponseDto(programOutlineResponseDtoList, pageInfoDto);
	}
}
