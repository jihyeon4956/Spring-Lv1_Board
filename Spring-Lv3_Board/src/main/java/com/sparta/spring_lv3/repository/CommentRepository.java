package com.sparta.spring_lv3.repository;

import com.sparta.spring_lv3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdOrderByCreatedAtDesc(Long boardId);


}
