package com.sparta.spring_lv1.service;


import com.sparta.spring_lv1.dto.BoardRequestDto;
import com.sparta.spring_lv1.dto.BoardResponseDto;
import com.sparta.spring_lv1.entity.Board;
import com.sparta.spring_lv1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());

        // RequestDto -> Entity 변환
        Board board = new Board(requestDto);
        board.setPassword(password);  // 암호화로 저장
        // DB저장
        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard);
    }


    // 전체 게시글 목록 조회
    public List<BoardResponseDto> getBoard() {
        // DB 조회
        return boardRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .toList();
    }




    // 선택조회
    public BoardResponseDto getBoardById(Long id) {
        // 해당 ID의 게시글을 조회
        Board board = findBoard(id);

        return new BoardResponseDto(board);
    }


    // 수정
    @Transactional
    public Long updateBoard(Long id, BoardRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        String password = requestDto.getPassword();
        Board board = findBoard(id);


        if(!passwordEncoder.matches(password, board.getPassword())) { // (평문, 암호문)
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        // 이름, 제목, 내용 수정가능
        board.update(requestDto);
        return id;

    }

    // 삭제
    public String deleteMemo(Long id, BoardRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        String password = requestDto.getPassword();
        Board board = findBoard(id);

        if(!passwordEncoder.matches(password, board.getPassword())) { // (평문, 암호문)
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        boardRepository.delete(board);
        return "게시글 삭제 완료";
    }

    // 게시판 찾기 메서드
    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
