package com.hanghae.module_newsfeed.resilience;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalErrorController {
    private final ErrorClient errorClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    public InternalErrorController(final ErrorClient errorClient, CircuitBreakerFactory circuitBreakerFactory, RetryRegistry retryRegistry) {
        this.errorClient = errorClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.retryRegistry = retryRegistry;
    }

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> errorClient.case1(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> errorClient.case1()).apply(1);

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorClient.case1()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> errorClient.case2(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> errorClient.case2()).apply(1);

        // 3. CircuitBreaker + Retry 적용
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorClient.case2()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> errorClient.case3(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> errorClient.case3()).apply(1);

        // 3. CircuitBreaker + Retry 적용
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorClient.case3()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }
}
