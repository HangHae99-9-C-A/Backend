package com.example.soldapple.quarydsl.post;

import com.example.soldapple.like.entity.QLike;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.dto.QPostResponseDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.entity.QPost;
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
        QPost qPost = post;
        QLike qLike = like;

        return queryFactory
                .select(post)
                .from(post)
                .join(post.likes, like).on(like.member.eq(mem))
                .fetch();
    }

    @Override
    public Page<PostResponseDto> findMyQuery(Pageable pageable) {

        QPost qPost = post;

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
        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }


    @Override
    public Page<?> findAllPostWithCategory(Pageable pageable, String categoryReceived, Member mem) {
        List<Tuple> list = queryFactory.
                select(post, like.likeId)
                .from(post)
                .where(post.category.eq(categoryReceived))
                .leftJoin(post.likes, like).on(like.member.eq(mem))
                .orderBy(post.postId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        //제 1안
//        List<PostResponseDto> collect = list.stream().map(r -> new PostResponseDto(r.get(post)) {
//                    public Long myLong = r.get(like.likeId);
//                }
//        ).collect(Collectors.toList());

        //제 2안
//        List<PostResponseDto> collect = list.stream().map(r -> new PostResponseDto(r.get(post))).collect(Collectors.toList());
//        list.stream().map(r-> {return (r.get(like.likeId)==null) ? false: true;}).collect(Collectors.toList());

        //제 3안
        List<PostResponseDto> collect = list.stream().map(r -> new PostResponseDto(r.get(post)){public Boolean myLikeCheck = r.get(like.likeId) != null;}).collect(Collectors.toList());
//        list.stream().map(r-> {return r.get(like.likeId) != null;}).collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(collect, pageable, countQuery::fetchOne);
    }

//    @Override
//    public Page<PostResponseDto> findAllPostWithCategory2(Pageable pageable, String categoryReceived, Member mem) {
//        QPost qPost = QPost.post;
//        // QMember qMember = QMember.member;
//        QLike qLike = QLike.like;
//
//        List<PostResponseDto> list = queryFactory.
//                select(new QPostResponseDto(post))
//                .from(post)
//                .where(post.category.eq(categoryReceived))
//                .leftJoin(post.likes, like).on(like.member.eq(mem))
//                .orderBy(post.postId.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
////        System.out.println("Tuple 성공");
////        list.stream().forEach(System.out::println);
//        JPAQuery<Long> countQuery = queryFactory
//                .select(post.count())
//                .from(post);
//
//        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
//    }

//    @Override
//    public Page<PostResponseDto> findAllPostWithCategory(Pageable pageable, String categoryReceived) {
//        QPost qPost = QPost.post;
//        // QMember qMember = QMember.member;
//        QLike qLike = QLike.like;
//
//        List<PostResponseDto> list = queryFactory.
//                select(new QPostResponseDto(post))
//                .from(post)
//                .where(post.category.eq(categoryReceived))
////                .leftJoin(post.likes, like).on(like.member.eq(mem))
//                .orderBy(post.postId.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
////        System.out.println("Tuple 성공");
////
////        list.stream().forEach(System.out::println);
//        JPAQuery<Long> countQuery = queryFactory
//                .select(post.count())
//                .from(post);
//
//        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
//    }
}
