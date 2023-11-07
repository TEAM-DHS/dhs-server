package com.efub.dhs.domain.program.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramDto {
	private Long programId;
	private String title;
	private Category category;
	private String thumbnailImage;
	private LocalDateTime schedule;
	private String postalCode;
	private String location;
	private LocalDateTime deadline;
	private Integer remainingDays;
	private Boolean isOpen;
	private GoalDto goal;
	private Long likeNumber;
	private String content;
	private List<ImageDto> contentImages;
	private String depositInfo;
	private String price;
	private List<NoticeDto> notices;
	private HostDto host;

	public ProgramDto(Program program, Integer remainingDays, GoalDto goal, List<ImageDto> contentImages,
		String depositInfo, List<NoticeDto> notices, HostDto host) {
		this.programId = program.getProgramId();
		this.title = program.getTitle();
		this.category = program.getCategory();
		this.thumbnailImage = program.getThumbnailImage();
		this.schedule = program.getSchedule();
		this.postalCode = program.getPostalCode();
		this.location = program.getLocation();
		this.deadline = program.getDeadline();
		this.remainingDays = remainingDays;
		this.isOpen = program.getIsOpen();
		this.goal = goal;
		this.likeNumber = program.getLikeNumber();
		this.content = program.getContent();
		this.contentImages = contentImages;
		this.depositInfo = depositInfo;
		this.price = program.getPrice();
		this.notices = notices;
		this.host = host;
	}
}
