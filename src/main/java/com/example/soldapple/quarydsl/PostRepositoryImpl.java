package com.example.soldapple.quarydsl;

import com.example.soldapple.like.entity.QLike;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
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


    //    @Override
//    public List<PostResponseDto> findAllMyTest(){
//    QPost qPost = QPost.post;
//    QLike qLike = QLike.like;
//
//        queryFactory
//                .select(post)
//                .from()
//                .join(post.like, like)
//                .fetch();
//
//    }


    //    @Override
//    public List<Post> findAllMyTest() {
//        QPost qPost = QPost.post;
//
//        //QLike qLike = QLike.like;
//
//        return queryFactory
//                .select(post)
//                .from(post)
//                .where(post.member.nickname.eq("이승주"))
//                .fetch();
//
//    }
//    테스트를 위해 주석
    @Override
    public List<Post> findAllMyTest(Member mem) {
        QPost qPost = post;
        QLike qLike = like;

        return queryFactory
                .select(post)
                .from(post)
                .join(post.likes, like).on(like.member.eq(mem))
                .fetch();
    }


}
