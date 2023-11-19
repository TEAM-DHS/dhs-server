package com.efub.dhs.domain.program.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {

	//List<Program> findTop3ByCategoryAndScheduleMonth(Category category, Month month);
	List<Program> findTop3ByCategory(Category category);

	Page<Program> findAllByHost(Member host, Pageable pageable);

	@Query(value = "select p from Program p join fetch Heart h on h.program=p where h.member=?1")
	Page<Program> findAllProgramLiked(Member member, Pageable pageable);
}
