package com.sparta.spring_lv1.service;

import com.sparta.spring_lv1.dto.SignupRequestDto;
import com.sparta.spring_lv1.entity.User;
import com.sparta.spring_lv1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN _일반 사용자인지 관리자인지 구분하기 위해 사용함
//    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";  // 실제로 이렇게 주진 않음
    
    public void signup(SignupRequestDto requestDto) {
        // client가 입력한 username 변수화
        String username = requestDto.getUsername();
        // client가 입력한 비밀번호 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());  
        
        // 회원 중복확인
        Optional<User> checkUserOverlap = userRepository.findbyUsername(username); // findbyUsername 반환형태가 Optional임


    }

    
}
