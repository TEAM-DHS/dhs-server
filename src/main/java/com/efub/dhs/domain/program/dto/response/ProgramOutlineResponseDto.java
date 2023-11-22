package com.efub.dhs.domain.program.dto.response;

import com.efub.dhs.domain.program.dto.GoalDto;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramOutlineResponseDto {
	protected Long programId;
	protected String title;
	protected Category category;
	protected String thumbnailImage;
	protected Integer remainingDays;
	protected Boolean isOpen;
	protected GoalDto goal;
	protected String content;
	protected Boolean hasLike;

	public ProgramOutlineResponseDto(Program program, Integer remainingDays, GoalDto goal, Boolean hasLike) {
		this.programId = program.getProgramId();
		this.title = program.getTitle();
		this.category = program.getCategory();
		this.thumbnailImage = program.getImages().get(0).getUrl();
		this.remainingDays = remainingDays;
		this.isOpen = program.getIsOpen();
		this.goal = goal;
		this.content = program.getContent();
		this.hasLike = hasLike;
	}
}
