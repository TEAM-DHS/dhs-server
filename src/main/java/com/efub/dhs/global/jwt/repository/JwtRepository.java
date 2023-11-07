package com.efub.dhs.global.jwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.efub.dhs.global.jwt.entity.JwtToken;

public interface JwtRepository extends CrudRepository<JwtToken, String> {

	Optional<JwtToken> findByAccessToken(String accessToken);
}
