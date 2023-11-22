package com.efub.dhs.domain.program.dto.response;

import java.util.List;

import com.efub.dhs.domain.program.dto.GoalDto;
import com.efub.dhs.domain.program.dto.PageInfoDto;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.RefundStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramRegisteredResponseDto {

	private List<ProgramOutlineWithStatusDto> programs;
	private PageInfoDto pageInfo;

	public static class ProgramOutlineWithStatusDto extends ProgramOutlineResponseDto {
		private final RefundStatus refundStatus;

		public ProgramOutlineWithStatusDto(
			Program program, Integer remainingDays, GoalDto goal, Boolean hasLike, RefundStatus refundStatus) {
			super(program, remainingDays, goal, hasLike);
			this.refundStatus = refundStatus;
		}
	}
}
