package com.example.soldapple.jwt;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.soldapple.error.ErrorCode.REFRESH_TOKEN_IS_EXPIRED;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if (accessToken != null) {
            if (!jwtUtil.tokenValidation(accessToken)) {
                response.sendRedirect("https://frontend-inky-delta.vercel.app/signin"); //토큰 만료시 Redirect 주소를 보냄
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(accessToken));  //accessToken이 유효하다면 Authentication에 인증정보와 유저 정보를 저장함
        } else if (refreshToken != null) {
            if (!jwtUtil.refreshTokenValidation(refreshToken)) {
                throw new CustomException(REFRESH_TOKEN_IS_EXPIRED);
            }
            setAuthentication(jwtUtil.getEmailFromToken(refreshToken));
        }

        filterChain.doFilter(request, response);    //다음 필터로 넘어감(@AuthenticationPrincipal을 사용가능하게할 UsernamePasswordAuthenticationFilter)
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);    //인증정보에 유저정보와 인증여부를 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);   //ContextHoler안에 있는 Authentication에 인증정보에 유저정보와 인증여부를 저장
    }

    //나중에 사용 할 수 있어 주석처리함
//    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
//        response.setStatus(status.value());
//        response.setContentType("application/json");
//        try {
//            String json = new ObjectMapper().writeValueAsString(new GlobalResDto(msg, status.value()));
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }

}
