package com.efub.dhs.domain.heart.service;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.efub.dhs.domain.heart.dto.HeartResponseDto;
import com.efub.dhs.domain.heart.entity.Heart;
import com.efub.dhs.domain.heart.repository.HeartRepository;
import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.MemberService;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

	private final HeartRepository heartRepository;
	private final ProgramRepository programRepository;
	private final MemberService memberService;

	private Heart getHeart(Member member, Program program) {
		return heartRepository.findByMemberAndProgram(member, program)
			.orElseThrow(() -> new IllegalArgumentException("이 회원은 이 행사를 신청하지 않았습니다."));
	}

	@Transactional(readOnly = true)
	public Boolean existsByMemberAndProgram(Member member, Program program) {
		if (heartRepository.existsByMemberAndProgram(member, program)) {
			Heart heart = getHeart(member, program);
			return heart.getExist();
		} else {
			return false;
		}
	}

	@Transactional
	public ResponseEntity<HeartResponseDto> heartProgram(Long programId) {
		Member currentUser = memberService.getCurrentUser();
		Program program = programRepository.findById(programId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 행사를 찾을 수 없습니다."));
		Heart heart = getOrCreateHeart(currentUser, program);
		return convertHeartExist(program, heart);
	}

	private ResponseEntity<HeartResponseDto> convertHeartExist(Program program, Heart heart) {
		Long programId = program.getProgramId();
		// 좋아요 생성
		if (Boolean.FALSE.equals(heart.getExist())) {
			heart.activateHeart();
			heartRepository.save(heart);
			program.increaseLikeNumber();
			String location = "/programs/" + programId;
			return ResponseEntity
				.created(URI.create(location))
				.body(new HeartResponseDto(programId, heart.getHeartId(), heart.getExist()));
		}
		// 좋아요 취소
		heart.deactivateHeart();
		heartRepository.save(heart);
		program.decreaseLikeNumber();
		return ResponseEntity
			.ok()
			.body(new HeartResponseDto(programId, heart.getHeartId(), heart.getExist()));
	}

	private Heart getOrCreateHeart(Member member, Program program) {
		if (heartRepository.existsByMemberAndProgram(member, program)) {
			return getHeart(member, program);
		} else {
			// 좋아요가 아직 없는 경우 새로 생성
			return Heart.builder()
				.member(member)
				.program(program)
				.build();
		}
	}
}
