package com.efub.dhs.domain.program.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramRegistryRequestDto {

	private String title;
	private String category;
	private LocalDateTime schedule;
	private String location;
	private String postalCode;
	private LocalDateTime deadline;
	private int targetNumber;
	private String content;
	private String depositAccount;
	private String depositBank;
	private String depositName;
	private String price;
	private String hostName;
	private String hostDescription;
	private List<String> images;

	public Program toEntity(Member member) {
		return Program.builder()
			.host(member)
			.title(title)
			.category(Category.valueOf(category))
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
			.imageUrlList(images)
			.build();
	}
}
