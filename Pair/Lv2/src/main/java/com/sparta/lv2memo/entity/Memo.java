package com.sparta.lv2memo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.lv2memo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "memo")
@NoArgsConstructor
public class Memo extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;//제목

//    @Column(name = "username", nullable = false)
//    private String username;//작성자명

    @Column(name = "contents", nullable = false)
    private String contents;//작성 내용

//    @Column(name = "password", nullable = false)
//    private String password;//비밀번호

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public Memo(MemoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
//        this.username = user.getUsername();
        this.contents = requestDto.getContents();
//        this.password = user.getPassword();
        this.user = user;
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
//        this.username = user.getUsername();
        this.contents = requestDto.getContents();
    }
}
