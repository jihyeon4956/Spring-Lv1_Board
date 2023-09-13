package com.sparta.spring_lv5.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
