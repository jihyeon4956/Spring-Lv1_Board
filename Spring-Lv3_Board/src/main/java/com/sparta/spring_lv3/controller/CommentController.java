package com.sparta.spring_lv3.controller;

import com.sparta.spring_lv3.dto.CommentRequestDto;
import com.sparta.spring_lv3.dto.CommentResponseDto;
import com.sparta.spring_lv3.dto.StatusResponseDto;
import com.sparta.spring_lv3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 등록
//    @PostMapping("/comments")
//    public CommentResponseDto createdComment(@RequestBody CommentRequestDto requestDto){
//        return commentService.createdComment(requestDto);
//    }

    // 게시글에 달린 댓글 전체조회

    // 수정

    // 삭제

}
