package com.efub.dhs.global.jwt.service;

import org.springframework.stereotype.Service;

import com.efub.dhs.global.jwt.entity.JwtToken;
import com.efub.dhs.global.jwt.repository.JwtRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtRepository jwtRepository;

	public JwtToken saveJwtToken(JwtToken jwtToken) {
		return jwtRepository.save(jwtToken);
	}

	public void removeJwtToken(String accessToken) {
		JwtToken jwtToken = jwtRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new SecurityException("JWT token is invalid."));
		jwtRepository.delete(jwtToken);
	}
}
