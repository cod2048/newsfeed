package com.hanghae.module_activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ModuleActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleActivityApplication.class, args);
    }

}
