package com.efub.dhs.domain.program.dto.response;

import com.efub.dhs.domain.program.dto.request.ProgramRegistrationRequestDto;
import com.efub.dhs.domain.registration.entity.Registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramRegistrationResponseDto {

	private long registrationId;
	private ProgramRegistrationRequestDto registrationInfo;

	public static ProgramRegistrationResponseDto from(Registration registration) {
		return new ProgramRegistrationResponseDto(
			registration.getRegistrationId(),
			ProgramRegistrationRequestDto.builder()
				.depositorName(registration.getDepositName())
				.depositAmount(registration.getDepositAmount())
				.depositDate(registration.getDepositDate())
				.registrantName(registration.getRegistrantName())
				.registrantPhone(registration.getRegistrantPhone())
				.refundBank(registration.getRefundBank())
				.refundAccount(registration.getRefundAccount())
				.refundName(registration.getRefundName())
				.build()
		);
	}
}
