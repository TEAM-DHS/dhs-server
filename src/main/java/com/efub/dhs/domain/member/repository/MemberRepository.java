package com.efub.dhs.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByUsername(String username);

	Optional<Member> findByUsername(String username);
}
