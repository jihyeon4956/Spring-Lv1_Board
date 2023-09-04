package com.sparta.spring_lv1.controller;

import com.sparta.spring_lv1.dto.SignupRequestDto;
import com.sparta.spring_lv1.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "usercontroll")
@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 화면
    @GetMapping("/user/login-page")
    public String loginPage(){
        return "login";
    }

    // 회원가입 화면
    @GetMapping("/user/signup")
    public String signupPage(){
        return "signup";
    }

    // 회원가입 정보 입력
    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult){

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) { // 에러가 있을 경우
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드: " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/uer/signup";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }




}
