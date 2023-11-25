package com.efub.dhs.domain.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ProgramImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;

	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false)
	private Program program;

	@Column(nullable = false)
	private String url;

	@Builder
	public ProgramImage(Program program, String url) {
		this.program = program;
		this.url = url;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
}
