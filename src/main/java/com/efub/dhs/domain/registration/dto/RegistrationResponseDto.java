package com.efub.dhs.domain.registration.dto;

import com.efub.dhs.domain.registration.entity.Registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationResponseDto {

	private String name;
	private String phone;
	private PaymentDto payment;
	private RefundDto refund;

	public static RegistrationResponseDto from(Registration registration) {
		return new RegistrationResponseDto(
			registration.getRegistrantName(),
			registration.getRegistrantPhone(),
			new PaymentDto(registration.getDepositCheck(), registration.getDepositName(),
				registration.getDepositDate(), registration.getDepositAmount()),
			new RefundDto(registration.getRefundStatus(), registration.getRefundBank(),
				registration.getRefundAccount(), registration.getRefundName())
		);
	}
}
