package com.sparta.spring_lv1.entity;

import com.sparta.spring_lv1.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="board")
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 글번호 식별자(자동)

    @Column(nullable = false)
    private String username;                // 입력한 이름
    @Column(nullable = false)
    private String title;               // 입력한 제목
    @Column(nullable = false)
    private String contents;            // 작성 내용
    @Column(nullable = false)
    private String password;            // 비밀번호
    // 작성시간, 수정시간 -> Timestamped


    public Board(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}

