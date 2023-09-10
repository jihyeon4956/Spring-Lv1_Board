package com.sparta.spring_lv4.dto;

import com.sparta.spring_lv4.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BoardResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        board.getCommentList().forEach(comment -> commentList.add(new CommentResponseDto(comment)));
        Collections.sort(commentList);
    }

    public BoardResponseDto(Board board, List<CommentResponseDto> commentList) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.commentList = commentList;
    }

}
