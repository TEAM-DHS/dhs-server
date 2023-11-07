package com.efub.dhs.domain.member.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.repository.MemberRepository;
import com.efub.dhs.global.jwt.auth.JwtAuthProvider;
import com.efub.dhs.global.jwt.entity.JwtToken;
import com.efub.dhs.global.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtAuthProvider jwtAuthProvider;
	private final JwtService jwtService;

	public Member signUp(String username, String password) {
		boolean exists = memberRepository.existsByUsername(username);
		if (exists) {
			throw new IllegalArgumentException("동일한 아이디의 유저가 존재합니다.");
		}
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.builder().username(username).password(encodedPassword).build();
		return memberRepository.save(member);
	}

	public JwtToken logIn(String username, String password) {
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(username, password);
		Authentication authenticated = authenticationManagerBuilder.getObject().authenticate(authentication);
		JwtToken jwtToken = jwtAuthProvider.generateJwtToken(authenticated);
		return jwtService.saveJwtToken(jwtToken);
	}
}
