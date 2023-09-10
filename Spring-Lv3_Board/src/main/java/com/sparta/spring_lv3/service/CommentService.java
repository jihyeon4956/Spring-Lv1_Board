package com.sparta.spring_lv3.service;

import com.sparta.spring_lv3.dto.CommentRequestDto;
import com.sparta.spring_lv3.dto.CommentResponseDto;
import com.sparta.spring_lv3.dto.StatusResponseDto;
import com.sparta.spring_lv3.entity.Board;
import com.sparta.spring_lv3.entity.Comment;
import com.sparta.spring_lv3.entity.User;
import com.sparta.spring_lv3.entity.UserRoleEnum;
import com.sparta.spring_lv3.repository.BoardRepository;
import com.sparta.spring_lv3.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest res) {
        User user = (User) res.getAttribute("user");

        Board saveBoard = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
        return new CommentResponseDto(commentRepository.save(new Comment(requestDto, saveBoard, user)));
    }

    @Transactional
    public StatusResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest req) {
        User user = (User)req.getAttribute("user");

        Comment comment = findComment(id);

        if (comment.getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            comment.update(requestDto);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), "댓글 업데이트 성공");
        } else {
            return new StatusResponseDto(String.valueOf(HttpStatus.FORBIDDEN), "작성자만 수정할 수 있습니다.");
        }
    }

    public StatusResponseDto deleteComment(Long id, CommentRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Comment comment = findComment(id);

        if (comment.getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), id + "번 댓글 삭제에 성공했습니다.");
        } else {
            return new StatusResponseDto(String.valueOf(HttpStatus.FORBIDDEN), "작성자만 삭제할 수 있습니다.");
        }

    }


    // 선택한 메모 존재여부 메서드
    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }
}
