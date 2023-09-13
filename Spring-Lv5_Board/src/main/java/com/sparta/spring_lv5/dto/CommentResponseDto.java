package com.sparta.spring_lv5.dto;

import com.sparta.spring_lv5.entity.Comment;
import com.sparta.spring_lv5.entity.Like;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto implements Comparable<CommentResponseDto> {
    private Long id;
    private String comment;
    private String username;
    private LocalDateTime createdAt;
    private int totalLike;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
        this.totalLike = comment.getLikeList().stream().mapToInt(Like::getValue).sum();
    }

    @Override
    public int compareTo(CommentResponseDto other) {
        // id를 기준으로 내림차순으로 정렬
        return other.createdAt.compareTo(this.createdAt);
    }
}
