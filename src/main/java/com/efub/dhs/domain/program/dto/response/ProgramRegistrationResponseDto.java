package com.efub.dhs.domain.program.dto.response;

import java.time.LocalDateTime;

import com.efub.dhs.domain.registration.entity.RefundStatus;
import com.efub.dhs.domain.registration.entity.Registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramRegistrationResponseDto {

	private long registrationId;
	private String depositorName;
	private String depositAmount;
	private LocalDateTime depositDate;
	private String registrantName;
	private String registrantPhone;
	private String refundBank;
	private String refundAccount;
	private String refundName;
	private String refundStatus;

	public static ProgramRegistrationResponseDto from(Registration registration) {
		return ProgramRegistrationResponseDto.builder()
			.registrationId(registration.getRegistrationId())
			.depositorName(registration.getDepositName())
			.depositAmount(registration.getDepositAmount())
			.depositDate(registration.getDepositDate())
			.registrantName(registration.getRegistrantName())
			.registrantPhone(registration.getRegistrantPhone())
			.refundBank(registration.getRefundBank())
			.refundAccount(registration.getRefundAccount())
			.refundName(registration.getRefundName())
			.refundStatus(RefundStatus.to(registration.getRefundStatus()))
			.build();
	}
}
