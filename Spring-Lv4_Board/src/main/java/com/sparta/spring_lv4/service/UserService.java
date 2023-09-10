package com.sparta.spring_lv4.service;

import com.sparta.spring_lv4.dto.LoginRequestDto;
import com.sparta.spring_lv4.dto.SignupRequestDto;
import com.sparta.spring_lv4.dto.StatusResponseDto;
import com.sparta.spring_lv4.entity.User;
import com.sparta.spring_lv4.entity.UserRoleEnum;
import com.sparta.spring_lv4.jwt.JwtUtil;
import com.sparta.spring_lv4.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화
    private final JwtUtil jwtUtil;


    // ADMIN_TOKEN _일반 사용자인지 관리자인지 구분하기 위해 사용함
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";  // 실제로 이렇게 주진 않음
    
    public StatusResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());  // 인코딩으로 저장
        
        // 회원 중복확인
        Optional<User> checkUsernameOverlap = userRepository.findByUsername(username);
        if(checkUsernameOverlap.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 권한확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){   // true == ADMIN
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀려서 등록이 불가능합니다");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new StatusResponseDto(String.valueOf(HttpStatus.OK), "회원가입 성공");
    }
}
