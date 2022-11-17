//package com.example.soldapple;
//
//import com.example.soldapple.member.entity.Member;
//import com.example.soldapple.post.dto.PostReqDto;
//import com.example.soldapple.post.entity.Post;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.ArrayList;
//import java.util.List;
//
//@Profile("local")
//@Component
//@RequiredArgsConstructor
//public class InitPost {
//    private final InitPostService initPostService;
//
//
//    @PostConstruct
//    public void init() {
//        initPostService.init();
//    }
//    @Component
//    static class InitPostService {
//        @PersistenceContext
//        EntityManager em;
//        @Transactional
//        public void init() {
//            Member memberA = new Member("emailA","nicknameA","domainA","avataUrlA");
//            Member memberB = new Member("emailB","nicknameB","domainB","avataUrlB");
//            Member memberC = new Member("emailC","nicknameC","domainC","avataUrlC");
//           PostReqDto postReqDtoA = new PostReqDto("title","category",1000,1000,"content");
//            PostReqDto {
//                private String title;
//                private String category;
//                private Long expectPrice;
//                private Long userPrice;
//                private String content;
//            }
//            Post postA = new Post(,memberA);
//            Post postB = new Post();
//
//            em.persist(memberA);
//            em.persist(memberB);
//
//        }
//    }
//}
