package com.fog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication
public class FogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FogApplication.class, args);
    }

}
