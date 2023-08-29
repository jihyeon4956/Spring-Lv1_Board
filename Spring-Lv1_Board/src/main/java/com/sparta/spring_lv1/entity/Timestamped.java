package com.sparta.spring_lv1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // JPA Entity Class들이 해당 클래스를 상속할 경우
                  // createdAt이랑 modifiedAt처럼 추상클래스에 선언한 멤버변수를 Column으로 인식해줌
                  // 단, Application Class에 @EnableJpaAuditing을 추가해줘야 한다
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    @Column(updatable = false) // 최초 시간만 저장함, 업데이트 거부
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate // 변경된 시간이 자동으로 저장된다
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}