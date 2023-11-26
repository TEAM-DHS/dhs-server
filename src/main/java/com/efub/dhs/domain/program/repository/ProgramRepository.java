package com.efub.dhs.domain.program.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramRepositoryCustom {

	List<Program> findTop3ByCategoryAndIsOpenAndDeadlineAfterAndProgramIdNotOrderByDeadlineAsc(Category category,
		Boolean isOpen, LocalDateTime deadline, Long programId);

	Page<Program> findAllByHost(Member host, Pageable pageable);

	@Query(value = "select p from Program p join fetch Heart h on h.program=p where h.member=?1")
	Page<Program> findAllProgramLiked(Member member, Pageable pageable);

	List<Program> findTop5ByIsOpenAndDeadlineAfterOrderByLikeNumberDesc(Boolean isOpen, LocalDateTime deadline);
}
