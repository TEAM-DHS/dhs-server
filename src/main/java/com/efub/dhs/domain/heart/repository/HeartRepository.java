package com.efub.dhs.domain.heart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.heart.entity.Heart;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;

public interface HeartRepository extends JpaRepository<Heart, Long> {

	Boolean existsByMemberAndProgram(Member member, Program program);

	Optional<Heart> findByMemberAndProgram(Member member, Program program);
}
