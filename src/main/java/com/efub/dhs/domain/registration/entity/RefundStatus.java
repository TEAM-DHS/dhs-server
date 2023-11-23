package com.efub.dhs.domain.registration.entity;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RefundStatus {
	NONE("해당없음"),
	REQUESTED("환불요청"),
	COMPLETED("환불완료");

	private final String name;

	public static RefundStatus from(String name) {
		return Arrays.stream(RefundStatus.values())
			.filter(refundStatus -> refundStatus.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid RefundStatus name: " + name));
	}

	public static String to(RefundStatus refundStatus) {
		return refundStatus.name;
	}
}
