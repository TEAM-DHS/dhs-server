package com.efub.dhs.global.jwt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class JwtToken {

	@Id
	private String username;

	@Indexed
	private String accessToken;

	private String refreshToken;

	public void updateAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
