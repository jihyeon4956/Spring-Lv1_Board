package com.sparta.spring_lv4.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "(^[0-9a-z]*$)",
            message = "ID는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp =  "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$",
            message = "Password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 포함한 8~15자여야 합니다.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";

}