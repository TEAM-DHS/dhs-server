package com.efub.dhs.domain.program.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramMemberDto {
	private boolean hasLike;
	private boolean hasRegistration;

	public ProgramMemberDto(Boolean hasLike, Boolean hasRegistration) {
		this.hasLike = hasLike;
		this.hasRegistration = hasRegistration;
	}
}
