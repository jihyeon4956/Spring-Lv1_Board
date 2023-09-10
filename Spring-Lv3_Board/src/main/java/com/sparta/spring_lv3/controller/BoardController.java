package com.sparta.spring_lv3.controller;


import com.sparta.spring_lv3.dto.BoardRequestDto;
import com.sparta.spring_lv3.dto.BoardResponseDto;
import com.sparta.spring_lv3.dto.StatusResponseDto;
import com.sparta.spring_lv3.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성하기
    @PostMapping("/boards")
    public BoardResponseDto creatBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest req) {
        return boardService.createBoard(requestDto, req);
    }

    // 게시글 전체조회
    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    // 게시글 선택조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto selectBoard(@PathVariable Long id) {
        return boardService.selectBoard(id);
    }

    // 게시글 수정하기 - 비밀번호 검증
    @PutMapping("/boards/{id}")
    public StatusResponseDto updateBoard(@PathVariable Long id,
                                         @RequestBody BoardRequestDto requestDto,
                                         HttpServletRequest req) {
        return boardService.updateBoard(id, requestDto, req);

    }

    // 메모 삭제하기 - 비밀번호 검증
    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteMemo(@PathVariable Long id,
                                        HttpServletRequest req) {
        return boardService.deleteMemo(id, req);
    }
}
