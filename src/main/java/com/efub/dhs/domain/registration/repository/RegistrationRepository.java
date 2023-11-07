package com.efub.dhs.domain.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Boolean existsByMemberAndProgram(Member member, Program program);
}
