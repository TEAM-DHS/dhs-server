package com.efub.dhs.domain.program.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostDto {
	private String name;
	private String description;

	public HostDto(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
