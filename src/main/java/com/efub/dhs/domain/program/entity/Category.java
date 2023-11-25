package com.efub.dhs.domain.program.entity;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
	PLAY("공연"),
	EXHIBIT("전시"),
	ACADEMIC("학술"),
	BAR("주점"),
	ETC("기타");

	private final String name;

	public static Category from(String name) {
		return Arrays.stream(Category.values())
			.filter(category -> category.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Category name: " + name));
	}

	public static String to(Category category) {
		return category.name;
	}
}
