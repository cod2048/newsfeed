package com.hanghae.module_activity.resilience;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Random;

@RestController
@Slf4j
public class ErrorfulController {

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        log.info("case1 실행");
        // Simulate 5% chance of 500 error
        if (new Random().nextInt(100) < 5) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        log.info("case2 실행");
        // Simulate blocking requests every first 10 seconds
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            // Simulate a delay (block) for 10 seconds
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return ResponseEntity.status(503).body("Service Unavailable");
        }

        return ResponseEntity.ok("Normal response");
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        log.info("case3 실행");
        // Simulate 500 error every first 10 seconds
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }
}