package com.efub.dhs.domain.program.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Sort {

	NEW("최신순"),
	POPULAR("인기순"),
	DEADLINE("마감임박순");

	private final String description;
}
