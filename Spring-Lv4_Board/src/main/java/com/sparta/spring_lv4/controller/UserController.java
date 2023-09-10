package com.sparta.spring_lv4.controller;

import com.sparta.spring_lv4.dto.LoginRequestDto;
import com.sparta.spring_lv4.dto.SignupRequestDto;
import com.sparta.spring_lv4.dto.StatusResponseDto;
import com.sparta.spring_lv4.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "usercontroll")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입 정보 입력
    @PostMapping("/user/signup")
    public StatusResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult){

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) { // 에러가 있을 경우
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드: " + fieldError.getDefaultMessage());
            }
            return new StatusResponseDto(String.valueOf(HttpStatus.BAD_REQUEST), "회원가입에 실패했습니다");
        }
        return userService.signup(requestDto);
    }
}
