package com.project.matchimban.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("그룹 이름")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("맛침반 API 명세서")
                        .description("맛침반 프로젝트 API 명세서입니다.")
                        .version("v1"));
    }

    @Bean
    public GroupedOpenApi restaurantReservationApi() {
        return GroupedOpenApi.builder()
                .group("매장_예약")
                .pathsToMatch("/api/restaurant-reservations/**")
                .build();
    }
}
