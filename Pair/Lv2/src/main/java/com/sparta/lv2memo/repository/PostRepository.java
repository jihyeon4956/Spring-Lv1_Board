package com.sparta.lv2memo.repository;

import com.sparta.lv2memo.entity.Memo;
import com.sparta.lv2memo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    List<Memo> findAllByUserOrderByModifiedAtDesc(User user);
}
