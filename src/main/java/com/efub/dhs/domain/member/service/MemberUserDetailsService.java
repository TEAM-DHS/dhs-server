package com.efub.dhs.domain.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return memberRepository.findByUsername(username)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException("User not found. username: " + username));
	}

	private UserDetails createUserDetails(Member member) {
		return User.builder()
			.username(member.getUsername())
			.password(member.getPassword())
			.authorities("USER")
			.build();
	}
}
