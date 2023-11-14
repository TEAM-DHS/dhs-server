package com.efub.dhs.domain.program.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoalDto {
	private Integer targetNumber;
	private Integer registrantNumber;
	private Double progressRate;

	public GoalDto(Integer targetNumber, Integer registrantNumber, Double progressRate) {
		this.targetNumber = targetNumber;
		this.registrantNumber = registrantNumber;
		this.progressRate = progressRate;
	}
}
