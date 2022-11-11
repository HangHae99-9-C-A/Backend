package com.example.soldapple.member.repository;



import com.example.soldapple.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    //MyPage를 위해 추가.
    Optional<Member> findById(Long id);
}
