package com.efub.dhs.global.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member signUp(String username, String password) {
		boolean exists = memberRepository.existsByUsername(username);
		if (exists) {
			throw new IllegalArgumentException("동일한 아이디의 유저가 존재합니다.");
		}
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.builder().username(username).password(encodedPassword).build();
		return memberRepository.save(member);
	}
}
