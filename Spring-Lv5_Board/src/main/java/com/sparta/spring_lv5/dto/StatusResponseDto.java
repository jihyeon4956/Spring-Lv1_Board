package com.sparta.spring_lv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class StatusResponseDto {
    private int status;
    private String msg;

}
