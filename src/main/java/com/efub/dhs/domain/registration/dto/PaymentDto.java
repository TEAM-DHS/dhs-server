package com.efub.dhs.domain.registration.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDto {

	private Boolean check;
	private String name;
	private LocalDateTime date;
	private String price;

	public PaymentDto(Boolean check, String name, LocalDateTime date, String price) {
		this.check = check;
		this.name = name;
		this.date = date;
		this.price = price;
	}
}
