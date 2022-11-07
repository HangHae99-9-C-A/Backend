package com.example.soldapple.security.user;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Not Found Account")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setMember(member);

        return userDetails;
    }
}
