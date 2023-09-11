package com.sparta.spring_lv4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "likecount")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value=1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Like(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
