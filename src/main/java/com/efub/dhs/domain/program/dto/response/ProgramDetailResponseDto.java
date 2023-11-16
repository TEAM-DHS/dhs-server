package com.efub.dhs.domain.program.dto.response;

import java.util.List;

import com.efub.dhs.domain.program.dto.ProgramDto;
import com.efub.dhs.domain.program.dto.ProgramMemberDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramDetailResponseDto {
	private ProgramDto program;
	private ProgramMemberDto member;
	private List<ProgramOutlineResponseDto> otherPrograms;

	public ProgramDetailResponseDto(ProgramDto program, ProgramMemberDto member,
		List<ProgramOutlineResponseDto> otherPrograms) {
		this.program = program;
		this.member = member;
		this.otherPrograms = otherPrograms;
	}
}
