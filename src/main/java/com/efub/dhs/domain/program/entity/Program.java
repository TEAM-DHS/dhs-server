package com.efub.dhs.domain.program.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.global.entity.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Program extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "program_id")
	private Long programId;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member host;

	@Column(nullable = false, length = 50)
	private String title;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Column(nullable = false, name = "program_schedule")
	private LocalDateTime schedule;

	@Column(nullable = false)
	private String location;

	@Column(name = "postal_code", nullable = false, length = 16)
	private String postalCode;

	@Column(nullable = false)
	private LocalDateTime deadline;

	@Column(name = "is_open", nullable = false)
	private Boolean isOpen;

	@Column(name = "target_number", nullable = false)
	private Integer targetNumber;

	@Column(name = "registrant_number")
	private Integer registrantNumber;

	@Column(nullable = false, length = 5000)
	private String content;

	@Column(name = "like_number")
	private Long likeNumber;

	@Column(name = "deposit_bank", nullable = false, length = 16)
	private String depositBank;

	@Column(name = "deposit_name", nullable = false, length = 32)
	private String depositName;

	@Column(name = "deposit_account", nullable = false, length = 32)
	private String depositAccount;

	@Column(nullable = false, length = 16)
	private String price;

	@Column(name = "host_name", nullable = false, length = 32)
	private String hostName;

	@Column(name = "host_description", nullable = false)
	private String hostDescription;

	@OneToMany(mappedBy = "program")
	private List<ProgramImage> images;

	@OneToMany(mappedBy = "program")
	private List<Notice> notices;

	@Builder
	public Program(Member host, String title, Category category,
		LocalDateTime schedule, String location, String postalCode, LocalDateTime deadline,
		Boolean isOpen, Integer targetNumber, String content, String depositBank,
		String depositName, String depositAccount, String price, String hostName,
		String hostDescription, List<ProgramImage> images, List<Notice> notices) {
		this.host = host;
		this.title = title;
		this.category = category;
		this.schedule = schedule;
		this.location = location;
		this.postalCode = postalCode;
		this.deadline = deadline;
		this.isOpen = isOpen;
		this.targetNumber = targetNumber;
		this.registrantNumber = 0;
		this.content = content;
		this.likeNumber = 0L;
		this.depositAccount = depositAccount;
		this.depositBank = depositBank;
		this.depositName = depositName;
		this.price = price;
		this.hostDescription = hostDescription;
		this.hostName = hostName;
		this.images = images;
		this.notices = notices;
	}
}
