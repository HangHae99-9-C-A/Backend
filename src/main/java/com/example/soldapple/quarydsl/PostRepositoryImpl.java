package com.example.soldapple.quarydsl;

import com.example.soldapple.like.entity.QLike;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.dto.QPostResponseDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.soldapple.like.entity.QLike.like;
import static com.example.soldapple.post.entity.QPost.post;

@Component
//@Primary
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findAllMyLikes(Member mem) {
        QPost qPost = QPost.post;
        QLike qLike = QLike.like;

        return queryFactory
                .select(post)
                .from(post)
                .join(post.like, like).on(like.member.eq(mem))
                .fetch();
    }

    @Override
    public Page<PostResponseDto> findMyQuery(Pageable pageable) {

        QPost qPost = QPost.post;

        List<PostResponseDto> postList = queryFactory
                .select(new QPostResponseDto(post))
                .from(post)
                .orderBy(post.postId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post);


 //       return new PageImpl<>(postList, pageable, 0);
        return PageableExecutionUtils.getPage(postList,pageable,countQuery::fetchOne);
    }

}
