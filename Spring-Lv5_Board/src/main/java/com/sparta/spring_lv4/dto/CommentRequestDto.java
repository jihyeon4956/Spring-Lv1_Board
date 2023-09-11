package com.sparta.spring_lv4.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long boardId;   // 보통 FE에서 받아옴
    private String comment;
}
