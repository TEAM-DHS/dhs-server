package com.efub.dhs.domain.program.dto.response;

import java.util.List;

import com.efub.dhs.domain.program.dto.GoalDto;
import com.efub.dhs.domain.program.dto.PageInfoDto;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.registration.entity.Registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramRegisteredResponseDto {

	private List<ProgramOutlineWithRegistrationDto> programs;
	private PageInfoDto pageInfo;

	@Getter
	public static class ProgramOutlineWithRegistrationDto extends ProgramOutlineResponseDto {
		private final ProgramRegistrationResponseDto registrationInfoDto;

		public ProgramOutlineWithRegistrationDto(
			Program program, Integer remainingDays, GoalDto goal, Boolean hasLike, Registration registration) {
			super(program, remainingDays, goal, hasLike);
			this.registrationInfoDto = ProgramRegistrationResponseDto.from(registration);
		}
	}
}
