package com.efub.dhs.domain.program.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
	PLAY("연극"),
	MUSICAL("뮤지컬"),
	EXHIBIT("전시"),
	ACADEMIC("학술"),
	BAR("주점"),
	ETC("기타");

	private final String name;
}
