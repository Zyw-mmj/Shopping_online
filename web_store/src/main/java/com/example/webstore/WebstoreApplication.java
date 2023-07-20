package com.example.webstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages = {"com.example.webstore.dao.mapper"})
public class WebstoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebstoreApplication.class, args);
    }
}
