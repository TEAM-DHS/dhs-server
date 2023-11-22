package com.efub.dhs.domain.registration.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RefundStatus {
	NONE("해당없음"),
	REQUESTED("환불요청"),
	COMPLETED("환불완료");

	private final String name;
}
