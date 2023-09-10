package com.sparta.spring_lv4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spring_lv4.dto.LoginRequestDto;
import com.sparta.spring_lv4.entity.UserRoleEnum;
import com.sparta.spring_lv4.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService; // UserDetailsServiceImpl 주입

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService; // UserDetailsServiceImpl 주입
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
//                            requestDto.getUsername(),
//                            requestDto.getPassword(),
//                            null

                            userDetails.getUsername(),
                            requestDto.getPassword()
//                            userDetails.getAuthorities()
                    )
            );
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
//        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
//
//        String token = jwtUtil.createToken(username, role);
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        String username = authResult.getName(); // Authentication에서 사용자 이름 가져오기
        UserDetails userDetails = (UserDetails) authResult.getPrincipal(); // UserDetails 가져오기
        // authorities를 UserRoleEnum 형태로 변환
        UserRoleEnum role = UserRoleEnum.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());

        String token = jwtUtil.createToken(username, role); // UserRoleEnum 형태로 변환한 롤 정보 사용
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}