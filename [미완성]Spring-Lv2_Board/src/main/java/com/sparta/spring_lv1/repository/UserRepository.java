package com.sparta.spring_lv1.repository;

import com.sparta.spring_lv1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findbyUsername(String username);  // username 찾는 메서드
}
