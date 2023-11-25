package com.efub.dhs.global.jwt.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Access Token."));
	}
}
