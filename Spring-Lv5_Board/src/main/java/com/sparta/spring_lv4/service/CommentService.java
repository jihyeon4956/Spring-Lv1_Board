package com.sparta.spring_lv4.service;

import com.sparta.spring_lv4.dto.CommentRequestDto;
import com.sparta.spring_lv4.dto.CommentResponseDto;
import com.sparta.spring_lv4.dto.StatusResponseDto;
import com.sparta.spring_lv4.entity.*;
import com.sparta.spring_lv4.repository.BoardRepository;
import com.sparta.spring_lv4.repository.CommentRepository;
import com.sparta.spring_lv4.repository.LikeRepository;
import com.sparta.spring_lv4.repository.UserRepository;
import com.sparta.spring_lv4.security.UserDetailsImpl;
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
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Board saveBoard = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
        return new CommentResponseDto(commentRepository.save(new Comment(requestDto, saveBoard, user)));
    }

    @Transactional
    public StatusResponseDto updateComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);

        if (comment.getUsername().equals(userDetails.getUsername()) || userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            comment.update(requestDto);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), "댓글 업데이트 성공");
        } else {
            return new StatusResponseDto(String.valueOf(HttpStatus.FORBIDDEN), "작성자만 수정할 수 있습니다.");
        }
    }

    public StatusResponseDto deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);

        if (comment.getUsername().equals(userDetails.getUsername()) || userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
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

    public StatusResponseDto likeCount(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Like findLike = likeRepository.findByUserAndComment(user, comment);
        if(findLike != null) {
            likeRepository.delete(findLike);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), "좋아요 취소");
        } else {
            Like like = new Like(user, comment);
            likeRepository.save(like);
            return new StatusResponseDto(String.valueOf(HttpStatus.OK), "좋아요 등록");
        }
    }
}
