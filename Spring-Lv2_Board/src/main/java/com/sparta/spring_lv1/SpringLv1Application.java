package com.sparta.spring_lv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring Security 인증 기능 제외
// Spring Security의 일부기능을 제외시킴 (시큐리티 적용할때 다시 해제할거임)
public class SpringLv1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLv1Application.class, args);
    }

}
