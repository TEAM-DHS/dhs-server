package com.efub.dhs.domain.program.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.Registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramRegistrationRequestDto {

	private String depositorName;
	private String depositAmount;
	@PastOrPresent
	private LocalDateTime depositDate;
	
	@NotBlank
	private String registrantName;
	@NotBlank
	private String registrantPhone;

	private String refundBank;
	private String refundAccount;
	private String refundName;

	public Registration toEntity(Member member, Program program) {
		return Registration.builder()
			.member(member)
			.program(program)
			.registrantName(registrantName)
			.registrantPhone(registrantPhone)
			.depositName(depositorName)
			.depositDate(depositDate)
			.depositAmount(depositAmount)
			.refundBank(refundBank)
			.refundAccount(refundAccount)
			.refundName(refundName)
			.build();
	}
}
