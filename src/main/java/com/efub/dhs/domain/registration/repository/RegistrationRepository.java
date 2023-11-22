package com.efub.dhs.domain.registration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Boolean existsByMemberAndProgram(Member member, Program program);

	@Query(value = "select r from Registration r join fetch Program p on r.program=p where r.member=?1")
	Page<Registration> findRegisteredPrograms(Member registrant, Pageable pageable);
}
