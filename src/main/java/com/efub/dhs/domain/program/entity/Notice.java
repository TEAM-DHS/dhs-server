package com.efub.dhs.domain.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efub.dhs.global.entity.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notice extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long noticeId;

	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false)
	private Program program;

	@Column(length = 32, nullable = false)
	private String title;

	@Column(length = 1000, nullable = false)
	private String content;

	@Builder
	public Notice(Program program, String title, String content) {
		this.program = program;
		this.title = title;
		this.content = content;
	}
}
