package com.sparta.spring_lv5.repository;

import com.sparta.spring_lv5.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreatedAtDesc();

}

