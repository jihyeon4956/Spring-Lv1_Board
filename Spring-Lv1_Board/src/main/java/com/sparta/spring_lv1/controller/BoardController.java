package com.sparta.spring_lv1.controller;


import com.sparta.spring_lv1.dto.BoardRequestDto;
import com.sparta.spring_lv1.dto.BoardResponseDto;
import com.sparta.spring_lv1.entity.Board;
import com.sparta.spring_lv1.service.BoardService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 생성하기
    @PostMapping("/boards")
    public BoardResponseDto creatBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);  //
    }

    // 게시글 전체조회
    @GetMapping("/boards")
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoard();  //
    }

    // 게시글 선택조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);  //
    }

    // 게시글 수정하기 - 비밀번호 검증
    @PutMapping("/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);  //
    // 업데이트 할 게시글의 {id}를 받아옴, 수정된 정보를 JSON으로 받아옴

    }

    // 메모 삭제하기 - 비밀번호 검증
    @DeleteMapping("/boards/{id}")
    public String deleteMemo(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) { // 여기에 String password 하고싶었음
        return boardService.deleteMemo(id, requestDto);  //
    }
}
