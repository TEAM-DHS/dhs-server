package com.efub.dhs.domain.registration.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationModificationRequestDto {

	private String registrantName;
	private String registrantPhone;
	private String refundBank;
	private String refundAccount;
	private String refundName;
	private String refundStatus;
}
