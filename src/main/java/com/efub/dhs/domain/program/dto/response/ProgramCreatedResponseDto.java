package com.efub.dhs.domain.program.dto.response;

import java.util.List;

import com.efub.dhs.domain.program.dto.PageInfoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramCreatedResponseDto {

	private List<ProgramOutlineResponseDto> programs;
	private PageInfoDto pageInfo;
}
