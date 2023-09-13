package com.sparta.spring_lv5.controller;

import com.sparta.spring_lv5.dto.SignupRequestDto;
import com.sparta.spring_lv5.dto.StatusResponseDto;
import com.sparta.spring_lv5.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StatusResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult){

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) { // 에러가 있을 경우
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드: " + fieldError.getDefaultMessage());
                throw new IllegalArgumentException(fieldError.getDefaultMessage());  // 에러 메세지 반환
            }
        }
        return userService.signup(requestDto);
    }
}
