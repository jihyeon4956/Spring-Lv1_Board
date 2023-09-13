package com.sparta.spring_lv5.controller;


import com.sparta.spring_lv5.dto.BoardRequestDto;
import com.sparta.spring_lv5.dto.BoardResponseDto;
import com.sparta.spring_lv5.dto.StatusResponseDto;
import com.sparta.spring_lv5.security.UserDetailsImpl;
import com.sparta.spring_lv5.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성하기
    @PostMapping("/boards")
    public BoardResponseDto creatBoard(@RequestBody BoardRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails);
    }

    // 게시글 전체조회
    @GetMapping("/boards")
    public List<BoardResponseDto> getBoardWithComments() {
        return boardService.getBoardWithComments();
    }

    // 게시글 선택조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto selectBoard(@PathVariable Long id) {
        return boardService.selectBoard(id);
    }

    // 게시글 수정하기
    @PutMapping("/boards/{id}")
    public StatusResponseDto updateBoard(@PathVariable Long id,
                                         @RequestBody BoardRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails);

    }
    // 게시글 삭제하기
    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteMemo(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteMemo(id, userDetails);
    }

    // 좋아요
    @PostMapping("/boards/{id}/like")
    public StatusResponseDto likeCount(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.likeCount(id, userDetails);
    }
}
