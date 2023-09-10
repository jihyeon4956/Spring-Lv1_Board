package com.sparta.spring_lv3.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
