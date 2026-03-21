package com.smartpos.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmartPossBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPossBackendApplication.class, args);
    }

}
