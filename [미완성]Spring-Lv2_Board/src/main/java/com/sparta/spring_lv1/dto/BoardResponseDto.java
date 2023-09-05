package com.sparta.spring_lv1.dto;

import com.sparta.spring_lv1.entity.Board;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
public class BoardResponseDto {
    private String username;                // 입력한 이름
    private String title;               // 입력한 제목
    private String contents;            // 작성 내용
    private LocalDateTime createdAt;         // 작성된 시간(자동)

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
    }

}
