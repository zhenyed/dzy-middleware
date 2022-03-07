package io.zhenye.gateway.router;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HttpRouter {

    @Bean
    public RouteLocator httpRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                 .route("spring_boot_route", r -> r
                        .path("/test/**").and()
                        .uri("lb://spring-boot")
                ).build();
    }

}
