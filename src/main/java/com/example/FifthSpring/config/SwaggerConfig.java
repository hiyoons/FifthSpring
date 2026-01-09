package com.example.FifthSpring.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        String[] paths = {"/api/playlist/**"}; //절대경로 등록
        return GroupedOpenApi.builder()
                .group("playlist")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApi ->
                        openApi.setInfo(
                                new Info().title("playlist api")
                                        .description("게시판").version("1.0.0")
                        )).build();
    }
}
