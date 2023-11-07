package com.efub.dhs.domain.program.dto;

import com.efub.dhs.domain.program.entity.ProgramImage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDto {
	private Long id;
	private String url;

	public ImageDto(ProgramImage image) {
		this.id = image.getImageId();
		this.url = image.getUrl();
	}
}
