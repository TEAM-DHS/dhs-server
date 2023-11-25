package com.efub.dhs.domain.program.repository;

import static com.efub.dhs.domain.program.entity.QProgram.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.efub.dhs.domain.program.dto.request.ProgramListRequestDto;
import com.efub.dhs.domain.program.entity.Category;
import com.efub.dhs.domain.program.entity.Program;
import com.efub.dhs.domain.program.entity.Sort;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProgramRepositoryImpl implements ProgramRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Program> findProgramListByFilter(ProgramListRequestDto requestDto, Pageable pageable) {
		List<Program> programList;

		OrderSpecifier<Long> defaultSpecifier = program.programId.asc();
		OrderSpecifier<LocalDateTime> newSpecifier = program.createdDate.desc();
		OrderSpecifier<Long> popularSpecifier = program.likeNumber.desc();
		OrderSpecifier<LocalDateTime> deadlineSpecifier = program.deadline.asc();

		JPAQuery<Program> foundQuery = findQuery(requestDto);

		Sort sort = requestDto.getSort();
		if (sort == null) {
			programList = foundQuery.orderBy(defaultSpecifier).fetch();
		} else if (sort.equals(Sort.NEW)) {
			programList = foundQuery.orderBy(newSpecifier).fetch();
		} else if (sort.equals(Sort.POPULAR)) {
			programList = foundQuery.orderBy(popularSpecifier).fetch();
		} else if (sort.equals(Sort.DEADLINE)) {
			programList = foundQuery.orderBy(deadlineSpecifier).fetch();
		} else {
			throw new RuntimeException("행사를 찾을 수 없습니다.");
		}

		int page = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int offset = page * size;
		int total = programList.size();
		int end = Math.min(offset + size, total);

		return new PageImpl<>(programList.subList(offset, end), pageable, total);
	}

	private JPAQuery<Program> findQuery(ProgramListRequestDto requestDto) {
		return queryFactory
			.selectFrom(program)
			.where(
				program.isOpen.eq(true),
				keywordCondition(requestDto.getKeyword()),
				categoryCondition(requestDto.getCategory())
			);
	}

	private BooleanExpression keywordCondition(String keyword) {
		if (keyword != null) {
			return program.title.containsIgnoreCase(keyword).or(program.content.containsIgnoreCase(keyword));
		}
		return null;
	}

	private BooleanExpression categoryCondition(List<Category> categoryList) {
		if (categoryList != null && !categoryList.isEmpty()) {
			return program.category.in(categoryList);
		}
		return null;
	}

}
