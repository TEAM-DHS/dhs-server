package com.efub.dhs.domain.program.dto.request;

import javax.validation.constraints.NotBlank;

import com.efub.dhs.domain.program.entity.Notice;
import com.efub.dhs.domain.program.entity.Program;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeCreationRequestDto {

	@NotBlank
	private String title;
	@NotBlank
	private String content;

	public Notice toEntity(Program program) {
		return Notice.builder()
			.program(program)
			.title(title)
			.content(content)
			.build();
	}
}
