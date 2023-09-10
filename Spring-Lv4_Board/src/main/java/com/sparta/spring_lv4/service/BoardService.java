package com.sparta.spring_lv4.service;


import com.sparta.spring_lv4.dto.BoardRequestDto;
import com.sparta.spring_lv4.dto.BoardResponseDto;
import com.sparta.spring_lv4.dto.CommentResponseDto;
import com.sparta.spring_lv4.dto.StatusResponseDto;
import com.sparta.spring_lv4.entity.Board;
import com.sparta.spring_lv4.entity.User;
import com.sparta.spring_lv4.entity.UserRoleEnum;
import com.sparta.spring_lv4.repository.BoardRepository;
import com.sparta.spring_lv4.repository.CommentRepository;
import com.sparta.spring_lv4.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    // 생성
    public BoardResponseDto createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        // RequestDto -> Entity 변환 & DB저장
        Board board = boardRepository.save(new Board(requestDto, userDetails.getUser()));
        return new BoardResponseDto(board);
    }

    // 전체 게시글 목록 조회
    public List<BoardResponseDto> getBoardWithComments() {
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
        return boards.stream().map(BoardResponseDto::new).toList();
//        return boardRepository.findAllByOrderByCreatedAtDesc()
//                .stream()
//                .map(BoardResponseDto::new)
//                .toList();
    }
    // 선택조회
    public BoardResponseDto selectBoard(Long id) {
        // 해당 ID의 게시글을 조회
        Board board = findBoard(id);

        List<CommentResponseDto> comments = commentRepository.findAllByBoardIdOrderByCreatedAtDesc(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new BoardResponseDto(board, comments);
    }
    // 수정
    @Transactional
    public StatusResponseDto updateBoard(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        // 해당 게시글이 DB에 존재하는지 확인
        Board board = findBoard(id);
        // 작성자이거나 관리자인 경우 수정 가능
        if(board.getUsername().equals(userDetails.getUsername()) || userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            board.update(requestDto);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), "수정 성공!");
        } else {
            return new StatusResponseDto(String.valueOf(HttpStatus.FORBIDDEN), "작성자만 수정할 수 있습니다.");
        }
    }

    // 삭제
    public StatusResponseDto deleteMemo(Long id, UserDetailsImpl userDetails) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);

        if(board.getUsername().equals(userDetails.getUsername()) || userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            boardRepository.delete(board);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), id + "번 게시물 삭제에 성공했습니다.");
        } else return new StatusResponseDto(String.valueOf(HttpStatus.FORBIDDEN), "작성자만 삭제할 수 있습니다.");
    }

    // 게시판 찾기 메서드
    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
