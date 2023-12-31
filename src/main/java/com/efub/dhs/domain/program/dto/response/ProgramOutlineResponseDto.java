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
	private Long programId;
	private String title;
	private String category;
	private String thumbnailImage;
	private Integer remainingDays;
	private Long likeNumber;
	private Boolean isOpen;
	private GoalDto goal;
	private String content;
	private Boolean hasLike;

	public ProgramOutlineResponseDto(Program program, Integer remainingDays, GoalDto goal, Boolean hasLike) {
		this.programId = program.getProgramId();
		this.title = program.getTitle();
		this.category = Category.to(program.getCategory());
		this.thumbnailImage = program.getImages().get(0).getUrl();
		this.remainingDays = remainingDays;
		this.likeNumber = program.getLikeNumber();
		this.isOpen = program.getIsOpen();
		this.goal = goal;
		this.content = program.getContent();
		this.hasLike = hasLike;
	}
}
