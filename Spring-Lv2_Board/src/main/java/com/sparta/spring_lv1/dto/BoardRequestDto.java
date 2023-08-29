package com.sparta.spring_lv1.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String username;                // 입력한 이름
    private String title;               // 입력한 제목
    private String contents;            // 작성 내용
    private String password;            // 입력한 비밀번호

}
