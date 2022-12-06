package com.example.soldapple.quarydsl.issues;

import com.example.soldapple.issues.dto.ResponseDto.QIssuesResponseDto;
import com.example.soldapple.issues.entity.QIssues;
import com.example.soldapple.issues.responsedto.IssuesResponseDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.soldapple.issues.entity.QIssues.issues;

@Component
//@Primary
public class IssuesRepositoryImpl implements IssuesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IssuesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //전체 이의제기글 + 정렬
    @Override
    public Page<IssuesResponseDto> findAllMyIssues(Pageable pageable) {
        QIssues qIssues = issues;

        JPAQuery<IssuesResponseDto> query = queryFactory
                .select(new QIssuesResponseDto(issues))
                .from(issues)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        // sorting
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(issues.getType(), issues.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }
        List<IssuesResponseDto> list = query.fetch();
        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    //전체 이의제기글 + 검색 + 정렬
    @Override
    public Page<IssuesResponseDto> findAllMyIssuesWithSearch(Pageable pageable, String searchReceived) {


        JPAQuery<IssuesResponseDto> query = queryFactory
                .select(new QIssuesResponseDto(issues))
                .from(issues)
                .where(issues.issuesTitle.contains(searchReceived).or(issues.issuesContent.contains(searchReceived)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        // sorting
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(issues.getType(), issues.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }
        List<IssuesResponseDto> list = query.fetch();
        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    //카테고리별 이의제기글 + 정렬
    @Override
    public Page<IssuesResponseDto> findAllIssuesWithCategory(Pageable pageable, String categoryReceived) {
        JPAQuery<IssuesResponseDto> query = queryFactory.
                select(new QIssuesResponseDto(issues))
                .from(issues)
                .where(issues.category.eq(categoryReceived))
                .orderBy(issues.issuesId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
// sorting
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(issues.getType(), issues.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }
        List<IssuesResponseDto> list = query.fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    //카테고리 + 검색 + 정렬
    @Override
    public Page<?> findAllIssuesWithCategoryWithSearch(Pageable pageable, String categoryReceived, String
            searchReceived) {

        JPAQuery<IssuesResponseDto> query = queryFactory
                .select(new QIssuesResponseDto(issues))
                .from(issues)
                .where(issues.category.eq(categoryReceived)
                        .and(issues.issuesTitle.contains(searchReceived))
                        .or(issues.issuesContent.contains(searchReceived)))
                .orderBy()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // sorting
        Sort sort = pageable.getSort();
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(issues.getType(), issues.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<IssuesResponseDto> list = query.fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(issues.count())
                .from(issues);

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

    }
}
