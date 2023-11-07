package com.efub.dhs.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.dhs.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByUsername(String username);
}
