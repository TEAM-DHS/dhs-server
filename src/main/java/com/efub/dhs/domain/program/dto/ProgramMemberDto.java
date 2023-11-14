package com.efub.dhs.domain.program.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramMemberDto {
	private Boolean hasLike;
	private Boolean hasRegistration;
	private Boolean isHost;

	public ProgramMemberDto(Boolean hasLike, Boolean hasRegistration, Boolean isHost) {
		this.hasLike = hasLike;
		this.hasRegistration = hasRegistration;
		this.isHost = isHost;
	}
}
