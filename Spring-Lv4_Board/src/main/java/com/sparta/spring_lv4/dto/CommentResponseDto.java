package com.sparta.spring_lv4.dto;

import com.sparta.spring_lv4.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto implements Comparable<CommentResponseDto> {
    private Long id;
    private String comment;
    private String username;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
    }

    @Override
    public int compareTo(CommentResponseDto other) {
        // id를 기준으로 내림차순으로 정렬
        return other.createdAt.compareTo(this.createdAt);
    }
}
