package com.example.soldapple.member.repository;



import com.example.soldapple.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndDomain(String email, String domain);
    Optional<Member> findById(Long id);

    Member findOneById(Long id);
}
