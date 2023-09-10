package com.sparta.lv2memo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$"
            , message = "알파벳 소문자(a~z)와 숫자(0~9)로 구성된 4~10자의 문자열이어야 합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[A-Z]).{8,15}$"
            , message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자(@#$%^&+=!)로 구성되어야 합니다.")
    private String password;


    private boolean admin = false;
    private String adminToken = "";
}