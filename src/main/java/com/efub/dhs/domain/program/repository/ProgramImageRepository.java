package com.efub.dhs.domain.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.entity.ProgramImage;

public interface ProgramImageRepository extends JpaRepository<ProgramImage, Long> {

	List<ProgramImage> findAllByProgram(Program program);
}
