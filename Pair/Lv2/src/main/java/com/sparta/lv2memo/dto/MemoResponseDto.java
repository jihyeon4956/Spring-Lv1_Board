package com.sparta.lv2memo.dto;

import com.sparta.lv2memo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private LocalDateTime createdAt; // 작성시간

    public MemoResponseDto(Memo memo) {
        this.title = memo.getTitle();
        this.username = memo.getUser().getUsername();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
    }
}

