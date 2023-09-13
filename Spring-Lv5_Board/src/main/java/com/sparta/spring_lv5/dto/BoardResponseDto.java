package com.sparta.spring_lv5.dto;

import com.sparta.spring_lv5.entity.Board;
import com.sparta.spring_lv5.entity.Like;
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
    private int totalLike;
    private List<CommentResponseDto> commentList = new ArrayList<>();


    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.totalLike = board.getLikeList().stream().mapToInt(Like::getValue).sum();
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
