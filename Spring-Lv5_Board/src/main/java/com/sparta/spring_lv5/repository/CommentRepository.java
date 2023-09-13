package com.sparta.spring_lv5.repository;

import com.sparta.spring_lv5.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdOrderByCreatedAtDesc(Long boardId);


}
