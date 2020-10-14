package com.tribehired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class SimpleBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleBackendApplication.class, args);
    }
}
