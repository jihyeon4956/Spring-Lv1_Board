package com.sparta.spring_lv5.repository;

import com.sparta.spring_lv5.entity.Board;
import com.sparta.spring_lv5.entity.Comment;
import com.sparta.spring_lv5.entity.Like;
import com.sparta.spring_lv5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserAndBoard(User user, Board board);

    Like findByUserAndComment(User user, Comment comment);
}
