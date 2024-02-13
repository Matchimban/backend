package com.project.matchimban.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("맛침반 API 명세서")
                        .description("맛침반 프로젝트 API 명세서입니다.")
                        .version("v1"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1. 전체 API")
                .pathsToMatch("/api/**")
                .build();
    }


    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("2. 회원 API")
                .pathsToMatch("/api/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi restaurantReservationApi() {
        return GroupedOpenApi.builder()
                .group("3. 매장_예약 API")
                .pathsToMatch("/api/restaurant-reservations/**")
                .build();
    }
}
