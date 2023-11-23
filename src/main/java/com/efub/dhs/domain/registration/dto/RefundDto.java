package com.efub.dhs.domain.registration.dto;

import com.efub.dhs.domain.registration.entity.RefundStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundDto {

	private String status;
	private String bank;
	private String account;
	private String name;

	public RefundDto(RefundStatus refundStatus, String bank, String account, String name) {
		this.status = RefundStatus.to(refundStatus);
		this.bank = bank;
		this.account = account;
		this.name = name;
	}
}
