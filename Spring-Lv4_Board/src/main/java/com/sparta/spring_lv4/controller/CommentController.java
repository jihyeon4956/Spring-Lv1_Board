package com.sparta.spring_lv4.controller;

import com.sparta.spring_lv4.dto.CommentRequestDto;
import com.sparta.spring_lv4.dto.CommentResponseDto;
import com.sparta.spring_lv4.dto.StatusResponseDto;
import com.sparta.spring_lv4.security.UserDetailsImpl;
import com.sparta.spring_lv4.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 등록
    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(requestDto, userDetails);
    }

    // 수정
    @Transactional
    @PutMapping("/comments/{id}")
    public StatusResponseDto updateComment(@PathVariable Long id,
                                           @RequestBody CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails);
    }
    // 삭제
    @DeleteMapping("/comments/{id}")
    public StatusResponseDto deleteComment(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails);
    }


}
