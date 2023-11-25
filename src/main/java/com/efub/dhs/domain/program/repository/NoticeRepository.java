package com.efub.dhs.domain.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.program.entity.Notice;
import com.efub.dhs.domain.program.entity.Program;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findAllByProgram(Program program);
}
