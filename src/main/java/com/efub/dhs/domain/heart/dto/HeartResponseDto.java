package com.efub.dhs.domain.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartResponseDto {

	private Long programId;
	private Long heartId;
	private Boolean isExist;
}
