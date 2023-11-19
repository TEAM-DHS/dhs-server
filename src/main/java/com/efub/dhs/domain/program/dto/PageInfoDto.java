package com.efub.dhs.domain.program.dto;

import org.springframework.data.domain.Page;

import com.efub.dhs.domain.program.entity.Program;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfoDto {

	private final int pageNum;
	private final int pageSize;
	private final long totalElements;
	private final int totalPages;

	@Builder
	private PageInfoDto(int pageNum, int pageSize, long totalElements, int totalPages) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

	public static PageInfoDto from(Page<Program> page) {
		return PageInfoDto.builder()
			.pageNum(page.getNumber())
			.pageSize(page.getSize())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.build();
	}
}
