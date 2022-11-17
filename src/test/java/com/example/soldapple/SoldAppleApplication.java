//package com.example.soldapple;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import javax.annotation.PostConstruct;
//import java.util.Locale;
//import java.util.TimeZone;
//
//@EnableJpaAuditing
//@SpringBootTest
//public class SoldAppleApplication {
//    @PostConstruct
//    public void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//        Locale.setDefault(Locale.KOREA);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(SoldAppleApplication.class, args);
//    }
//
//}
