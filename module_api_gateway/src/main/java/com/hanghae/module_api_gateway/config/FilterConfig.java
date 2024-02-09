package com.hanghae.module_api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/api/users/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/verification/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/articles/**")
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/api/comments/**")
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/api/follows/**")
                        .uri("http://localhost:8081"))
                .route(r-> r.path("/api/likes/**")
                        .uri("http://localhost:8081"))
                .route(r-> r.path("/api/newsfeed/**")
                        .uri("http://localhost:8082"))
                .build();

    }
}
