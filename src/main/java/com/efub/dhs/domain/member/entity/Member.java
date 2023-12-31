package com.efub.dhs.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(nullable = false, length = 32)
	private String username;

	@Column
	private String password;

	@Column(nullable = false)
	private Boolean isOAuthUser;

	@Builder
	public Member(String username, String password) {
		this.username = username;
		this.password = password;
		this.isOAuthUser = false;
	}

	public void convertToOAuthUser() {
		this.password = null;
		this.isOAuthUser = true;
	}
}
