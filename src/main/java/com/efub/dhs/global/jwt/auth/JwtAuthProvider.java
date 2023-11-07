package com.efub.dhs.global.jwt.auth;

import static com.efub.dhs.global.jwt.utils.JwtUtils.*;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.efub.dhs.global.jwt.entity.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthProvider {

	private final SecretKey secretKey;

	public JwtAuthProvider(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public Authentication authenticate(String token) {
		Claims claims = parseClaims(secretKey, token);
		Collection<GrantedAuthority> authorities = getGrantedAuthorities(claims);
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		log.debug("User principal: {}", principal.getUsername());
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public JwtToken generateJwtToken(Authentication authentication) {
		long now = new Date(System.currentTimeMillis()).getTime();
		String accessToken = generateAccessToken(now, authentication.getName(), authentication.getAuthorities());
		String refreshToken = Jwts.builder()
			.expiration(new Date(now + REFRESH_TOKEN_EXPIRATION))
			.signWith(secretKey)
			.compact();
		return new JwtToken(authentication.getName(), accessToken, refreshToken);
	}

	private String generateAccessToken(long now, String subject, Collection<? extends GrantedAuthority> authorities) {
		String auth = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.subject(subject)
			.claim(AUTH, auth)
			.expiration(new Date(now + ACCESS_TOKEN_EXPIRATION))
			.signWith(secretKey)
			.compact();
	}

	public String reissueAccessToken(JwtToken token) {
		validateToken(token.getRefreshToken());

		Claims claims = parseClaims(secretKey, token.getAccessToken());
		Collection<GrantedAuthority> authorities = getGrantedAuthorities(claims);

		long now = new Date(System.currentTimeMillis()).getTime();
		return generateAccessToken(now, claims.getSubject(), authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			log.debug("Success: Token validation");
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT is empty.", e);
		}
		return false;
	}
}
