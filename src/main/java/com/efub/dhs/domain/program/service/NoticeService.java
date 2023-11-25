package com.efub.dhs.domain.program.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.efub.dhs.domain.member.entity.Member;
import com.efub.dhs.domain.member.service.MemberService;
import com.efub.dhs.domain.program.dto.request.NoticeCreationRequestDto;
import com.efub.dhs.domain.program.dto.response.NoticeCreationResponseDto;
import com.efub.dhs.domain.program.entity.Notice;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberService memberService;

	public NoticeCreationResponseDto createNotice(Program program, NoticeCreationRequestDto requestDto) {
		Member member = memberService.getCurrentUser();
		if (member.equals(program.getHost())) {
			Notice notice = noticeRepository.save(requestDto.toEntity(program));
			return new NoticeCreationResponseDto(notice.getNoticeId());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
}
