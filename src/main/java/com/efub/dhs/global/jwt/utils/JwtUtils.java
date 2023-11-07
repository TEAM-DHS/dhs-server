package com.efub.dhs.global.jwt.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

	public static final String AUTH = "auth";
	public static final String BEARER = "Bearer";
	public static final Long ACCESS_TOKEN_EXPIRATION = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
	public static final Long REFRESH_TOKEN_EXPIRATION = Duration.ofDays(14).toMillis(); // 만료시간 2주

	public static Claims parseClaims(SecretKey secretKey, String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public static Collection<GrantedAuthority> getGrantedAuthorities(Claims claims) {
		if (claims.get(AUTH) == null) {
			throw new SecurityException("Unauthorized JWT Token");
		}
		return Arrays.stream(claims.get(AUTH).toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	public static String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !authorization.startsWith(BEARER)) {
			return null;
		}
		return authorization.split(" ")[1];
	}
}
