package com.efub.dhs.global.jwt.service;

import org.springframework.stereotype.Service;

import com.efub.dhs.global.jwt.auth.JwtAuthProvider;
import com.efub.dhs.global.jwt.entity.JwtToken;
import com.efub.dhs.global.jwt.repository.JwtRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtRepository jwtRepository;
	private final JwtAuthProvider jwtAuthProvider;

	public JwtToken saveJwtToken(JwtToken jwtToken) {
		return jwtRepository.save(jwtToken);
	}

	public void removeJwtToken(String accessToken) {
		JwtToken jwtToken = getJwtToken(accessToken);
		jwtRepository.delete(jwtToken);
	}

	public JwtToken refreshToken(String accessToken) {
		JwtToken jwtToken = getJwtToken(accessToken);
		String reissuedAccessToken = jwtAuthProvider.reissueAccessToken(jwtToken);
		jwtToken.updateAccessToken(reissuedAccessToken);
		saveJwtToken(jwtToken);
		return jwtToken;
	}

	private JwtToken getJwtToken(String accessToken) {
		return jwtRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new SecurityException("JWT token is invalid."));
	}
}
