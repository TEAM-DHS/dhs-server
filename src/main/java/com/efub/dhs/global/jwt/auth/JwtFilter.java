package com.efub.dhs.global.jwt.auth;

import static com.efub.dhs.global.jwt.utils.JwtUtils.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtAuthProvider jwtAuthProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = resolveToken(request);

		if (token != null && jwtAuthProvider.validateToken(token)) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			Authentication authentication = jwtAuthProvider.authenticate(token);
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			log.debug("[+] Token in SecurityContextHolder");
		}
		filterChain.doFilter(request, response);
	}
}
