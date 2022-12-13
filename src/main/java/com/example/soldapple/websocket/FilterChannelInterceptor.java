package com.example.soldapple.websocket;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99) //Spring Security 보다 인터셉터의 우선 순위를 높임
public class FilterChannelInterceptor implements ChannelInterceptor {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    public FilterChannelInterceptor() {
    }

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public MemberRepository memberRepository;       //???

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert headerAccessor != null;

        if(headerAccessor.getCommand() == StompCommand.CONNECT) {   //연결 했을때 헤더 확인
            String token = String.valueOf(headerAccessor.getNativeHeader("ACCESS_TOKEN").get(0));

            try{
                String emailAndDomain = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
                String[] usernameAndDomain = emailAndDomain.split(",");
                if (usernameAndDomain[0] == null || usernameAndDomain[1] == null || usernameAndDomain.length != 2) {
                    throw new UsernameNotFoundException("Username and domain must be provided");
                }
                Member member = memberRepository.findByEmailAndDomain(usernameAndDomain[0], usernameAndDomain[1]).orElseThrow(
                        () -> new RuntimeException("Not Found Account")
                );
                headerAccessor.addNativeHeader("MemberId", String.valueOf(member.getId()));
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return message;
    }
}
