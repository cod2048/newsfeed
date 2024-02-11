package com.hanghae.module_newsfeed.resilience;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalErrorController {
    private final ErrorClient errorClient;

    public InternalErrorController(final ErrorClient errorClient) {
        this. errorClient = errorClient;
    }

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        errorClient.case1();
        return ResponseEntity.ok("Case1 success response");
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        errorClient.case2();
        return ResponseEntity.ok("Case2 success response");
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        errorClient.case3();
        return ResponseEntity.ok("Case3 success response");
    }
}
