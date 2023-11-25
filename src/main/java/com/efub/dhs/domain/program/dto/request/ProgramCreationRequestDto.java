package com.efub.dhs.domain.program.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramCreationRequestDto {

	@NotBlank
	private String title;
	@NotBlank
	private String category;
	@NotNull
	private LocalDateTime schedule;
	@NotBlank
	private String location;
	@NotBlank
	private String postalCode;
	@NotNull
	private LocalDateTime deadline;
	@NotNull
	private int targetNumber;
	@NotBlank
	private String content;
	@NotBlank
	private String depositAccount;
	@NotBlank
	private String depositBank;
	@NotBlank
	private String depositName;
	@NotBlank
	private String price;
	@NotBlank
	private String hostName;
	@NotBlank
	private String hostDescription;

	public Program toEntity(Member member) {
		return Program.builder()
			.host(member)
			.title(title)
			.category(Category.from(category))
			.schedule(schedule)
			.location(location)
			.postalCode(postalCode)
			.deadline(deadline)
			.targetNumber(targetNumber)
			.content(content)
			.depositAccount(depositAccount)
			.depositBank(depositBank)
			.depositName(depositName)
			.price(price)
			.hostName(hostName)
			.hostDescription(hostDescription)
			.build();
	}
}
