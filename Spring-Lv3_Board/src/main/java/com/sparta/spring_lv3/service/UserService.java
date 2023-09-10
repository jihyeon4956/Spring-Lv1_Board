package com.sparta.spring_lv3.service;

import com.sparta.spring_lv3.dto.LoginRequestDto;
import com.sparta.spring_lv3.dto.SignupRequestDto;
import com.sparta.spring_lv3.dto.StatusResponseDto;
import com.sparta.spring_lv3.entity.User;
import com.sparta.spring_lv3.entity.UserRoleEnum;
import com.sparta.spring_lv3.jwt.JwtUtil;
import com.sparta.spring_lv3.repository.UserRepository;
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


    public StatusResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 존재여부 DB확인
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // password 검증
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(),user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return new StatusResponseDto(String.valueOf(HttpStatus.OK), "로그인 성공");
    }
}
