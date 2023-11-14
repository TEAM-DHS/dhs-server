package com.efub.dhs.domain.heart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.dhs.domain.heart.entity.Heart;
import com.efub.dhs.domain.heart.repository.HeartRepository;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.program.entity.Program;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

	private final HeartRepository heartRepository;

	@Transactional(readOnly = true)
	public Boolean existsByMemberAndProgram(Member member, Program program) {
		if (heartRepository.existsByMemberAndProgram(member, program)) {
			Heart heart = heartRepository.findByMemberAndProgram(member, program)
				.orElseThrow(() -> new IllegalArgumentException("이 회원은 이 행사를 신청하지 않았습니다."));
			return heart.getExist();
		} else {
			return false;
		}
	}
}
