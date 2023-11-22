package com.efub.dhs.domain.program.dto.request;

import java.util.List;

import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Sort;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramListRequestDto {
	private String keyword;
	private Sort sort;
	private List<Category> category;
}
