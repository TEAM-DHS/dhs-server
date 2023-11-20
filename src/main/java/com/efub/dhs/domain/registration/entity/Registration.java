package com.efub.dhs.domain.registration.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Registration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id")
	private Long registrationId;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false)
	private Program program;

	@Column(name = "registrant_name", length = 32, nullable = false)
	private String registrantName;

	@Column(name = "registrant_phone", length = 32, nullable = false)
	private String registrantPhone;

	@Column(name = "deposit_check", nullable = false)
	private Boolean depositCheck;

	@Column(name = "deposit_name", length = 50, nullable = false)
	private String depositName;

	@Column(name = "deposit_date", nullable = false)
	private LocalDateTime depositDate;

	@Column(name = "deposit_amount", length = 16, nullable = false)
	private String depositAmount;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private RefundStatus refundStatus;

	@Column(name = "refund_bank", length = 32, nullable = false)
	private String refundBank;

	@Column(name = "refund_account", length = 50, nullable = false)
	private String refundAccount;

	@Column(name = "refund_name", length = 50, nullable = false)
	private String refundName;

	@Builder
	public Registration(Member member, Program program, String registrantName, String registrantPhone,
		String depositName, LocalDateTime depositDate, String depositAmount,
		String refundBank, String refundAccount, String refundName) {
		this.member = member;
		this.program = program;
		this.registrantName = registrantName;
		this.registrantPhone = registrantPhone;
		this.depositCheck = false;
		this.depositName = depositName;
		this.depositDate = depositDate;
		this.depositAmount = depositAmount;
		this.refundStatus = RefundStatus.NONE;
		this.refundBank = refundBank;
		this.refundAccount = refundAccount;
		this.refundName = refundName;
	}
}
