package com.efub.dhs.domain.program.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.efub.dhs.domain.program.dto.request.ProgramListRequestDto;
import com.efub.dhs.domain.program.entity.Program;

public interface ProgramRepositoryCustom {

	Page<Program> findProgramListByFilter(ProgramListRequestDto requestDto, Pageable pageable);
}
