package com.efub.dhs.domain.program.dto;

import java.time.LocalDateTime;

import com.efub.dhs.domain.program.entity.Notice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeDto {
	private Long noticeId;
	private String title;
	private String content;
	private LocalDateTime createdDate;

	public NoticeDto(Notice notice) {
		this.noticeId = notice.getNoticeId();
		this.title = notice.getTitle();
		this.content = notice.getContent();
		this.createdDate = notice.getCreatedDate();
	}
}
