package com.sparta.spring_lv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringLv5Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLv5Application.class, args);
    }

}
