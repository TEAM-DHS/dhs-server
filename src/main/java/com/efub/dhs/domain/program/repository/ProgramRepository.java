package com.efub.dhs.domain.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {

	//List<Program> findTop3ByCategoryAndScheduleMonth(Category category, Month month);
	List<Program> findTop3ByCategory(Category category);
}
