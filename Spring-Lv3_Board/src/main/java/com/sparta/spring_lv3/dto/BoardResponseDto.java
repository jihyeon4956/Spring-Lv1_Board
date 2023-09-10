package com.sparta.spring_lv3.dto;

import com.sparta.spring_lv3.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
    }

}
