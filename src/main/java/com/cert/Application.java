package com.cert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cert")
public class Application {
    public static void main(String[] args) {
        System.out.println("========== Starting =========");
        SpringApplication.run(Application.class);
        System.out.println("========== Started =============");
    }
}
