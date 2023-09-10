package com.sparta.spring_lv4.entity;

import com.sparta.spring_lv4.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;  // 댓글 남긴 사용자 ID

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;   // 댓글을 남긴 게시글ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 댓글을 남긴 사용자ID

    public Comment(CommentRequestDto requestDto, Board saveBoard, User user) {
        this.username = user.getUsername();
        this.comment = requestDto.getComment();
        this.board = saveBoard;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
