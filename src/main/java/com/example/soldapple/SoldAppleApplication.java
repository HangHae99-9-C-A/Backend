package com.example.soldapple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SoldAppleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoldAppleApplication.class, args);
    }

}
