package com.efub.dhs.domain.member.service;

import static com.efub.dhs.global.jwt.utils.JwtUtils.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.repository.MemberRepository;
import com.efub.dhs.global.feign.client.KakaoTokenClient;
import com.efub.dhs.global.feign.client.KakaoUserInfoClient;
import com.efub.dhs.global.feign.dto.request.KakaoTokenRequestDto;
import com.efub.dhs.global.feign.dto.response.KakaoTokenResponseDto;
import com.efub.dhs.global.feign.dto.response.KakaoUserInfoResponseDto;
import com.efub.dhs.global.jwt.auth.JwtAuthProvider;
import com.efub.dhs.global.jwt.entity.JwtToken;
import com.efub.dhs.global.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

	private final KakaoTokenClient kakaoTokenClient;
	private final KakaoUserInfoClient kakaoUserInfoClient;
	private final MemberRepository memberRepository;
	private final JwtAuthProvider jwtAuthProvider;
	private final JwtService jwtService;

	@Value("${kakao.client-id}")
	private String clientId;

	public JwtToken signUp(String redirectUri, String code) {
		String kakaoToken = getKakaoToken(redirectUri, code);
		String username = getKakaoUsername(kakaoToken);
		log.debug("[KAKAO] 회원 ID: {}", username);
		createMember(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
			username, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
		JwtToken jwtToken = jwtAuthProvider.generateJwtToken(authentication);
		return jwtService.saveJwtToken(jwtToken);
	}

	private String getKakaoToken(String redirectUri, String code) {
		KakaoTokenResponseDto tokenResponseDto = kakaoTokenClient.getToken(
			KakaoTokenRequestDto.builder()
				.grant_type("authorization_code")
				.client_id(clientId)
				.redirect_uri(redirectUri)
				.code(code)
				.build()
		);
		return tokenResponseDto.getAccessToken();
	}

	private String getKakaoUsername(String kakaoToken) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, BEARER + " " + kakaoToken);
		KakaoUserInfoResponseDto kakaoUserInfo = kakaoUserInfoClient.getUserInfo(httpHeaders);
		return String.valueOf(kakaoUserInfo.getId());
	}

	private void createMember(String username) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		if (optionalMember.isEmpty()) {
			Member member = Member.builder()
				.username(username)
				.build();
			member.convertToOAuthUser();
			memberRepository.save(member);
		}
	}
}
