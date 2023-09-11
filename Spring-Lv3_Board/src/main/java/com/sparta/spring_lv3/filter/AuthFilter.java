package com.sparta.spring_lv3.filter;



import com.sparta.spring_lv3.entity.User;
import com.sparta.spring_lv3.jwt.JwtUtil;
import com.sparta.spring_lv3.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
@Order(1)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        log.info(url);  // /api/boards
        log.info(String.valueOf(url.startsWith("/api/boards/**")));  // false
        log.info(String.valueOf(url.startsWith("/api/boards")));

        log.info(String.valueOf(StringUtils.hasText(url)));
        log.info(String.valueOf(httpServletRequest.getMethod().equals("GET")));

        if (StringUtils.hasText(url) &&
            (url.startsWith("/api/user") || url.startsWith("/css") || url.startsWith("/js"))) {
            log.info("인증처리를 하지 않는 URL 1: " + url);
            chain.doFilter(request, response); // 다음 Filter 로 이동
        }
        else if (StringUtils.hasText(url) && (url.startsWith("/api/boards/**") && httpServletRequest.getMethod().equals("GET")) ){ // 전체목록 비회원조회 가능
            log.info("인증처리를 하지 않는 게시판 조회 URL 2: " + url);
        }
        else if (StringUtils.hasText(url) && (url.startsWith("/api/boards") && httpServletRequest.getMethod().equals("GET"))
        ){ // 전체목록 비회원조회 가능
            log.info("인증처리를 하지 않는 게시판 조회 URL 3: " + url);
            chain.doFilter(request, response);
        }
        else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인 (Controller에서는 @CookieValue(AUTHORIZATION_HEADER으로 토큰을 가져올 수 있었는데 필터는 우선실행이라 별도로 가져와야함)
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->  // getSubject() : 유저이름 가져오기
                        new NullPointerException("Not Found User")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

}