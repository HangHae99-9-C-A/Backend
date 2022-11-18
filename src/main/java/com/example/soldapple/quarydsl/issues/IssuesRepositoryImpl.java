package com.example.soldapple.quarydsl.issues;

import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.dto.ResponseDto.QIssuesResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.QIssues;
import com.example.soldapple.member.entity.Member;

import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.dto.QPostResponseDto;
import com.example.soldapple.quarydsl.post.PostRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.soldapple.issues.entity.QIssues.issues;
import static com.example.soldapple.like.entity.QLike.like;

@Component
//@Primary
public class IssuesRepositoryImpl implements IssuesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IssuesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<IssuesResponseDto> findMyQuery(Pageable pageable) {
        QIssues qIssues = issues;

        List<IssuesResponseDto> postList = queryFactory
                .select(new QIssuesResponseDto(issues))
                .from(issues)
                .orderBy(issues.issuesId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    //카테고리별 이의제기글 get
    @Override
    public Page<IssuesResponseDto> findAllIssuesWithCategory(Pageable pageable, String categoryReceived) {
        List<IssuesResponseDto> list = queryFactory.
                select(new QIssuesResponseDto(issues))
                .from(issues)
                .where(issues.category.eq(categoryReceived))
                .orderBy(issues.issuesId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }
}
